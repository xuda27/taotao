package cn.eden.taotao.service;
import cn.eden.taotao.pojo.DataGridResult;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemDesc;
import cn.eden.taotao.pojo.TbItemParamItem;
import cn.eden.taotao.util.TaotaoResult;

public interface ItemService {
	
	TbItem getItemById(Long id);
	DataGridResult getItemsByPage(long page, long pageSize);
	TaotaoResult addItem(TbItem item, TbItemDesc itemDesc, String itemParam);
	
	//临时
	TbItemDesc listItemDesc(long itemId);
	TaotaoResult updateItem(TbItem item, TbItemDesc itemDesc);
	
	TaotaoResult deleteItem(String ids);
}
