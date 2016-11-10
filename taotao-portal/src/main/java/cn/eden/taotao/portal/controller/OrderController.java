package cn.eden.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.eden.taotao.portal.pojo.CartItem;
import cn.eden.taotao.portal.pojo.Order;
import cn.eden.taotao.portal.service.CartService;
import cn.eden.taotao.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	/**
	 * 支付页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 根据用户信息，取出用户的收货地址列表
		// 静态数据模拟
		// 从cookie中取出商品列表
		List<CartItem> itemList = cartService.getCartItems(request);
		model.addAttribute("cartList", itemList);
		return "order-cart";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createOrder(Order order, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String orderId = orderService.createOrder(order, request, response);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			// 增加3天
			model.addAttribute("date",
					new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "wori");
			return "error/exception";
		}
	}
}
