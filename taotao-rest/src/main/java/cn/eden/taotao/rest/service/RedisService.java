package cn.eden.taotao.rest.service;

import cn.eden.taotao.util.TaotaoResult;

public interface RedisService {
	//将数据库更新的内容信息同步到redis中
	TaotaoResult syncContent(long contentCategoryId);
}
