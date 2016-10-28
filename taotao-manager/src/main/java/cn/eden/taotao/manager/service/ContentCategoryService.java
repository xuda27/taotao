package cn.eden.taotao.manager.service;

import java.util.List;

import cn.eden.taotao.manager.pojo.ContentCatTreeNode;
import cn.eden.taotao.manager.pojo.TreeNode;
import cn.eden.taotao.util.TaotaoResult;

public interface ContentCategoryService {
	List<ContentCatTreeNode> getCategoryList(long parentId);

	TaotaoResult insertContentCategory(long parentId, String name);
	
	TaotaoResult deleteContentCategory(long parentId, long id);
	
	TaotaoResult updateContentCategory(long id, String name);
}
