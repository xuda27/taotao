package cn.eden.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.search.service.ItemService;
import cn.eden.taotao.util.TaotaoResult;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/importItems")
	@ResponseBody
	public TaotaoResult importItems() {
		return itemService.importItems();
	}
}
