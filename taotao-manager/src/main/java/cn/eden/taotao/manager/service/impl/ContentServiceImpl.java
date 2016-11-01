package cn.eden.taotao.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.eden.taotao.manager.pojo.DataGridResult;
import cn.eden.taotao.manager.service.ContentService;
import cn.eden.taotao.mapper.TbContentMapper;
import cn.eden.taotao.pojo.TbContent;
import cn.eden.taotao.pojo.TbContentExample;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemExample;
import cn.eden.taotao.util.ExceptionUtil;
import cn.eden.taotao.util.HttpClientUtil;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 内容管理
 * 
 * @author Eden
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@Override
	public DataGridResult getContentsByPage(long page, long pageSize) {
		TbContentExample example = new TbContentExample();
		// 开始分页
		PageHelper.startPage((int) page, (int) pageSize);
		// 获取查询结果
		List<TbContent> rows = contentMapper.selectByExample(example);
		DataGridResult dgr = new DataGridResult();
		dgr.setRows(rows);
		// 获取分页信息 商品总数信息
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(rows);
		dgr.setTotal(pageInfo.getTotal());
		return dgr;
	}

	@Override
	public TaotaoResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
