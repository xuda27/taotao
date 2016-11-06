package cn.eden.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.sso.service.UserService;
import cn.eden.taotao.util.TaotaoResult;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	// 数据校验
	@RequestMapping("/user/check/{param}/{type}")
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
}
