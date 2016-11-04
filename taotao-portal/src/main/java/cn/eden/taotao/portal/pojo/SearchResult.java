package cn.eden.taotao.portal.pojo;

import java.util.List;

/**
 * 商品 查询的结果
 * 
 * @author Eden
 *
 */
public class SearchResult {
	// 商品列表
	private List<Item> items;
	// 总记录数
	private long recordCount;
	// 总页数
	private long pageCount;
	// 当前页
	private long currentPage;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

}
