package cn.eden.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.pojo.DataGridResult;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{id}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long id) {
		return itemService.getItemById(id);
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGridResult getItemsByPage(long page, long rows) {
		return itemService.getItemsByPage(page, rows);
	}
}
