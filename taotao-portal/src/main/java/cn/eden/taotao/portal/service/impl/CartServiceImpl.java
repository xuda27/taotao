package cn.eden.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.portal.pojo.CartItem;
import cn.eden.taotao.portal.service.CartService;
import cn.eden.taotao.util.CookieUtils;
import cn.eden.taotao.util.HttpClientUtil;
import cn.eden.taotao.util.JsonUtils;
import cn.eden.taotao.util.TaotaoResult;

/**
 * 购物车Service
 * 
 * @author Eden
 *
 */
@Service
public class CartServiceImpl implements CartService {
	@Value("${REST_ITEM_URL}")
	private String REST_ITEM_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	@Override
	public TaotaoResult addCartItem(long itemId, int num,
			HttpServletRequest request, HttpServletResponse response) {
		CartItem cartItem = null;
		// 取购物车商品列表
		List<CartItem> itemList = getCartItems(request);

		// 判断购物车商品列表中是否存在此商品，
		for (CartItem item : itemList) {
			// 1.如果存在则添加商品数量
			if (item.getId() == itemId) {
				item.setNum(item.getNum() + num);
				cartItem = item;
				break;
			}
			// 2.不存在，根据id查询商品信息，添加到购物车列表
			if (cartItem == null) {
				cartItem = new CartItem();
				String json = HttpClientUtil.doGet(REST_ITEM_URL
						+ ITEM_INFO_URL + itemId);
				// 把json转换成java对象
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,
						TbItem.class);
				if (taotaoResult.getStatus() == 200) {
					TbItem tbItem = (TbItem) taotaoResult.getData();
					cartItem.setId(tbItem.getId());
					cartItem.setTitle(tbItem.getTitle());
					cartItem.setNum(num);
					cartItem.setPrice(tbItem.getPrice());
					cartItem.setImage(tbItem.getImage() == null ? "" : tbItem
							.getImage().split(",")[0]);
				}
			}
			// 添加到购物车列表
			itemList.add(cartItem);
		}
		if (itemList == null || itemList.size() == 0) {
			cartItem = new CartItem();
			String json = HttpClientUtil.doGet(REST_ITEM_URL + ITEM_INFO_URL
					+ itemId);
			// 把json转换成java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,
					TbItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItem tbItem = (TbItem) taotaoResult.getData();
				cartItem.setId(tbItem.getId());
				cartItem.setTitle(tbItem.getTitle());
				cartItem.setNum(num);
				cartItem.setPrice(tbItem.getPrice());
				cartItem.setImage(tbItem.getImage() == null ? "" : tbItem
						.getImage().split(",")[0]);
			}
			// 添加到购物车列表
			itemList.add(cartItem);
		}
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART",
				JsonUtils.objectToJson(itemList), true);
		return TaotaoResult.ok();
	}

	/**
	 * 从cookie中取购物车商品
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public List<CartItem> getCartItems(HttpServletRequest request) {
		// 从Cookie中取商品列表 需要编码
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (StringUtils.isBlank(cartJson) || cartJson.trim().equals("[]")) {
			return new ArrayList<CartItem>();
		}
		try {
			// 把json转换成商品列表
			List<CartItem> list = JsonUtils
					.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<CartItem>();
	}

	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		//从cookie中去购物车商品列表
		List<CartItem> itemList = getCartItems(request);
		//从列表中找到此商品
		for(CartItem cartItem : itemList) {
			if(cartItem.getId() == itemId) {
				itemList.remove(cartItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		return TaotaoResult.ok();
	}

	
}
