package br.com.zup.bootcamp.bancodigital.accountproposal.document;

import br.com.zup.bootcamp.bancodigital.shared.upload.Uploader;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class UploadCpfRequest {

	@NotNull
	private MultipartFile cpfFile;

	public String uploadFileAndGetLocation (Uploader uploader) {
		return uploader.upload(cpfFile);
	}

	// Usado na desserialização pelo Spring
	public void setCpfFile (MultipartFile cpfFile) {
		this.cpfFile = cpfFile;
	}
}
