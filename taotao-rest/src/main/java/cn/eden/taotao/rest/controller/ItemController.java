package cn.eden.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.rest.service.ItemService;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 商品信息服务发布url
 * @author Eden
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TaotaoResult getItem(@PathVariable Long itemId) {
		return itemService.getItem(itemId);
	}
	
	@RequestMapping("/itemDesc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		return itemService.getItemDesc(itemId);
	}
	
	@RequestMapping("/itemParamItem/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamItem(@PathVariable Long itemId) {
		return itemService.getItemParamItem(itemId);
	}
}
