package com.imageserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imageserver.models.Image;
import com.imageserver.payload.ImageResponse;
import com.imageserver.repository.ImageRepository;
import com.imageserver.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;

	@Override
	public Image save(Image image) throws NullPointerException {
		if (image == null)
			throw new NullPointerException("Image Data NULL");
		return imageRepository.save(image);
	}

	@Override
	public Image findByFileName(String fileName) {
		return this.imageRepository.findByFileName(fileName);
	}

	@Override
	public Image findByUuid(String uuid) {
		return this.imageRepository.findByUuid(uuid);
	}

	@Override
	public List<ImageResponse> findAllImageResponse() {
		return this.imageRepository.findAllImageResponse();
	}

}
