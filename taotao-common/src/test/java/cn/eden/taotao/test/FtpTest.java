package cn.eden.taotao.test;

import java.io.FileInputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

/**
 * 使用commons-net包 测试ftp服务器
 * @author Eden
 *
 */
public class FtpTest {
	@Test
	public void testFtp() throws Exception {
		//1. 连接ftp服务器
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("123.207.27.28", 21);
		//2. 登录ftp服务器
		ftpClient.login("ftpuser", "a");
		//3. 读取本地文件
		FileInputStream fis = new FileInputStream("D:\\images\\1.jpg");
		//4. 上传文件
		//指定上传目录
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//指定上传类型
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.storeFile("3.jpg", fis);
		//5.退出登录
		ftpClient.logout();
		
	}
}
