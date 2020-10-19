package br.com.zup.bootcamp.bancodigital.accountproposal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/account-proposal")
public class AccountProposalController {

	private final EntityManager entityManager;

	public AccountProposalController (EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@PostMapping
	@Transactional
	public ResponseEntity stepOne(@Valid @RequestBody InitAccountProposalRequest initAccountProposalRequest,
	                              UriComponentsBuilder uriBuilder) {

		AccountProposal accountProposal = initAccountProposalRequest.createAccountProposal();
		entityManager.persist(accountProposal);

		UriComponents uriComponents = uriBuilder.path("/account-proposal/{id}").buildAndExpand(accountProposal.getId());
		return ResponseEntity.created(uriComponents.toUri()).build();
	}
}
