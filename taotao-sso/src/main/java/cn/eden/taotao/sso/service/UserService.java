package cn.eden.taotao.sso.service;

import cn.eden.taotao.util.TaotaoResult;

public interface UserService {
	TaotaoResult checkData(String content, Integer type);
}
