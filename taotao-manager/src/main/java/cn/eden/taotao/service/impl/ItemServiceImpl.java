package cn.eden.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.eden.taotao.mapper.TbItemMapper;
import cn.eden.taotao.pojo.DataGridResult;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemExample;
import cn.eden.taotao.service.ItemService;
/**
 * 测试商品 
 * @author Eden
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public TbItem getItemById(Long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		return item;
	}
	
	@Override
	public DataGridResult getItemsByPage(long page, long pageSize) {
		TbItemExample example = new TbItemExample();
		//开始分页
		PageHelper.startPage((int)page, (int)pageSize);
		//获取查询结果
		List<TbItem> rows = itemMapper.selectByExample(example);
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(rows);
		//获取分页信息  商品总数信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(rows);
		dgr.setTotal(pageInfo.getTotal());
		return dgr;
	}

}
