package nl.rabobank.dto.mapper.implementation;

import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.config.CustomMapper;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.account.AccountType;
import nl.rabobank.dto.mapper.DtoAccountMapper;
import nl.rabobank.exception.UnknownPaymentTypeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class DtoAccountMapperImplTest {

    private static final CustomMapper customMapper = new CustomMapper();

    private DtoAccountMapper dtoAccountMapper = new DtoAccountMapperImpl(customMapper);

    @BeforeAll
    static void  setUp(){
        customMapper.init();
    }

    @Test
     void should_return_payment_account_when_account_type_payment() {
        AccountDto accountDto = AccountDto.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .accountType(AccountType.PAYMENT)
                .balance(0d)
                .build();
        Account account = dtoAccountMapper.map(accountDto);
        assertTrue(account instanceof PaymentAccount);
    }

    @Test
     void should_return_saving_account_when_account_type_payment() {
        AccountDto accountDto = AccountDto.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .accountType(AccountType.SAVING)
                .balance(0d)
                .build();
        Account account = dtoAccountMapper.map(accountDto);
        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    void should_return_unknownpayment_type_when_account_type_null() {
        AccountDto accountDto = AccountDto.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .balance(0d)
                .build();

        UnknownPaymentTypeException unknownPaymentTypeException = assertThrows(
                UnknownPaymentTypeException.class,
                () -> dtoAccountMapper.map(accountDto),
                "Account doen not exist than throw AccountNotFoundException"
        );
        assertNotNull(unknownPaymentTypeException);
    }

    @Test
     void should_return_account_when_saving_account() {
        Account account = SavingsAccount.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .balance(0d)
                .build();
        AccountDto accountDto = dtoAccountMapper.map(account);
        assertEquals(AccountType.SAVING,accountDto.getAccountType());
    }

    @Test
     void should_return_account_when_payment_account() {
        Account account = PaymentAccount.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .balance(0d)
                .build();
        AccountDto accountDto = dtoAccountMapper.map(account);
        assertEquals(AccountType.PAYMENT,accountDto.getAccountType());
    }

}