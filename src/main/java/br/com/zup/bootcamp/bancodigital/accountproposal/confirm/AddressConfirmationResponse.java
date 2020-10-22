package br.com.zup.bootcamp.bancodigital.accountproposal.confirm;

import br.com.zup.bootcamp.bancodigital.accountproposal.address.Address;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect (fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AddressConfirmationResponse {

	private String cep;
	private String street;
	private String neighborhood;
	private String complement;
	private String city;
	private String state;

	public AddressConfirmationResponse (Address address) {
		this.cep = address.getCep();
		this.street = address.getStreet();
		this.neighborhood = address.getNeighborhood();
		this.complement = address.getComplement();
		this.city = address.getCity();
		this.state = address.getState();
	}
}
