package cn.eden.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.eden.taotao.search.dao.SearchDao;
import cn.eden.taotao.search.pojo.Item;
import cn.eden.taotao.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResult search(SolrQuery query) {
		// 创建返回值对象
		SearchResult result = new SearchResult();
		// 根据查询条件查询索引库
		QueryResponse queryResponse = null;
		try {
			queryResponse = solrServer.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// 取查询结果
		SolrDocumentList documentList = queryResponse.getResults();
		// 取查询结果总数量，并加入result中
		result.setRecordCount(documentList.getNumFound());
		// 商品列表
		List<Item> items = new ArrayList<Item>();
		// 取高亮显示
		Map<String, Map<String, List<String>>> hightLighting = queryResponse
				.getHighlighting();
		// 取商品列表
		for (SolrDocument document : documentList) {
			// 创建商品对象
			Item item = new Item();
			item.setId((String) document.get("id"));
			// 取高亮显示的结果
			List<String> list = hightLighting.get(document.get("id")).get(
					"item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) document.get("item_title");
			}
			item.setTitle(title);
			item.setImage((String) document.get("item_image"));
			item.setPrice((long) document.get("item_price"));
			item.setSell_point((String) document.get("item_sell_point"));
			item.setCategory_name((String) document.get("item_category_name"));
			// 添加的商品列表
			items.add(item);
		}
		result.setItems(items);
		return result;
	}

}
