package cn.eden.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.manager.pojo.DataGridResult;
import cn.eden.taotao.manager.service.ContentService;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public DataGridResult getContentsByPage(Long page, Long rows) {
		return contentService.getContentsByPage(page, rows);
	}
}
