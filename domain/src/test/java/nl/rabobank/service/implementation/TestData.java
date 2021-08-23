package nl.rabobank.service.implementation;

import lombok.Value;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.account.AccountType;
import nl.rabobank.dto.authorizations.AuthorizationDto;
import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;
import nl.rabobank.mongo.document.account.MongoAccount;
import nl.rabobank.mongo.document.account.MongoPaymentAccount;
import nl.rabobank.mongo.document.authorizations.MongoAuthorization;
import nl.rabobank.mongo.document.authorizations.MongoPowerOfAttorney;

import java.util.Collections;
import java.util.List;

@Value
public class TestData {
    AccountDto accountDtoCreate = AccountDto.builder()
            .accountNumber("1212")
            .accountHolderName("accountHolderName")
            .accountType(AccountType.SAVING)
            .balance(0d)
            .build();

    PaymentAccount paymentAccountCreate = PaymentAccount.builder()
            .accountNumber("1212")
            .accountHolderName("accountHolderName")
            .balance(0d)
            .build();

    MongoAccount mongoAccountCreate = MongoPaymentAccount.builder()
            .id("id")
            .accountNumber("1212")
            .accountHolderName("accountHolderName")
            .balance(0d)
            .build();

    MongoAccount mongoAccountGet = MongoPaymentAccount.builder()
            .id("id")
            .accountNumber("1216")
            .accountHolderName("accountHolderName")
            .balance(0d)
            .build();

    AccountDto accountDtoDuplicate = AccountDto.builder()
            .accountNumber("1213")
            .accountHolderName("accountHolderName")
            .accountType(AccountType.SAVING)
            .balance(0d)
            .build();

    PaymentAccount paymentAccountDuplicate = PaymentAccount.builder()
            .accountNumber("1213")
            .accountHolderName("accountHolderName")
            .balance(0d)
            .build();

    MongoAccount mongoAccountDuplicate = MongoPaymentAccount.builder()
            .id("id")
            .accountNumber("1213")
            .accountHolderName("accountHolderName")
            .balance(0d)
            .build();


    AccountDto accountDtoGet = AccountDto.builder()
            .accountNumber("1214")
            .accountHolderName("accountHolderName")
            .accountType(AccountType.SAVING)
            .balance(0d)
            .build();

    PaymentAccount paymentAccountGet = PaymentAccount.builder()
            .accountNumber("1214")
            .accountHolderName("accountHolderName")
            .balance(0d)
            .build();


    String doesNotExistAccountNumber = "1215";

    PowerOfAttorneyDto powerOfAttorneyDtoCreate = PowerOfAttorneyDto.builder()
            .accountNumber("1216")
            .authorization(AuthorizationDto.WRITE)
            .granteeName("granteeName")
            .grantorName("grantorName")
            .build();


    PowerOfAttorney powerOfAttorneyCreate = PowerOfAttorney.builder()
            .account(paymentAccountCreate)
            .authorization(Authorization.WRITE)
            .granteeName("granteeName")
            .grantorName("grantorName")
            .build();

    MongoPowerOfAttorney mongoPowerOfAttorneyCreate = MongoPowerOfAttorney.builder()
            .account(mongoAccountCreate)
            .authorization(MongoAuthorization.WRITE)
            .granteeName("granteeName")
            .grantorName("grantorName")
            .build();

    MongoPowerOfAttorney mongoPowerOfAttorneyGet = MongoPowerOfAttorney.builder()
            .account(mongoAccountGet)
            .authorization(MongoAuthorization.WRITE)
            .granteeName("granteeName")
            .grantorName("grantorName")
            .build();

   PowerOfAttorney powerOfAttorneyGet = PowerOfAttorney.builder()
            .account(paymentAccountGet)
            .authorization(Authorization.WRITE)
            .granteeName("granteeName")
            .grantorName("grantorName")
            .build();

    PowerOfAttorneyDto powerOfAttorneyDtoGet = PowerOfAttorneyDto.builder()
            .accountNumber(paymentAccountGet.getAccountNumber())
            .authorization(AuthorizationDto.WRITE)
            .granteeName("granteeName")
            .grantorName("grantorName")
            .build();

    List<MongoPowerOfAttorney> mongoPowerOfAttorneys = Collections.singletonList(mongoPowerOfAttorneyGet);

    String doesNotExistGranteeName = "granteeName";
}
