package nl.rabobank.config;

import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.account.AccountType;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomMapper extends ModelMapper {

    @PostConstruct
    public void init(){
        this.addMappings(new PropertyMap <PaymentAccount, AccountDto>() {
            protected void configure() {
                map().setAccountType(AccountType.PAYMENT);
            }
        });

        this.addMappings(new PropertyMap <SavingsAccount, AccountDto>() {
            protected void configure() {
                map().setAccountType(AccountType.SAVING);
            }
        });

        getConfiguration()
                .setDeepCopyEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

    }

}
