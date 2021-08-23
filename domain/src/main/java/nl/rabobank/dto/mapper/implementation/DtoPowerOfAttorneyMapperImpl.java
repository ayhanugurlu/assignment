package nl.rabobank.dto.mapper.implementation;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.Account;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.config.CustomMapper;
import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;
import nl.rabobank.dto.mapper.DtoPowerOfAttorneyMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DtoPowerOfAttorneyMapperImpl implements DtoPowerOfAttorneyMapper {

    private final CustomMapper mapper;

    @Override
    public PowerOfAttorneyDto map(PowerOfAttorney powerOfAttorney) {
        return mapper.map(powerOfAttorney,PowerOfAttorneyDto.class);
    }

    @Override
    public PowerOfAttorney map(PowerOfAttorneyDto paymentAccountDto, Account account) {
        return mapper.map(paymentAccountDto,PowerOfAttorney.PowerOfAttorneyBuilder.class).account(account).build();
    }
}
