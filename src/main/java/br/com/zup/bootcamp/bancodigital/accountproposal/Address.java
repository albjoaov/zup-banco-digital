package br.com.zup.bootcamp.bancodigital.accountproposal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class Address {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Pattern (regexp = "\\d{5}-?\\d{3}")
	@NotBlank
	@Column (nullable = false)
	private String cep;

	@NotBlank
	@Column(nullable = false)
	private String street;

	@NotBlank
	@Column(nullable = false)
	private String neighborhood;

	@NotBlank
	@Column(nullable = false)
	private String complement;

	@NotBlank
	@Column(nullable = false)
	private String city;

	@NotBlank
	@Column(nullable = false)
	private String state;

	/**
	 * Framework usage only!
	 * Don't use this constructor. Maintain your system health using the right constructors
	 * to keep object's valid state
	 */
	@Deprecated
	public Address () { }

	/**
	 * Used at step two of account proposal process
	 */
	public Address (@Pattern (regexp = "\\d{5}-?\\d{3}") @NotBlank String cep,
	                @NotBlank String street,
	                @NotBlank String neighborhood,
	                @NotBlank String complement,
	                @NotBlank String city,
	                @NotBlank String state) {
		this.cep = cep;
		this.street = street;
		this.neighborhood = neighborhood;
		this.complement = complement;
		this.city = city;
		this.state = state;
	}
}
