package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
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
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping ("/account-proposal")
@PropertySource (value = { "classpath:ValidationMessages.properties", "classpath:MailConfig.properties" }, encoding = "UTF-8")
public class AcceptAccountProposalController {

	@Value ("${id.invalid}")
	private String invalidAccountProposalIdMessage;

	@Value("${account-accept.mail.subject}")
	private String rejectMailSubject;
	@Value("${account-accept.mail.body}")
	private String rejectMailBody;

	private final EntityManager entityManager;

	private final Mailer mailer; //1
	private final DocumentIntegrityChecker documentIntegrityChecker; //2

	public AcceptAccountProposalController (EntityManager entityManager,
	                                        Mailer mailer,
	                                        DocumentIntegrityChecker documentIntegrityChecker) {
		this.entityManager = entityManager;
		this.mailer = mailer;
		this.documentIntegrityChecker = documentIntegrityChecker;
	}

	@PostMapping("/{id}/step-five/accept")
	@Transactional
	public ResponseEntity acceptAccountProposal(@PathVariable Long id) {

		//3 account proposal
		AccountProposal foundAccountProposal = this.entityManager.find(AccountProposal.class, id);
		var accountProposalOptional = Optional.ofNullable(foundAccountProposal);
		var accountProposal = accountProposalOptional.orElseThrow(() -> new EntityNotFoundException(this.invalidAccountProposalIdMessage));
		//4 funcao como argumento no throw

		//5 funcao como argumento
		new Thread(() -> {
			//6
//			DocumentStatus documentStatus = this.documentIntegrityChecker.check(accountProposal);
			this.documentIntegrityChecker.check2(accountProposal);
			// Fiz a contagem passando a responsabilidade pro checker (que passa a virar um check handler)

			// documentStatus.handle(this.entityManager, accountProposal, this.mailer);

			// 7
//			if (documentStatus == DocumentStatus.SUCCESS) {
//				accountProposal.setDocumentStatus(documentStatus);
//				this.entityManager.merge(accountProposal);
//
//				// 8
//				Account account = new Account(accountProposal);
//				this.entityManager.persist(account);
//
//				// 9
//				Email email = new Email("","", accountProposal.getEmail());
//				this.mailer.send(email);
//
//				// 10
//			} else {
//				accountProposal.setDocumentStatus(documentStatus);
//				this.entityManager.merge(accountProposal);
//			}
		}).start();

		return ResponseEntity.ok().build();
	}
}
