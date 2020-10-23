package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect (fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DocumentIntegrityCheckerRequest {

	private String cpf;
	private String cpfFileURL;

	public DocumentIntegrityCheckerRequest (AccountProposal accountProposal) {
		this.cpf = accountProposal.getCpf();
		this.cpfFileURL = accountProposal.getCpfFileUrl();
	}
}
