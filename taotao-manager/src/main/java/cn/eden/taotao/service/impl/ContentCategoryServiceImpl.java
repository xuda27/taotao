package cn.eden.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.TbContentCategoryMapper;
import cn.eden.taotao.pojo.TbContentCategory;
import cn.eden.taotao.pojo.TbContentCategoryExample;
import cn.eden.taotao.pojo.TbContentCategoryExample.Criteria;
import cn.eden.taotao.pojo.TreeNode;
import cn.eden.taotao.service.ContentCategoryService;

/**
 * 内容分类管理
 * 
 * @author Eden
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<TreeNode> getCategoryList(long parentId) {
		//根据parentId查询数据库
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//创建返回值list 并包装数据
		List<TreeNode> listResult = new ArrayList<TreeNode>();
		for(TbContentCategory contentCategory : list) {
			TreeNode node = new TreeNode();
			node.setId(contentCategory.getId());
			node.setState(contentCategory.getIsParent() ? "closed" : "open");
			node.setText(contentCategory.getName());
			listResult.add(node);
		}
		return listResult;
	}

}
