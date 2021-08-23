package nl.rabobank.data.mapper.implementation;

import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.config.CustomMapper;
import nl.rabobank.data.mapper.MongoAccountMapper;
import nl.rabobank.mongo.document.account.MongoAccount;
import nl.rabobank.mongo.document.account.MongoPaymentAccount;
import nl.rabobank.mongo.document.account.MongoSavingsAccount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MongoAccountMapperImplTest {

    private static final CustomMapper customMapper = new CustomMapper();

    private final MongoAccountMapper mongoAccountMapper = new MongoAccountMapperImpl(customMapper);

    @BeforeAll
    static void setUp() {
        customMapper.init();
    }

    @Test
    void should_return_mongo_payment_account_when_payment_account() {
        PaymentAccount paymentAccount = PaymentAccount.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .balance(0d)
                .build();
        MongoAccount mongoAccount = mongoAccountMapper.map(paymentAccount);
        assertTrue(mongoAccount instanceof MongoPaymentAccount);
    }

    @Test
    void should_return_mongo_saving_account_when_payment_account() {
        SavingsAccount savingsAccount = SavingsAccount.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .balance(0d)
                .build();
        MongoAccount mongoAccount = mongoAccountMapper.map(savingsAccount);
        assertTrue(mongoAccount instanceof MongoSavingsAccount);
    }


    @Test
    void should_return_account_when_saving_account() {
        MongoAccount mongoAccount = MongoSavingsAccount.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .balance(0d)
                .build();
        Account account = mongoAccountMapper.map(mongoAccount);
        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    void should_return_account_when_payment_account() {
        MongoAccount mongoAccount = MongoPaymentAccount.builder()
                .accountNumber("1212")
                .accountHolderName("accountHolderName")
                .balance(0d)
                .build();
        Account account = mongoAccountMapper.map(mongoAccount);
        assertTrue(account instanceof PaymentAccount);
    }


}