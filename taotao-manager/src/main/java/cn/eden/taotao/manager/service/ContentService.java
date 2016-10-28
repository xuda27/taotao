package cn.eden.taotao.manager.service;

import cn.eden.taotao.manager.pojo.DataGridResult;
import cn.eden.taotao.pojo.TbContent;
import cn.eden.taotao.util.TaotaoResult;

public interface ContentService {
	DataGridResult getContentsByPage(long page, long pageSize);
	TaotaoResult insertContent(TbContent content);
}
