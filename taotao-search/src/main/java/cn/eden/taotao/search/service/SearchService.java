package cn.eden.taotao.search.service;

import cn.eden.taotao.search.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString, int page, int rows);
}
