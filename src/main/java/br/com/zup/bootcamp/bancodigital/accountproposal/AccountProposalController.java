package br.com.zup.bootcamp.bancodigital.accountproposal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

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

		URI uri = uriBuilder
							.path("/account-proposal/{id}/step-two")
							.buildAndExpand(accountProposal.getId())
							.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PostMapping("/{id}/step-two}")
	@Transactional
	public ResponseEntity stepTwo (@PathVariable Long id,
	                               @Valid @RequestBody IncludeAddressToAccountProposalRequest includeAddressToAccountProposalRequest,
	                               UriComponentsBuilder uriBuilder) {

		// checar nulidade?
		var accountProposal = entityManager.find(AccountProposal.class, id);

		Address newAddress = includeAddressToAccountProposalRequest.createAddress();
		accountProposal.addAddress(newAddress);

		UriComponents uriComponents = uriBuilder.path("/api/account-proposal/{id}/step-three").buildAndExpand(id);
		return ResponseEntity.created(uriComponents.toUri()).build();
	}
}
