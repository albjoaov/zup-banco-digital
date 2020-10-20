package br.com.zup.bootcamp.bancodigital.accountproposal.document;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import br.com.zup.bootcamp.bancodigital.shared.Uploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping (value = "/account-proposal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
@PropertySource (value = "classpath:ValidationMessages.properties", encoding = "UTF-8")
public class UploadCpfController {

	@Value ("${id.invalid}")
	private String invalidAccountProposalIdMessage;

	private final EntityManager entityManager;
	private final Uploader uploader;

	public UploadCpfController (EntityManager entityManager, Uploader uploader) {
		this.entityManager = entityManager;
		this.uploader = uploader;
	}

	@PostMapping(value = "/{id}/step-three", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Transactional
	public ResponseEntity stepThree(@PathVariable Long id,
	                                @Valid UploadCpfRequest uploadCpfRequest,
	                                UriComponentsBuilder uriBuilder) {

		var accountProposalOptional = Optional.ofNullable(this.entityManager.find(AccountProposal.class, id));
		var accountProposal = accountProposalOptional.orElseThrow(() -> new EntityNotFoundException(invalidAccountProposalIdMessage));


		String cpfFileUrl= uploadCpfRequest.uploadFileAndGetLocation(uploader);
		accountProposal.setCpfFileUrl(cpfFileUrl);

		URI uri = uriBuilder
				.path("/account-proposal/{id}/step-four")
				.buildAndExpand(id)
				.toUri();

		return ResponseEntity.created(uri).build();
	}
}
