package cn.eden.taotao.manager.service;

import java.util.List;

import cn.eden.taotao.manager.pojo.ItemParam;
import cn.eden.taotao.pojo.TbItemParam;
import cn.eden.taotao.util.TaotaoResult;

public interface ItemParamService {
	TaotaoResult getItemParamByCid(Long cid);
	TaotaoResult insertItemParam(TbItemParam itemParam);
	List<ItemParam> getItemParams();
}
