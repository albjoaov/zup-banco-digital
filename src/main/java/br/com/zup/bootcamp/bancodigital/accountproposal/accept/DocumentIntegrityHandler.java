package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;

public interface DocumentIntegrityHandler {

	void handle(DocumentStatus documentStatus, AccountProposal accountProposal);
}
