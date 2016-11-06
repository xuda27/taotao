package cn.eden.taotao.sso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.TbUserMapper;
import cn.eden.taotao.pojo.TbUser;
import cn.eden.taotao.pojo.TbUserExample;
import cn.eden.taotao.pojo.TbUserExample.Criteria;
import cn.eden.taotao.sso.service.UserService;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 单点登录
 * @author Eden
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

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

}
