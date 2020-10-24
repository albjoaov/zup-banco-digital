package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

import static br.com.zup.bootcamp.bancodigital.shared.RandomUtils.randomNumberGeneratorWithLeadingZeros;
import static java.math.BigDecimal.ZERO;

/**
 * Atributos númericos como `String` para evitar problemas com 0 à esquerda
 */
@Entity
public class Account {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Pattern(regexp = "\\d{4}")
	@Column(nullable = false)
	private String agency;

	@Pattern(regexp = "\\d{8}")
	@Column(nullable = false)
	private String number;

	@Pattern(regexp = "\\d{3}")
	@Column(nullable = false)
	private String bankCode;

	@Column(nullable = false)
	private BigDecimal balance;

	@NotNull
	@OneToOne(optional = false)
	private AccountProposal accountProposal;

	/**
	 * Framework usage only!
	 * Don't use this constructor. Maintain your system health using the right constructors
	 * to keep object's valid state
	 */
	@Deprecated
	public Account () { }

	public Account (@NotNull AccountProposal accountProposal) {
		this.accountProposal = accountProposal;
		this.agency = randomNumberGeneratorWithLeadingZeros(4);
		this.number = randomNumberGeneratorWithLeadingZeros(8);
		this.bankCode = randomNumberGeneratorWithLeadingZeros(3);
		this.balance = ZERO;
	}

}
