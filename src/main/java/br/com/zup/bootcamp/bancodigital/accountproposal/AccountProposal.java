package br.com.zup.bootcamp.bancodigital.accountproposal;

import br.com.zup.bootcamp.bancodigital.accountproposal.address.Address;
import br.com.zup.bootcamp.bancodigital.validators.MoreThan18Years;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
public class AccountProposal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotBlank
	@Column(nullable = false)
	private String lastName;

	@Email
	@NotBlank
	@Column(nullable = false)
	private String email;

	@NotNull
	@Past
	@MoreThan18Years
	@Column(nullable = false)
	private LocalDate birthDate;

	@CPF
	@NotBlank
	@Column(nullable = false)
	private String cpf;

	@OneToOne(cascade = CascadeType.MERGE)
	private Address address;

	@Column (nullable = false)
	@NotBlank
	private String cpfFileUrl;

	/**
	 * Framework usage only!
	 * Don't use this constructor. Maintain your system health using the right constructors
	 * to keep object's valid state
	 */
	@Deprecated
	public AccountProposal () { }

	/**
	 * Used to init account proposal process
	 * @param name customer name
	 * @param lastName customer last name
 	 * @param email customer email
	 * @param birthDate customer birthDate
	 * @param cpf customer cpf
	 */
	public AccountProposal (@NotBlank String name,
	                        @NotBlank String lastName,
	                        @Email @NotBlank String email,
	                        @NotNull @Past LocalDate birthDate,
	                        @CPF @NotBlank String cpf) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.cpf = cpf;
	}

	public Long getId () {
		return id;
	}

	public void setAddress (Address address) {
		this.address = address;
	}

	public void setCpfFileUrl (String cpfFileUrl) {
		this.cpfFileUrl = cpfFileUrl;
	}
}
