package cn.eden.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.pojo.TbUser;
import cn.eden.taotao.sso.service.UserService;
import cn.eden.taotao.util.ExceptionUtil;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 用户登录服务
 * 
 * @author Eden
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 数据校验
	 * 
	 * @param param
	 * @param type
	 * @param callback
	 * @return Object(TaotaoResult or MappingJacksonValue)
	 */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object chechData(@PathVariable String param,
			@PathVariable Integer type, String callback) {
		TaotaoResult result = null;
		// 参数有效性验证
		if (StringUtils.isBlank(param)) {
			result = TaotaoResult.build(400, "校验内容更不能为空");
		}
		if (type == null) {
			result = TaotaoResult.build(400, "校验内容类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = TaotaoResult.build(400, "校验内容类型错误");
		}

		result = userService.checkData(param, type);

		// 返回回调函数形式，形成jsonp格式
		if (callback != null) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

	/**
	 * 用户注册<br>
	 * 接受提交的数据：用户名、密码、电话、邮件，使用pojo接受
	 * 
	 * @param user
	 * @return TaotaoResult：成功200失败400异常500
	 */
	@RequestMapping("/register")
	@ResponseBody
	public TaotaoResult createUser(TbUser user) {
		try {
			TaotaoResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 用户登录<br>
	 * 接受表单，包含用户、密码
	 * 
	 * @param username
	 * @param password
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult userLogin(String username, String password) {
		return userService.userLogin(username, password);
	}

	/**
	 * 接受Token调用Service获得用户信息，判断是否是jsonp调用，按需返回值
	 * 
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		// 判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	}

	/**
	 * 接受Token调用Service安全退出，判断是否是jsonp调用，按需返回值
	 * 
	 * @param token
	 * @param callback
	 * @return Object
	 */
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public Object userLogout(@PathVariable String token, String callback) {
		TaotaoResult result = null;
		try {
			result = userService.userLogout(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		// 判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	}
}
