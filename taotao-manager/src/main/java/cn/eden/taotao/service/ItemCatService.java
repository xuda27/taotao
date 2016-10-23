package cn.eden.taotao.service;

import java.util.List;

import cn.eden.taotao.pojo.TreeNode;

public interface ItemCatService {
	List<TreeNode> getItemCats(Long parentId);
}
