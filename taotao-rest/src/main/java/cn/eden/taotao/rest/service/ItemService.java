package cn.eden.taotao.rest.service;

import cn.eden.taotao.util.TaotaoResult;

public interface ItemService {
	TaotaoResult getItem(long itemId);
	TaotaoResult getItemDesc(long itemId);
	TaotaoResult getItemParamItem(long itemId);
}
