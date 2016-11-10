package cn.eden.taotao.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.portal.pojo.Order;
import cn.eden.taotao.portal.service.OrderService;
import cn.eden.taotao.util.CookieUtils;
import cn.eden.taotao.util.HttpClientUtil;
import cn.eden.taotao.util.JsonUtils;
import cn.eden.taotao.util.TaotaoResult;

@Service
public class OrderServiceImpl implements OrderService {
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	@Override
	public String createOrder(Order order, HttpServletRequest request,
			HttpServletResponse response) {
		// 调用taotao-order的服务 提交订单
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL
				+ ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		TaotaoResult result = TaotaoResult.format(json);
		if (result.getStatus() == 200) {
			// 删除cookie
			CookieUtils.deleteCookie(request, response, "TT_CART");
			Object orderId = result.getData();
			return orderId.toString();
		}
		return "";
	}

}
