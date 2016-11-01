package cn.eden.taotao.rest.service;

import java.util.List;

import cn.eden.taotao.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(long contentCategoryId);
}
