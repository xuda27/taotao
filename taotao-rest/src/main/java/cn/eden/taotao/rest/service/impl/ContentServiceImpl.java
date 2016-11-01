package cn.eden.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.TbContentMapper;
import cn.eden.taotao.pojo.TbContent;
import cn.eden.taotao.pojo.TbContentExample;
import cn.eden.taotao.pojo.TbContentExample.Criteria;
import cn.eden.taotao.rest.dao.JedisClient;
import cn.eden.taotao.rest.service.ContentService;
import cn.eden.taotao.util.JsonUtils;

/**
 * 根据内容分类id查询内容列表（大广告位）
 * 
 * @author Eden
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public List<TbContent> getContentList(long contentCategoryId) {
		try {
			// 从缓存中取内容
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY,
					contentCategoryId + "");
			if (!StringUtils.isBlank(result)) {
				// 把字符串转换成list
				List<TbContent> resultList = JsonUtils.jsonToList(result,
						TbContent.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// 根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCategoryId);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(example);

		try {
			// 向缓存中添加内容
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "",
					cacheString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list;
	}

}
