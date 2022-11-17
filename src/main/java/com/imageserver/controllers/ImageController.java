package com.imageserver.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imageserver.helpers.FileNameHelper;
import com.imageserver.models.Image;
import com.imageserver.payload.ImageResponse;
import com.imageserver.service.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	@Autowired
	private ImageService imageService;

	private FileNameHelper fileHelper = new FileNameHelper();

	@PostMapping("/upload")
	public ImageResponse uploadSingleFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Image image = Image.buildImage(file, fileHelper);
		imageService.save(image);
		return new ImageResponse(image);
	}
	
	@GetMapping("/show/{fileName}")
	public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws Exception {
		Image image = imageService.findByFileName(fileName);
		return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
	}
	
	@GetMapping("/images")
	public ResponseEntity<List<ImageResponse>> getAllImageInfo() throws Exception {
		List<ImageResponse> imageResponses = imageService.findAllImageResponse();
		return ResponseEntity.ok().body(imageResponses);
	}
}
