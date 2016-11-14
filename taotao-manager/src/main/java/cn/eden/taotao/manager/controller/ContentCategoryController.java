package cn.eden.taotao.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eden.taotao.manager.pojo.ContentCatTreeNode;
import cn.eden.taotao.manager.service.ContentCategoryService;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 商品分类管理
 * 
 * @author Eden
 *
 */
@Controller
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService  contentCategoryService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	List<ContentCatTreeNode> getCategoryList(
			@RequestParam(value = "id", defaultValue = "0") long parentId) {
		return contentCategoryService.getCategoryList(parentId);
	}

	@RequestMapping(value = "/content/category/create", method = RequestMethod.POST )
	@ResponseBody
	TaotaoResult insertContentCategory(Long parentId, String name) {
		return contentCategoryService.insertContentCategory(parentId, name);
	}
	
	/**
	 * 删除节点
	 * @param parentId
	 * @param id
	 * @return TaotaoResult
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	TaotaoResult deleteContentCategory(Long parentId, Long id) {
		return contentCategoryService.deleteContentCategory(parentId, id);
	}
	
	@RequestMapping(value = "/content/category/update", method = RequestMethod.POST)
	@ResponseBody
	TaotaoResult updateContentCategory(Long id, String name) {
		return contentCategoryService.updateContentCategory(id, name);
	}
}
