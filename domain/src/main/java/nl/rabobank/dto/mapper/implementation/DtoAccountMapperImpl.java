package nl.rabobank.dto.mapper.implementation;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.config.CustomMapper;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.account.AccountType;
import nl.rabobank.dto.mapper.DtoAccountMapper;
import nl.rabobank.exception.UnknownPaymentTypeException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DtoAccountMapperImpl implements DtoAccountMapper {

    private final CustomMapper mapper;

    private AccountDto map(PaymentAccount paymentAccount) {
        return mapper.map(paymentAccount,AccountDto.class);
    }

    private AccountDto map(SavingsAccount savingsAccount) {
        return mapper.map(savingsAccount,AccountDto.class);
    }


    private PaymentAccount mapPayment(AccountDto accountDto)
    {
        return mapper.map(accountDto,PaymentAccount.PaymentAccountBuilder.class).build();
    }

    private SavingsAccount mapSavings(AccountDto accountDto) {
        return mapper.map(accountDto,SavingsAccount.SavingsAccountBuilder.class).build();
    }


    @Override
    public AccountDto map(Account account) {
        if(account instanceof PaymentAccount){
            return map((PaymentAccount)account);
        }else if(account instanceof SavingsAccount){
            return map((SavingsAccount)account);
        }
        throw new UnknownPaymentTypeException();
    }

    @Override
    public Account map(AccountDto accountDto) {
        if(accountDto.getAccountType() == AccountType.SAVING){
           return this.mapSavings(accountDto);
        }else if(accountDto.getAccountType() == AccountType.PAYMENT){
            return this.mapPayment(accountDto);
        }
        throw new UnknownPaymentTypeException();
    }
}
