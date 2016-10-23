package cn.eden.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import cn.eden.taotao.pojo.PictureResult;

public interface PictureService {
	PictureResult uploadPicture(MultipartFile uploadFile);
}
