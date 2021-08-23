package nl.rabobank.data.mapper.implementation;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.config.CustomMapper;
import nl.rabobank.data.mapper.MongoAccountMapper;
import nl.rabobank.exception.UnknownPaymentTypeException;
import nl.rabobank.mongo.document.account.MongoAccount;
import nl.rabobank.mongo.document.account.MongoPaymentAccount;
import nl.rabobank.mongo.document.account.MongoSavingsAccount;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoAccountMapperImpl implements MongoAccountMapper {

    private final CustomMapper customMapper;

    private PaymentAccount map(MongoPaymentAccount mongoPaymentAccount) {
        return customMapper.map(mongoPaymentAccount,PaymentAccount.PaymentAccountBuilder.class).build();
    }

    private MongoPaymentAccount map(PaymentAccount paymentAccount) {
        return customMapper.map(paymentAccount,MongoPaymentAccount.class);
    }


    private SavingsAccount map(MongoSavingsAccount mongoSavingsAccount) {
        return customMapper.map(mongoSavingsAccount,SavingsAccount.SavingsAccountBuilder.class).build();
    }


    private MongoSavingsAccount map(SavingsAccount savingsAccount) {
        return customMapper.map(savingsAccount,MongoSavingsAccount.class);
    }


    @Override
    public Account map(MongoAccount mongoAccount) {
        if(mongoAccount instanceof MongoPaymentAccount){
            return this.map((MongoPaymentAccount) mongoAccount);
        }else if(mongoAccount instanceof MongoSavingsAccount){
            return this.map((MongoSavingsAccount)mongoAccount);
        }
        throw new UnknownPaymentTypeException();
    }


    @Override
    public MongoAccount map(Account account) {
        if(account instanceof SavingsAccount){
            return this.map((SavingsAccount)account);
        }else if(account instanceof PaymentAccount){
            return this.map((PaymentAccount)account);
        }
        throw new UnknownPaymentTypeException();
    }
}
