package cn.eden.taotao.pojo;

public class TreeNode {
	private long id;
	private String text; // 显示节点文本
	private String state; // 节点状态

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public TreeNode() {
		super();
	}

	public TreeNode(long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}

}
