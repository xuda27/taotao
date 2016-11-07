package cn.eden.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.eden.taotao.pojo.TbUser;
import cn.eden.taotao.util.TaotaoResult;

public interface UserService {
	TaotaoResult checkData(String content, Integer type);
	TaotaoResult createUser(TbUser user);
	TaotaoResult userLogin(HttpServletRequest request, HttpServletResponse response, String username, String password);
	TaotaoResult getUserByToken(String token);
	TaotaoResult userLogout(String token);
}
