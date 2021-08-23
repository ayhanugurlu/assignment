package nl.rabobank.dto.mapper;


import nl.rabobank.account.Account;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;

public interface DtoPowerOfAttorneyMapper {
    PowerOfAttorneyDto map(PowerOfAttorney paymentAccount);
    PowerOfAttorney map(PowerOfAttorneyDto paymentAccountDto, Account account);
}
