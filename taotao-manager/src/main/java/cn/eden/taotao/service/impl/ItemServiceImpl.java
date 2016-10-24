package cn.eden.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.eden.taotao.mapper.TbItemDescMapper;
import cn.eden.taotao.mapper.TbItemMapper;
import cn.eden.taotao.pojo.DataGridResult;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemDesc;
import cn.eden.taotao.pojo.TbItemDescExample;
import cn.eden.taotao.pojo.TbItemDescExample.Criteria;
import cn.eden.taotao.pojo.TbItemExample;
import cn.eden.taotao.service.ItemService;
import cn.eden.taotao.util.ExceptionUtil;
import cn.eden.taotao.util.IDUtils;
import cn.eden.taotao.util.TaotaoResult;
/**
 * 商品 
 * @author Eden
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;

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

	@Override
	public TaotaoResult addItem(TbItem item, TbItemDesc itemDesc) {
		try {
			//生成商品id
			//可以使用redis的自增长key,没有redis之前使用时间+随机数策略生成
			Long itemId = IDUtils.genItemId();
			//补全完整的字段
			//item
			item.setId(itemId);
			item.setStatus((byte)1);
			item.setCreated(new Date());
			item.setUpdated(new Date());
			itemMapper.insert(item);
			//itemDesc
			itemDesc.setItemId(itemId);
			itemDesc.setCreated(new Date());
			itemDesc.setUpdated(new Date());
			itemDescMapper.insert(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	
	@Override
	public TbItemDesc listItemDesc(long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public TaotaoResult updateItem(TbItem item, TbItemDesc itemDesc) {
		try {
			//更新商品
			TbItemExample e = new TbItemExample();
			cn.eden.taotao.pojo.TbItemExample.Criteria c = e.createCriteria();
			c.andIdEqualTo(item.getId());
			item.setCreated(new Date());
			item.setUpdated(new Date());
			item.setStatus((byte)1);
			itemMapper.updateByExample(item, e);
			
			//更新商品描述
			TbItemDescExample de = new TbItemDescExample();
			Criteria dc = de.createCriteria();
			dc.andItemIdEqualTo(item.getId());
			itemDesc.setItemId(item.getId());
			itemDesc.setCreated(new Date());
			itemDesc.setUpdated(new Date());
			itemDescMapper.updateByExample(itemDesc, de);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItem(String ids) {
		try {
			String[] idsArray = ids.split(",");
			List<Long> values = new ArrayList<Long>();
			for(String id : idsArray) {
				values.add(Long.parseLong(id));
			}
			TbItemExample e = new TbItemExample();
			cn.eden.taotao.pojo.TbItemExample.Criteria c = e.createCriteria();
			c.andIdIn(values);
			itemMapper.deleteByExample(e);
			
			
			TbItemDescExample de = new TbItemDescExample();
			Criteria dc = de.createCriteria();
			dc.andItemIdIn(values);
			itemDescMapper.deleteByExample(de);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return TaotaoResult.ok();
	}

	
}
