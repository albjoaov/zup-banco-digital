package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
@JsonAutoDetect (fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DocumentIntegrityCheckerResponse {
	private DocumentStatus documentStatus;

	public DocumentStatus getDocumentStatus () {
		return documentStatus;
	}
}
