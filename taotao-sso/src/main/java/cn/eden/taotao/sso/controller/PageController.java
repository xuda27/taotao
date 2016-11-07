package cn.eden.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/user/showRegister")
	public String showRegister() {
		return "register";
	}

	/**
	 * 设置回调
	 * 
	 * @param redirect
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/showLogin")
	public String showLogin(String redirect, Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}
}
