package cn.eden.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.eden.taotao.portal.service.ContentService;

/**
 * 首页展示
 * @author Eden
 *
 */
@Controller
public class ShowPageController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		String adJson = contentService.getContentList();
		model.addAttribute("ad1", adJson);
		return "index";
	}
}
