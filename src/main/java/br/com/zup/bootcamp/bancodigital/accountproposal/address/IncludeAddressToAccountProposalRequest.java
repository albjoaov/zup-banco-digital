package br.com.zup.bootcamp.bancodigital.accountproposal.address;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties (ignoreUnknown = true)
@JsonAutoDetect (fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class IncludeAddressToAccountProposalRequest {

	@Pattern(regexp = "\\d{5}-?\\d{3}")
	@NotBlank
	private String cep;

	@NotBlank
	private String street;

	@NotBlank
	private String neighborhood;

	@NotBlank
	private String complement;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	public Address toModel () {
		return new Address(this.cep, this.street, this.neighborhood, this.complement, this.city, this.state);
	}
}
