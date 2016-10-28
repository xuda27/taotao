package cn.eden.taotao.manager.pojo;

import java.util.List;
/**
 * EasyUi树形表格 分页查询所需数据的pojo
 * @author Eden
 *
 */
public class DataGridResult {
	private long total;
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	
}
