package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
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
@PropertySource (value = {"classpath:ValidationMessages.properties"}, encoding = "UTF-8")
public class AcceptAccountProposalController { // 6

	@Value ("${id.invalid}")
	private String invalidAccountProposalIdMessage;

	private final EntityManager entityManager;

	private final DocumentIntegrityChecker documentIntegrityChecker;
	private final DocumentIntegrityHandler documentIntegrityHandler;

	public AcceptAccountProposalController (EntityManager entityManager,
	                                        DocumentIntegrityChecker documentIntegrityChecker,
	                                        DocumentIntegrityHandler documentIntegrityHandler) {
		this.entityManager = entityManager;
		this.documentIntegrityChecker = documentIntegrityChecker;
		this.documentIntegrityHandler = documentIntegrityHandler;
	}

	@PostMapping("/{id}/step-five/accept")
	@Transactional
	public ResponseEntity acceptAccountProposal(@PathVariable Long id) {

		AccountProposal foundAccountProposal = this.entityManager.find(AccountProposal.class, id);
		var accountProposalOptional = Optional.ofNullable(foundAccountProposal);
		var accountProposal = accountProposalOptional.orElseThrow(() -> new EntityNotFoundException(this.invalidAccountProposalIdMessage));

		new Thread(() -> {
			DocumentStatus documentStatus = this.documentIntegrityChecker.check(accountProposal);
			this.documentIntegrityHandler.handle(documentStatus, accountProposal);
		}).start();

		return ResponseEntity.ok().build();
	}
}
