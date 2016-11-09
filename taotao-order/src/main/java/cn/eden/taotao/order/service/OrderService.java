package cn.eden.taotao.order.service;

import java.util.List;

import cn.eden.taotao.pojo.TbOrder;
import cn.eden.taotao.pojo.TbOrderItem;
import cn.eden.taotao.pojo.TbOrderShipping;
import cn.eden.taotao.util.TaotaoResult;

public interface OrderService {
	TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItems, TbOrderShipping orderShipping);
}
