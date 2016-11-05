package cn.eden.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.TbItemDescMapper;
import cn.eden.taotao.mapper.TbItemMapper;
import cn.eden.taotao.mapper.TbItemParamItemMapper;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemDesc;
import cn.eden.taotao.pojo.TbItemParamItem;
import cn.eden.taotao.pojo.TbItemParamItemExample;
import cn.eden.taotao.pojo.TbItemParamItemExample.Criteria;
import cn.eden.taotao.rest.dao.JedisClient;
import cn.eden.taotao.rest.service.ItemService;
import cn.eden.taotao.util.JsonUtils;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 搜索结果=>商品详细页商品信息
 * 
 * @author Eden
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Autowired
	private JedisClient jedisClient;

	// redis key的过期时间
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Override
	public TaotaoResult getItem(long itemId) {
		// 添加缓存逻辑
		// key-value 数据库表名:字段名:字段属性值 = item的json
		// 从缓存中取商品信息，商品id对应的信息
		String json = jedisClient.get("tb_item:id:" + itemId);
		// 判断是否有值
		if (!StringUtils.isBlank(json)) {
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			return TaotaoResult.ok(item);
		}
		// 根据商品id查询商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		// 把商品信息写入redis缓存中
		jedisClient.set("tb_item:id:" + itemId, JsonUtils.objectToJson(item));
		// 设置key的有效期
		jedisClient.expire("tb_item:id:" + itemId, REDIS_ITEM_EXPIRE);
		return TaotaoResult.ok(item);
	}

	@Override
	public TaotaoResult getItemDesc(long itemId) {
		// 添加缓存逻辑
		// key-value 数据库表名:字段名:字段属性值 = item的json
		// 从缓存中取商品信息，商品id对应的信息
		String json = jedisClient.get("tb_item_desc:item_id:" + itemId);
		// 判断是否有值
		if (!StringUtils.isBlank(json)) {
			TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
			return TaotaoResult.ok(itemDesc);
		}
		// 根据商品id查询商品信息
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

		// 把商品信息写入redis缓存中
		jedisClient.set("tb_item_desc:item_id:" + itemId,
				JsonUtils.objectToJson(itemDesc));
		// 设置key的有效期
		jedisClient.expire("tb_item_desc:item_id:" + itemId, REDIS_ITEM_EXPIRE);
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult getItemParamItem(long itemId) {
		// 添加缓存逻辑
		// key-value 数据库表名:字段名:字段属性值 = item的json
		// 从缓存中取商品信息，商品id对应的信息
		String json = jedisClient.get("tb_item_param_item:item_id:" + itemId);
		// 判断是否有值
		if (!StringUtils.isBlank(json)) {
			TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json,
					TbItemParamItem.class);
			return TaotaoResult.ok(itemParamItem);
		}
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		// 根据商品id查询商品信息
		List<TbItemParamItem> itemParamItems = itemParamItemMapper
				.selectByExampleWithBLOBs(example);
		TbItemParamItem itemParamItem = null;
		if (itemParamItems != null && itemParamItems.size() > 0) {
			itemParamItem = itemParamItems.get(0);
		} else {
			return TaotaoResult.build(400, "数据库中没有该商品的规格参数");
		}
		// 把商品信息写入redis缓存中
		jedisClient.set("tb_item_param_item:item_id:" + itemId,
				JsonUtils.objectToJson(itemParamItem));
		// 设置key的有效期
		jedisClient.expire("tb_item_param_item:item_id:" + itemId,
				REDIS_ITEM_EXPIRE);
		return TaotaoResult.ok(itemParamItem);
	}
}
