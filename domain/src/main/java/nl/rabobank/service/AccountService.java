package nl.rabobank.service;

import nl.rabobank.dto.account.AccountDto;

import javax.validation.Valid;

public interface AccountService {

    AccountDto getAccount(String accountNumber);

    AccountDto create(@Valid AccountDto accountDTO);
}
