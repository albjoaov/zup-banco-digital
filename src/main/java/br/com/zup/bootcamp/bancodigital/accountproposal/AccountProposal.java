package br.com.zup.bootcamp.bancodigital.accountproposal;

import br.com.zup.bootcamp.bancodigital.accountproposal.accept.DocumentStatus;
import br.com.zup.bootcamp.bancodigital.accountproposal.address.Address;
import br.com.zup.bootcamp.bancodigital.validators.MoreThan18Years;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.groups.Default;
import java.time.LocalDate;

import static br.com.zup.bootcamp.bancodigital.validators.ValidationUtils.validator;

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
	@Column(nullable = false, unique = true)
	private String email;

	@NotNull
	@Past
	@MoreThan18Years
	@Column(nullable = false)
	private LocalDate birthDate;

	@CPF
	@NotBlank
	@Column(nullable = false, unique = true)
	private String cpf;

	@OneToOne(cascade = CascadeType.MERGE)
	@NotNull(groups = StepTwoGroup.class)
	private Address address;

	@Column
	@NotBlank(groups = StepThreeGroup.class)
	private String cpfFileUrl;

	@Enumerated(value = EnumType.STRING)
	private DocumentStatus documentStatus;

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

	public String getName () {
		return name;
	}

	public String getLastName () {
		return lastName;
	}

	public String getEmail () {
		return email;
	}

	public LocalDate getBirthDate () {
		return birthDate;
	}

	public String getCpf () {
		return cpf;
	}

	public Address getAddress () {
		return address;
	}

	public String getCpfFileUrl () {
		return cpfFileUrl;
	}

	public void setAddress (Address address) {
		var stateErrors = validator.validate(this, Default.class);
		if(!stateErrors.isEmpty()) throw new ConstraintViolationException("Invalid states has been found", stateErrors);

		this.address = address;
	}

	public void setCpfFileUrl (String cpfFileUrl) {
		var stateErrors = validator.validate(this, Default.class, StepTwoGroup.class);
		if(!stateErrors.isEmpty()) throw new ConstraintViolationException("Invalid states has been found", stateErrors);

		this.cpfFileUrl = cpfFileUrl;
	}

	public void setDocumentStatus (DocumentStatus documentStatus) {
		this.documentStatus = documentStatus;
	}

	public void checkIfProcessIsComplete () {
		var stateErrors = validator.validate(this, Default.class, StepTwoGroup.class, StepThreeGroup.class);
		if(!stateErrors.isEmpty()) throw new ConstraintViolationException("Invalid states has been found", stateErrors);
	}
}
