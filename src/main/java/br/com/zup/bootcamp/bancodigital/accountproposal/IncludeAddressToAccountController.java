package br.com.zup.bootcamp.bancodigital.accountproposal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping ("/account-proposal")
@PropertySource (value = "classpath:ValidationMessages.properties", encoding = "UTF-8")
public class IncludeAddressToAccountController {

	@Value ("${id.invalid}")
	private String invalidAccountProposalIdMessage;

	private final EntityManager entityManager;

	public IncludeAddressToAccountController (EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * A fim de não expor dados internos da aplicação, mesmo que à priori inofensivos, a mensagem da exceção
	 * será substituída
	 */
	@PostMapping ("/{id}/step-two")
	@Transactional
	public ResponseEntity stepTwo (@PathVariable Long id,
	                               @Valid @RequestBody IncludeAddressToAccountProposalRequest includeAddressToAccountProposalRequest,
	                               UriComponentsBuilder uriBuilder) {

		Address newAddress = includeAddressToAccountProposalRequest.createAddress();
		AccountProposal accountProposal;
		try {
			accountProposal = entityManager.getReference(AccountProposal.class, id);
			accountProposal.setAddress(newAddress);
			this.entityManager.merge(accountProposal);
		} catch (EntityNotFoundException | IllegalArgumentException e) {
			throw new EntityNotFoundException(invalidAccountProposalIdMessage);
		}

		UriComponents uriComponents = uriBuilder.path("/account-proposal/{id}/step-three").buildAndExpand(id);
		return ResponseEntity.created(uriComponents.toUri()).build();
	}
}
