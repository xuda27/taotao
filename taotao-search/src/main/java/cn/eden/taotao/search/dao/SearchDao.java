package cn.eden.taotao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.eden.taotao.search.pojo.SearchResult;

public interface SearchDao {
	SearchResult search(SolrQuery query);
}
