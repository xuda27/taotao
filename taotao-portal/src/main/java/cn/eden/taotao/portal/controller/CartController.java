package cn.eden.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.eden.taotao.portal.pojo.CartItem;
import cn.eden.taotao.portal.service.CartService;
import cn.eden.taotao.util.TaotaoResult;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;

	/**
	 * 添加购物车商品成功
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId,
			@RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = cartService.addCartItem(itemId, num, request,
				response);
		return "cartSuccess";
	}

	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request, Model model) {
		List<CartItem> list = cartService.getCartItems(request);
		model.addAttribute("cartList", list);
		return "cart";
	}

	/**
	 * 删除购物车商品
	 * 
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
			HttpServletRequest request, HttpServletResponse response) {
		cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}
}
