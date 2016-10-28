package cn.eden.taotao.manager.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.manager.pojo.ItemParam;
import cn.eden.taotao.manager.service.ItemParamService;
import cn.eden.taotao.pojo.TbItemParam;
import cn.eden.taotao.util.TaotaoResult;

@Controller
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/item/param/query/itemcatid/{cid}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long cid) {
		return itemParamService.getItemParamByCid(cid);
	}
	
	@RequestMapping("/item/param/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData) {
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		return itemParamService.insertItemParam(itemParam);
	}
	
	@RequestMapping("/item/param/list")
	@ResponseBody
	public List<ItemParam>  getItemParams() {
		return itemParamService.getItemParams();
	}
}
