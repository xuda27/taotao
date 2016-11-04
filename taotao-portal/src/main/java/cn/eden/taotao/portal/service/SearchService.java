package cn.eden.taotao.portal.service;

import cn.eden.taotao.portal.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString,Integer page);
}
