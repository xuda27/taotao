package cn.eden.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.search.pojo.SearchResult;
import cn.eden.taotao.search.service.SearchService;
import cn.eden.taotao.util.ExceptionUtil;
import cn.eden.taotao.util.TaotaoResult;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	TaotaoResult search(@RequestParam("q") String queryString,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "40") Integer rows) {
		// 查询条件不能为空
		if (StringUtils.isBlank(queryString)) {
			return TaotaoResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			// 解决get乱码问题
			searchResult = searchService.search(
					new String(queryString.getBytes("iso8859-1"), "utf-8"),
					page, rows);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok(searchResult);
	}
}
