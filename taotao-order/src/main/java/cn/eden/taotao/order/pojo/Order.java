package cn.eden.taotao.order.pojo;

import java.util.List;

import cn.eden.taotao.pojo.TbOrder;
import cn.eden.taotao.pojo.TbOrderItem;
import cn.eden.taotao.pojo.TbOrderShipping;

public class Order extends TbOrder {
	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

}
