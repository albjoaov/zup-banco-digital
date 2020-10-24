package br.com.zup.bootcamp.bancodigital.accountproposal.confirm;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping ("/account-proposal")
@PropertySource (value = "classpath:ValidationMessages.properties", encoding = "UTF-8")
public class AccountProposalConfirmationController {

	@Value ("${account-proposal.id.invalid}")
	private String invalidAccountProposalIdMessage;

	private final EntityManager entityManager;

	public AccountProposalConfirmationController (EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@GetMapping ("/{id}/step-four")
	public ResponseEntity<AccountProposalConfirmationResponse> retrieve(@PathVariable Long id) {

		var accountProposalOptional = Optional.ofNullable(this.entityManager.find(AccountProposal.class, id));
		var accountProposal = accountProposalOptional.orElseThrow(() -> new EntityNotFoundException(invalidAccountProposalIdMessage));

		accountProposal.checkIfProcessIsComplete();
		return ResponseEntity.ok(new AccountProposalConfirmationResponse(accountProposal));
	}
}
