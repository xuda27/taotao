package cn.eden.taotao.manager.service;

import org.springframework.web.multipart.MultipartFile;

import cn.eden.taotao.manager.pojo.PictureResult;

public interface PictureService {
	PictureResult uploadPicture(MultipartFile uploadFile);
}
