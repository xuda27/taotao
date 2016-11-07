package cn.eden.taotao.portal.service;

import cn.eden.taotao.pojo.TbUser;

public interface UserService {
	TbUser getUserByToken(String token);
}
