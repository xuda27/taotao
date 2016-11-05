package cn.eden.taotao.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemDesc;
import cn.eden.taotao.portal.service.ItemService;
import cn.eden.taotao.util.HttpClientUtil;
import cn.eden.taotao.util.JsonUtils;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 调用rest服务的ItemService
 * 
 * @author Eden
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_ITEM_URL}")
	private String REST_ITEM_URL;

	@Override
	public TbItem getItemById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_ITEM_URL + "/item/" + itemId);
		if (!StringUtils.isBlank(json)) {
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
			if(result.getStatus() == 200) {
				TbItem item = (TbItem) result.getData();
				return item;
			}
		}
		return null;
	}

	@Override
	public String getItemDescById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_ITEM_URL + "/itemDesc/" + itemId);
		if (!StringUtils.isBlank(json)) {
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemDesc.class);
			if(result.getStatus() == 200) {
				TbItemDesc itemDesc = (TbItemDesc) result.getData();
				return itemDesc.getItemDesc();
			}
		}
		return null;
	}

}
