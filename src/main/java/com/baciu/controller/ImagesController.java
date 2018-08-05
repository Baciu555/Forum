package com.baciu.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImagesController {
	
	private final String IMAGE_NOT_FOUND = "404.jpg";
	
	@RequestMapping(value = "uploads/{imageName:.+}")
	public byte[] getAvatar(@PathVariable(value = "imageName") String imageName) throws IOException {
		Path uploadedFolder = Paths.get("uploads");
		File serverFile = new File(uploadedFolder + "/" + imageName);
		
		byte[] avatar;
		try {
			avatar = Files.readAllBytes(serverFile.toPath());
		} catch (IOException ioException) {
			ioException.printStackTrace();
			File notFound = new File(uploadedFolder + "/" + IMAGE_NOT_FOUND);
			byte[] notFoundImage = Files.readAllBytes(notFound.toPath());
			return notFoundImage;
		}
		
		return avatar;
	}

}
