package cn.eden.taotao.manager.service;

import java.util.List;

import cn.eden.taotao.manager.pojo.TreeNode;

public interface ItemCatService {
	List<TreeNode> getItemCats(Long parentId);
}
