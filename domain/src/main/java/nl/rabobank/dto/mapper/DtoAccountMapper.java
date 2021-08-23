package nl.rabobank.dto.mapper;

import nl.rabobank.account.Account;
import nl.rabobank.dto.account.AccountDto;

public interface DtoAccountMapper {

    AccountDto map(Account account);
    Account map(AccountDto accountDto);

}
