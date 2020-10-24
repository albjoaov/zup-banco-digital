package br.com.zup.bootcamp.bancodigital.shared.upload;

import org.springframework.web.multipart.MultipartFile;

public interface Uploader {

	String upload (MultipartFile multipartFile);
}
