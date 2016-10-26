package cn.eden.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowPageController {
	@RequestMapping("/index")
	public String showIndex() {
		return "index";
	}
}
