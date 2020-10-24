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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.random;
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
		this.agency = this.randomNumberGeneratorWithLeadingZeros(4);
		this.number = this.randomNumberGeneratorWithLeadingZeros(8);
		this.bankCode = this.randomNumberGeneratorWithLeadingZeros(3);
		this.balance = ZERO;
	}

	private String randomNumberGeneratorWithLeadingZeros(int digitAmount) {
		int min = 0;
		int max = 10; // exclusive

		return IntStream
				.generate(() -> (int) (random() * (max - min) + min))
				.limit(digitAmount)
				.mapToObj(String::valueOf)
				.collect(Collectors.joining());
	}
}
