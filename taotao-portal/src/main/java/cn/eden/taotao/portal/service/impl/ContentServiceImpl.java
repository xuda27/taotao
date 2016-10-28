package cn.eden.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.pojo.TbContent;
import cn.eden.taotao.portal.service.ContentService;
import cn.eden.taotao.util.HttpClientUtil;
import cn.eden.taotao.util.JsonUtils;
import cn.eden.taotao.util.TaotaoResult;

@Service
public class ContentServiceImpl implements ContentService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL; // 服务层基础url
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL; //大广告位
	
	//jsp页面要求的json格式
//	{
//        "srcB": "http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg", 
//        "height": 240, 
//        "alt": "", 
//        "width": 670, 
//        "src": "http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg", 
//        "widthB": 550, 
//        "href": "http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE", 
//        "heightB": 240
//    }
	@Override
	public String getContentList() {
		// 调用服务层 查询商品内容信息（即大广告位）
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		try {
			// 把字符串转换成TaotaoResult
			TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
			// 取出内容列表
			List<TbContent> list = (List<TbContent>) taotaoResult.getData();
			List<Map> resultList = new ArrayList<Map>(); 
			// 创建一个jsp页码要求的pojo列表
			for(TbContent tbContent : list) {
				Map map = new HashMap();
				map.put("srcB", tbContent.getPic2());
				map.put("height", 240);
				map.put("alt", tbContent.getTitle());
				map.put("width", 670);
				map.put("src", tbContent.getPic());
				map.put("widthB", 550);
				map.put("href", tbContent.getUrl());
				map.put("heightB", 240);
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
