package cn.eden.taotao.test;

import org.junit.Test;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;

public class CosTest {
	@Test
	public void testUpload() {
		// 设置用户属性, 包括appid, secretId和SecretKey
        // 这些属性可以通过cos控制台获取(https://console.qcloud.com/cos)
        long appId = 1252795366;
        String secretId = "AKIDUp60iQWY5Z3XuBNadeeXM9rRwPxQ6x24";
        String secretKey = "750oWH6rTuAF9sRnyzqgNwKdrv11vqiV";
        // 设置要操作的bucket
        String bucketName = "edenbucket";
        // 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如广州(gz), 天津(tj)
        clientConfig.setRegion("gz");
        // 初始化秘钥信息
        Credentials cred = new Credentials(appId, secretId, secretKey);
        // 初始化cosClient
        COSClient cosClient = new COSClient(clientConfig, cred);
        ///////////////////////////////////////////////////////////////
        // 文件操作 //
        ///////////////////////////////////////////////////////////////
        // 1. 上传文件(默认不覆盖)
        // 将本地的local_file_1.txt上传到bucket下的根分区下,并命名为sample_file.txt
        // 默认不覆盖, 如果cos上已有文件, 则返回错误
        String cosFilePath = "/test.txt";
        String localFilePath1 = "src/test/resources/1.txt";
        UploadFileRequest uploadFileRequest =
                new UploadFileRequest(bucketName, cosFilePath, localFilePath1);
        uploadFileRequest.setEnableSavePoint(false);
        uploadFileRequest.setEnableShaDigest(false);
        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
        System.out.println("upload file ret:" + uploadFileRet);
	}
}
