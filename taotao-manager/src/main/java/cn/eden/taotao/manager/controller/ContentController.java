package cn.eden.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.manager.pojo.DataGridResult;
import cn.eden.taotao.manager.service.ContentService;
import cn.eden.taotao.pojo.TbContent;
import cn.eden.taotao.util.TaotaoResult;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public DataGridResult getContentsByPage(Long page, Long rows) {
		return contentService.getContentsByPage(page, rows);
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		return contentService.insertContent(content);
	}
}
