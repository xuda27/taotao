package cn.eden.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.eden.taotao.mapper.TbUserMapper;
import cn.eden.taotao.pojo.TbUser;
import cn.eden.taotao.pojo.TbUserExample;
import cn.eden.taotao.pojo.TbUserExample.Criteria;
import cn.eden.taotao.sso.dao.JedisClient;
import cn.eden.taotao.sso.service.UserService;
import cn.eden.taotao.util.ExceptionUtil;
import cn.eden.taotao.util.JsonUtils;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 单点登录
 * 
 * @author Eden
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	@Autowired
	private JedisClient jedisClient;

	private String REDIS_USER_SESSION_KEY;

	private Integer SSO_SESSION_EXPIRE = 60 * 60 * 2; // 2 hours

	/**
	 * 数据校验
	 */
	@Override
	public TaotaoResult checkData(String content, Integer type) {
		// 创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 对数据进行校验：1、2、3分别代表username、phone、email
		if (type == 1) {
			criteria.andUsernameEqualTo(content);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(content);
		} else if (type == 3) {
			criteria.andEmailEqualTo(content);
		}
		// 执行查询，如果数据库没有数据 则返回true 否则false
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return TaotaoResult.ok(true);
		} else {
			return TaotaoResult.ok(false);
		}
	}

	/**
	 * 用户注册
	 */
	@Override
	public TaotaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		// md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword()
				.getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok(user);
	}

	/**
	 * 用户登录
	 */
	@Override
	public TaotaoResult userLogin(String username, String password) {
		// 设置查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		// 如果没有此用户名
		if (list == null || list.size() == 0) {
			TaotaoResult.build(400, "没有此用户,请注册");
		}
		TbUser user = list.get(0);
		// 对比密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(
				user.getPassword())) {
			return TaotaoResult.build(400, "密码错误");
		}
		// 生成 token
		String uuid = UUID.randomUUID().toString();
		// 保存用户之前，把用户对象中的密码清空
		user.setPassword(null);
		// 把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + uuid,
				JsonUtils.objectToJson(user));
		// 设置session的过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + uuid,
				SSO_SESSION_EXPIRE);
		// 返回token
		return TaotaoResult.ok(uuid);
	}

	/**
	 * 通过Token查询user信息
	 */
	@Override
	public TaotaoResult getUserByToken(String token) {
		// 根据token从redis中查询用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		// 判断是否为空
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "用户信息已经过期");
		}
		// 更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token,
				SSO_SESSION_EXPIRE);
		// 返回用户信息
		return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

	@Override
	public TaotaoResult userLogout(String token) {
		// 根据token从redis删除用户信息
		jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
		// 返回用户信息
		return TaotaoResult.ok();
	}

}
