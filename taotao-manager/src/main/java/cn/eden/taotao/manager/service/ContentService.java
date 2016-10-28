package cn.eden.taotao.manager.service;

import cn.eden.taotao.manager.pojo.DataGridResult;

public interface ContentService {
	DataGridResult getContentsByPage(long page, long pageSize);
}
