package nl.rabobank.dto.mapper.implementation;

import nl.rabobank.account.PaymentAccount;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.config.CustomMapper;
import nl.rabobank.dto.authorizations.AuthorizationDto;
import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;
import nl.rabobank.dto.mapper.DtoPowerOfAttorneyMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoPowerOfAttorneyMapperImplTest {

    private static final CustomMapper customMapper = new CustomMapper();
    private final DtoPowerOfAttorneyMapper dtoPowerOfAttorneyMapper = new DtoPowerOfAttorneyMapperImpl(customMapper);

    @BeforeAll
    static void  setUp(){
        customMapper.init();
    }
    @Test
    void should_return_power_of_attorney_dto_when_send_power_of_attorney(){
        PaymentAccount paymentAccount = PaymentAccount.builder()
                .accountHolderName("accountHolderName").accountNumber("1234").balance(0d).build();
        PowerOfAttorney powerOfAttorney = PowerOfAttorney.builder()
                .account(paymentAccount)
                .authorization(Authorization.WRITE)
                .granteeName("granteeName")
                .grantorName("grantorName")
                .build();
        PowerOfAttorneyDto powerOfAttorneyDto = dtoPowerOfAttorneyMapper.map(powerOfAttorney);
        assertEquals(paymentAccount.getAccountNumber(),powerOfAttorneyDto.getAccountNumber());
        assertEquals(powerOfAttorney.getGranteeName(),powerOfAttorneyDto.getGranteeName());
        assertEquals(powerOfAttorney.getGrantorName(),powerOfAttorneyDto.getGrantorName());
        assertEquals(powerOfAttorney.getAuthorization().name(),powerOfAttorneyDto.getAuthorization().name());
    }


    @Test
    void should_return_power_of_attorney_when_send_power_of_attorney_dto(){
        String accountNumber = "1234";
        PaymentAccount paymentAccount = PaymentAccount.builder()
                .accountHolderName("accountHolderName").accountNumber(accountNumber).balance(0d).build();
        PowerOfAttorneyDto powerOfAttorneyDto = PowerOfAttorneyDto.builder()
                .accountNumber(accountNumber)
                .authorization(AuthorizationDto.WRITE.WRITE)
                .granteeName("granteeName")
                .grantorName("grantorName")
                .build();
        PowerOfAttorney powerOfAttorney = dtoPowerOfAttorneyMapper.map(powerOfAttorneyDto,paymentAccount);
        assertEquals(powerOfAttorneyDto.getAccountNumber(),powerOfAttorney.getAccount().getAccountNumber());
        assertEquals(powerOfAttorneyDto.getGranteeName(),powerOfAttorney.getGranteeName());
        assertEquals(powerOfAttorneyDto.getGrantorName(),powerOfAttorney.getGrantorName());
        assertEquals(powerOfAttorneyDto.getAuthorization().name(),powerOfAttorney.getAuthorization().name());
    }

}