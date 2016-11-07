package cn.eden.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/user/showRegister")
	public String showRegister() {
		return "register";
	}
	
	@RequestMapping("/user/showLogin")
	public String showLogin() {
		return "login";
	}
}
