package cn.eden.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.TbItemMapper;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.rest.service.ItemService;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 搜索结果=>商品详细页商品信息 
 * @author Eden
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TaotaoResult getItem(long itemId) {
		//根据商品id查询商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return TaotaoResult.ok(item);
	}

}
