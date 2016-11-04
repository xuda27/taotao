package cn.eden.taotao.portal.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.eden.taotao.portal.pojo.SearchResult;
import cn.eden.taotao.portal.service.SearchService;

/**
 * 前台搜索控制层
 * 
 * @author Eden
 *
 */
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString, @RequestParam(defaultValue="1")Integer page, Model model) {
		if(!StringUtils.isBlank(queryString)) {
			try {
				queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		SearchResult result = searchService.search(queryString, page);
		//向页面传递参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("items", result.getItems());
		model.addAttribute("page", page);
		return "search";
	}
}
