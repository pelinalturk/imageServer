package com.imageserver.service;

import java.util.List;

import com.imageserver.models.Image;
import com.imageserver.payload.ImageResponse;


public interface ImageService {

	public Image save(Image image);

	public Image findByFileName(String fileName);

	public Image findByUuid(String uuid);
	
	public List<ImageResponse> findAllImageResponse();

}
