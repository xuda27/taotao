package cn.eden.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转Controller
 * @author Eden
 *
 */
@Controller
public class PageController {
	/**
	 * 打开首页
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";// 逻辑视图
	}
	
	/**
	 * 展示其他页面
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
	

}

