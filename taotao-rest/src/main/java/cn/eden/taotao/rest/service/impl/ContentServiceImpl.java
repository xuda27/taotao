package cn.eden.taotao.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.TbContentMapper;
import cn.eden.taotao.pojo.TbContent;
import cn.eden.taotao.pojo.TbContentExample;
import cn.eden.taotao.pojo.TbContentExample.Criteria;
import cn.eden.taotao.rest.service.ContentService;
/**
 * 根据内容分类id查询内容列表
 * @author Eden
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Override
	public List<TbContent> getContentList(long contentCid) {
		//根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		
		return list;
	}


}
