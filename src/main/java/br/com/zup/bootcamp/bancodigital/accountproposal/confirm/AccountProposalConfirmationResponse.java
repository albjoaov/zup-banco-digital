package br.com.zup.bootcamp.bancodigital.accountproposal.confirm;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDate;

@JsonAutoDetect (fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AccountProposalConfirmationResponse {

	private final String name;

	private final String lastName;

	private final String email;

	private final LocalDate birthDate;

	private final String cpf;

	private final AddressConfirmationResponse address;

	private final String cpfFileUrl;

	public AccountProposalConfirmationResponse (AccountProposal accountProposal) {
		this.name = accountProposal.getName();
		this.lastName = accountProposal.getLastName();
		this.email = accountProposal.getEmail();
		this.birthDate = accountProposal.getBirthDate();
		this.cpf = accountProposal.getCpf();
		this.address = new AddressConfirmationResponse(accountProposal.getAddress());
		this.cpfFileUrl = accountProposal.getCpfFileUrl();
	}
}
