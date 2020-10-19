package br.com.zup.bootcamp.bancodigital.accountproposal;

import br.com.zup.bootcamp.bancodigital.validators.MoreThan18Years;
import br.com.zup.bootcamp.bancodigital.validators.Unique;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@JsonAutoDetect (fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class StepOneAccountProposal {

	@NotBlank
	private String name;

	@NotBlank
	private String lastName;

	@Email
	@NotBlank
	@Unique(entityClass = AccountProposal.class, entityField = "email")
	private String email;

	@NotNull
	@Past
	@MoreThan18Years
	private LocalDate birthDate;

	@CPF
	@NotBlank
	@Unique(entityClass = AccountProposal.class, entityField = "cpf")
	private String cpf;

	public AccountProposal createAccountProposal () {
		return new AccountProposal(this.name, this.lastName, this.email, this.birthDate, this.cpf);
	}
}
