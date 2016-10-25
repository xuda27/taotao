package cn.eden.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eden.taotao.mapper.ItemParamMapper;
import cn.eden.taotao.mapper.TbItemParamMapper;
import cn.eden.taotao.pojo.ItemParam;
import cn.eden.taotao.pojo.TbItemParam;
import cn.eden.taotao.pojo.TbItemParamExample;
import cn.eden.taotao.pojo.TbItemParamExample.Criteria;
import cn.eden.taotao.service.ItemParamService;
import cn.eden.taotao.util.TaotaoResult;

@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Autowired
	private TbItemParamMapper itemParamMapper;
	@Autowired
	private ItemParamMapper ipm;
	
	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到值
		if(itemParams != null && itemParams.size() > 0) {
			return TaotaoResult.ok(itemParams.get(0));
		}
		return TaotaoResult.ok();
	}


	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		int result = itemParamMapper.insert(itemParam);
		if(result > 0) {
			return TaotaoResult.ok();
		}
		return null;
	}


	@Override
	public List<ItemParam>  getItemParams() {
		return ipm.getItemParams();
	}

}
