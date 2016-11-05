package cn.eden.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemDesc;
import cn.eden.taotao.pojo.TbItemParamItem;
import cn.eden.taotao.portal.service.ItemService;
import cn.eden.taotao.util.HttpClientUtil;
import cn.eden.taotao.util.JsonUtils;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 调用rest服务的ItemService
 * 
 * @author Eden
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_ITEM_URL}")
	private String REST_ITEM_URL;

	@Override
	public TbItem getItemById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_ITEM_URL + "/item/" + itemId);
		if (!StringUtils.isBlank(json)) {
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
			if (result.getStatus() == 200) {
				TbItem item = (TbItem) result.getData();
				return item;
			}
		}
		return null;
	}

	@Override
	public String getItemDescById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_ITEM_URL + "/itemDesc/"
				+ itemId);
		if (!StringUtils.isBlank(json)) {
			TaotaoResult result = TaotaoResult.formatToPojo(json,
					TbItemDesc.class);
			if (result.getStatus() == 200) {
				TbItemDesc itemDesc = (TbItemDesc) result.getData();
				return itemDesc.getItemDesc();
			}
		}
		return null;
	}

	@Override
	public String getItemParamItemById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_ITEM_URL + "/itemParamItem/"
				+ itemId);
		if(StringUtils.isBlank(json)){
			return "";
		}
		// 把json转换成java对象
		TaotaoResult result = TaotaoResult.formatToPojo(json,
				TbItemParamItem.class);
		if (result.getStatus() == 200) {
			TbItemParamItem itemParamItem = (TbItemParamItem) result.getData();
			String paramData = itemParamItem.getParamData();
			// 生成html
			// 把规格参数json数据转换成java对象
			List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
			StringBuffer sb = new StringBuffer();
			sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
			sb.append("    <tbody>\n");
			for (Map m1 : jsonList) {
				sb.append("        <tr>\n");
				sb.append("            <th class=\"tdTitle\" colspan=\"2\">"
						+ m1.get("group") + "</th>\n");
				sb.append("        </tr>\n");
				List<Map> list2 = (List<Map>) m1.get("params");
				for (Map m2 : list2) {
					sb.append("        <tr>\n");
					sb.append("            <td class=\"tdTitle\">"
							+ m2.get("k") + "</td>\n");
					sb.append("            <td>" + m2.get("v") + "</td>\n");
					sb.append("        </tr>\n");
				}
			}
			sb.append("    </tbody>\n");
			sb.append("</table>");
			// 返回html片段
			return sb.toString();
		}
		return "";
	}

}
