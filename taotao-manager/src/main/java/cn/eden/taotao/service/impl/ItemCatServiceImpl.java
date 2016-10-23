package cn.eden.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.TbItemCatMapper;
import cn.eden.taotao.pojo.TbItemCat;
import cn.eden.taotao.pojo.TbItemCatExample;
import cn.eden.taotao.pojo.TbItemCatExample.Criteria;
import cn.eden.taotao.pojo.TreeNode;
import cn.eden.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	//根据parentId查询分类列表
	@Override
	public List<TreeNode> getItemCats(Long parentId) {
		//制造查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(parentId);
		//获取查询结果
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//包装查询结果
		List<TreeNode> listResult = new ArrayList<TreeNode>();
		for(TbItemCat itemCat : list) {
			TreeNode node = new TreeNode(itemCat.getId(), itemCat.getName(), 
					itemCat.getIsParent() ? "closed" : "open");
			listResult.add(node);
		}
		return listResult;
	}
}
