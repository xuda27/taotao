package cn.eden.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.eden.taotao.pojo.TbUser;
import cn.eden.taotao.portal.service.UserService;
import cn.eden.taotao.util.HttpClientUtil;
import cn.eden.taotao.util.TaotaoResult;

@Service
public class UserServiceImpl implements UserService {
	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	public String SSO_USER_TOKEN;
	@Value("${SSO_USER_LOGIN}")
	public String SSO_USER_LOGIN;
	
	@Override
	public TbUser getUserByToken(String token) {
		try {
			//调用sso系统的服务，根据token取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
			//把json转换成TaotaoResult
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
			if(result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
