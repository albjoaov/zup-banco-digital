package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;

@FunctionalInterface
public interface DocumentIntegrityChecker {

	DocumentStatus check (AccountProposal accountProposal);
}
