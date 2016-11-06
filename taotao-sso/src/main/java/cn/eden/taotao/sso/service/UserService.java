package cn.eden.taotao.sso.service;

import cn.eden.taotao.pojo.TbUser;
import cn.eden.taotao.util.TaotaoResult;

public interface UserService {
	TaotaoResult checkData(String content, Integer type);
	TaotaoResult  createUser(TbUser user);
	TaotaoResult userLogin(String username, String password);
	TaotaoResult getUserByToken(String token);
}
