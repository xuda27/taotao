package cn.eden.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.eden.taotao.portal.pojo.CartItem;
import cn.eden.taotao.util.TaotaoResult;

public interface CartService {
	TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request,
			HttpServletResponse response);

	List<CartItem> getCartItems(HttpServletRequest request);

	TaotaoResult deleteCartItem(long itemId, HttpServletRequest request,
			HttpServletResponse response);
}
