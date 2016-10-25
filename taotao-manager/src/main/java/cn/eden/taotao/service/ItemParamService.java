package cn.eden.taotao.service;

import cn.eden.taotao.pojo.TbItemParam;
import cn.eden.taotao.util.TaotaoResult;

public interface ItemParamService {
	TaotaoResult getItemParamByCid(Long cid);
	TaotaoResult insertItemParam(TbItemParam itemParam);
}
