package br.com.zup.bootcamp.bancodigital.account;

import br.com.zup.bootcamp.bancodigital.shared.RandomUtils;
import br.com.zup.bootcamp.bancodigital.shared.mail.Email;
import br.com.zup.bootcamp.bancodigital.shared.mail.Mailer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

@RestController
@RequestMapping ("/account")
@PropertySource (value = {"classpath:ValidationMessages.properties"}, encoding = "UTF-8")
public class AccountFirstAccessController {

	private final Mailer mailer;
	private final EntityManager entityManager;

	public AccountFirstAccessController (Mailer mailer,
	                                     EntityManager entityManager) {
		this.mailer = mailer;
		this.entityManager = entityManager;
	}

	@PostMapping ("/first")
	public ResponseEntity firstAccessAttempt (@Valid FirstAccessAttemptRequest firstAccessAttempt) {
		String sql = "select ap.email, a.id from Account a " +
					" inner join AccountProposal ap " +
					" where ap.email = :emailAttempt and ap.cpf = :cpfAttempt";

		TypedQuery<Tuple> query = this.entityManager.createQuery(sql, Tuple.class);
		query.setParameter("emailAttempt", firstAccessAttempt.getEmail());
		query.setParameter("cpfAttempt", firstAccessAttempt.getCpf());

		try {
			Tuple queryResult = query.getSingleResult();
			String emailFound = queryResult.get("email", String.class);

			String token = RandomUtils.randomNumberGeneratorWithLeadingZeros(6);
			Email email = new Email("Seu token", token, emailFound);

			// Guardar token associado com email em um banco chave e valor
			this.mailer.send(email);
		} catch (NoResultException e) {
			throw new IllegalStateException("Conta não encontrada");
		}

		return ResponseEntity.ok().build();
	}

}
