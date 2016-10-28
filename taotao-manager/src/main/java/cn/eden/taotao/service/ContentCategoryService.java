package cn.eden.taotao.service;

import java.util.List;

import cn.eden.taotao.pojo.TreeNode;
import cn.eden.taotao.util.TaotaoResult;

public interface ContentCategoryService {
	List<TreeNode> getCategoryList(long parentId);

	TaotaoResult insertContentCategory(long parentId, String name);
}
