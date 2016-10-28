package cn.eden.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.TbContentCategoryMapper;
import cn.eden.taotao.pojo.TbContentCategory;
import cn.eden.taotao.pojo.TbContentCategoryExample;
import cn.eden.taotao.pojo.TbContentCategoryExample.Criteria;
import cn.eden.taotao.pojo.ContentCatTreeNode;
import cn.eden.taotao.service.ContentCategoryService;
import cn.eden.taotao.util.ExceptionUtil;
import cn.eden.taotao.util.TaotaoResult;

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
	public List<ContentCatTreeNode> getCategoryList(long parentId) {
		// 根据parentId查询数据库
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper
				.selectByExample(example);
		// 创建返回值list 并包装数据
		List<ContentCatTreeNode> listResult = new ArrayList<ContentCatTreeNode>();
		for (TbContentCategory contentCategory : list) {
			ContentCatTreeNode node = new ContentCatTreeNode();
			node.setId(contentCategory.getId());
			node.setState(contentCategory.getIsParent() ? "closed" : "open");
			node.setText(contentCategory.getName());
			node.setParentId(parentId);
			listResult.add(node);
		}
		return listResult;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		// 创建一个pojo
		TbContentCategory contentCategory = null;
		try {
			contentCategory = new TbContentCategory();
			contentCategory.setCreated(new Date());
			contentCategory.setIsParent(false);
			contentCategory.setName(name);
			contentCategory.setParentId(parentId);
			contentCategory.setUpdated(new Date());
			// 状态 正常为1
			contentCategory.setStatus(1);
			contentCategory.setSortOrder(1);
			// 添加记录
			contentCategoryMapper.insert(contentCategory);
			// 查看父节点的isparent列是否为true，如果是false改成true
			TbContentCategory parentCat = contentCategoryMapper
					.selectByPrimaryKey(parentId);
			if (!parentCat.getIsParent()) {
				parentCat.setIsParent(true);
				// 更新父节点
				contentCategoryMapper.updateByPrimaryKey(parentCat);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory(long parentId, long id) {
		contentCategoryMapper.deleteByPrimaryKey(id);
		// 判断父节点下是否还有子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper
				.selectByExample(example);
		// 父节点
		TbContentCategory parentCat = contentCategoryMapper
				.selectByPrimaryKey(parentId);
		// 如果没有子节点，设置为false
		if (list != null && list.size() > 0) {
			parentCat.setIsParent(true);
		} else {
			parentCat.setIsParent(false);
		}
		return TaotaoResult.ok();
	}

}
