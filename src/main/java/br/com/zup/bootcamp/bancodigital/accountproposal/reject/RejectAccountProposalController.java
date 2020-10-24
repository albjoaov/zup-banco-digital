package br.com.zup.bootcamp.bancodigital.accountproposal.reject;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import br.com.zup.bootcamp.bancodigital.shared.mail.Email;
import br.com.zup.bootcamp.bancodigital.shared.mail.Mailer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping ("/account-proposal")
@PropertySource (value = { "classpath:ValidationMessages.properties", "classpath:MailConfig.properties" }, encoding = "UTF-8")
public class RejectAccountProposalController {

	@Value ("${account-proposal.id.invalid}")
	private String invalidAccountProposalIdMessage;

	@Value("${account-reject.mail.subject}")
	private String rejectMailSubject;
	@Value("${account-reject.mail.body}")
	private String rejectMailBody;

	private final Mailer mailer;
	private final EntityManager entityManager;

	public RejectAccountProposalController (Mailer mailer, EntityManager entityManager) {
		this.mailer = mailer;
		this.entityManager = entityManager;
	}

	@PostMapping ("/{id}/step-five/reject")
	public ResponseEntity rejectAccountProposal(@PathVariable Long id) {

		var accountProposalOptional = Optional.ofNullable(this.entityManager.find(AccountProposal.class, id));
		var accountProposal = accountProposalOptional.orElseThrow(() -> new EntityNotFoundException(this.invalidAccountProposalIdMessage));

		Email email = new Email(this.rejectMailSubject, this.rejectMailBody, accountProposal.getEmail());
		this.mailer.send(email);

		return ResponseEntity.ok().build();
	}
}
