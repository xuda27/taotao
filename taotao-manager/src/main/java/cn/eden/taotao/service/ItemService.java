package cn.eden.taotao.service;
import cn.eden.taotao.pojo.DataGridResult;
import cn.eden.taotao.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(Long id);
	DataGridResult getItemsByPage(long page, long pageSize);
}
