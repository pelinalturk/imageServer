package com.imageserver.models;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import com.imageserver.helpers.FileNameHelper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images", schema = "public")
public class Image extends BaseEntity {

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "size")
	private long size;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "system_name")
	private String systemName;

	@Lob
	@Column(name = "data")
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] data;

	@Transient
	public static Image build() {
		String uuid = UUID.randomUUID().toString();
		Image image = new Image();
		Date now = new Date();
		image.setUuid(uuid);
		image.setCreatedDate(now);
		image.setUpdatedDate(now);
		image.setCreatedBy("default");
		image.setSystemName("default");
		image.setUpdatedBy("default");
		image.setStatus(true);
		return image;
	}

	@Transient
	public void setFiles(MultipartFile file) {
		setFileType(file.getContentType());
		setSize(file.getSize());
	}

	@Transient
	public byte[] scale(int width, int height) throws Exception {

		if (width == 0 || height == 0)
			return data;

		ByteArrayInputStream in = new ByteArrayInputStream(data);

		try {
			BufferedImage img = ImageIO.read(in);

			java.awt.Image scaledImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
			BufferedImage imgBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			imgBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			ImageIO.write(imgBuff, "jpg", buffer);
			setData(buffer.toByteArray());
			return buffer.toByteArray();

		} catch (Exception e) {
			throw new Exception("IOException in scale");
		}
	}

	@Transient
	public static Image buildImage(MultipartFile file, FileNameHelper helper) {
		String fileName = helper.generateDisplayName(file.getOriginalFilename());

		Image image = Image.build();
		image.setFileName(fileName);
		image.setFiles(file);

		try {
			image.setData(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}