package cn.eden.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.rest.dao.JedisClient;
import cn.eden.taotao.rest.service.RedisService;
import cn.eden.taotao.util.ExceptionUtil;
import cn.eden.taotao.util.TaotaoResult;
/**
 * redis相关业务
 * @author Eden
 *
 */
@Service
public class RedisServiceImpl implements RedisService {
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	/**
	 * 
	 */
	@Override
	public TaotaoResult syncContent(long contentCategoryId) {
		try {
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "");
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
