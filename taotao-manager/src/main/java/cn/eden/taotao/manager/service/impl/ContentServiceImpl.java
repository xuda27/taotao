package cn.eden.taotao.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.eden.taotao.manager.pojo.DataGridResult;
import cn.eden.taotao.manager.service.ContentService;
import cn.eden.taotao.mapper.TbContentMapper;
import cn.eden.taotao.pojo.TbContent;
import cn.eden.taotao.pojo.TbContentExample;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemExample;

/**
 * 内容管理
 * 
 * @author Eden
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public DataGridResult getContentsByPage(long page, long pageSize) {
		TbContentExample example = new TbContentExample();
		// 开始分页
		PageHelper.startPage((int) page, (int) pageSize);
		// 获取查询结果
		List<TbContent> rows = contentMapper.selectByExample(example);
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(rows);
		// 获取分页信息 商品总数信息
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(rows);
		dgr.setTotal(pageInfo.getTotal());
		return dgr;
	}

}
