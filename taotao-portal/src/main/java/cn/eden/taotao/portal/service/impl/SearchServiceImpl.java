package cn.eden.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.portal.pojo.SearchResult;
import cn.eden.taotao.portal.service.SearchService;
import cn.eden.taotao.util.HttpClientUtil;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 前台商品搜索业务
 * 
 * @author Eden
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String queryString, Integer page) {
		// 调用search服务工程
		// 设置查询参数，通过httpClient调用服务层
		Map<String, String> param = new HashMap<String, String>();
		// 设置查询条件
		param.put("q", queryString);
		param.put("page", page + "");
		// 通过httpClient调用服务层
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		// 把json字符串转换为java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,
				SearchResult.class);
		if (taotaoResult.getStatus() == 200) {
			SearchResult result = (SearchResult) taotaoResult.getData();
			return result;
		}
		return null;
	}

}
