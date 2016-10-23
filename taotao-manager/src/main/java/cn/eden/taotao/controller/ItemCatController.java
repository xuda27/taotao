package cn.eden.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.pojo.TreeNode;
import cn.eden.taotao.service.ItemCatService;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<TreeNode> getItemCats(@RequestParam(value="id", defaultValue="0")Long parentId) {
		return itemCatService.getItemCats(parentId);
	}
}
