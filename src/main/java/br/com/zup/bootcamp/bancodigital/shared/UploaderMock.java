package br.com.zup.bootcamp.bancodigital.shared;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Profile ("dev")
public class UploaderMock implements Uploader {

	@Override
	public String upload (MultipartFile multipartFile) {
		String fakeStorageServiceURL = "https://s3.amazon.com/";
		return fakeStorageServiceURL + UUID.randomUUID() + multipartFile.getOriginalFilename();
	}

}
