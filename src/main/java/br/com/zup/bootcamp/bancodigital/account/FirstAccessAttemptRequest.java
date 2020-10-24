package br.com.zup.bootcamp.bancodigital.account;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FirstAccessAttemptRequest {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@CPF
	private String cpf;

	public String getEmail () {
		return email;
	}

	public String getCpf () {
		return cpf;
	}
}
