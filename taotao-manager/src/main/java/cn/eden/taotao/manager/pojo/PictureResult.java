package cn.eden.taotao.manager.pojo;
/**
 * 上传图片的返回结果pojo
 * @author Eden
 *
 */
public class PictureResult {
	private int error;
	private String url;
	private String message;
	public PictureResult() {
		super();
	}
	public PictureResult(int error, String url, String message) {
		super();
		this.error = error;
		this.url = url;
		this.message = message;
	}
	/**
	 * 成功时调用的方法
	 * @param url 
	 * @return
	 */
	public static PictureResult ok(String url) {
		return new PictureResult(0, url, null);
	}
	
	/**
	 * 失败时调用的方法
	 * @param message
	 * @return
	 */
	public static PictureResult error(String message) {
		return new PictureResult(1, null, message);
	}
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
