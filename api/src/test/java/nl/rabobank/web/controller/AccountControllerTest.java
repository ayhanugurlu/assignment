package nl.rabobank.web.controller;


import nl.rabobank.RaboAssignmentApplication;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.account.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = RaboAssignmentApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void should_create_account_when_sent_new_account_dto() {
        AccountDto accountDto = AccountDto.builder().balance(0d)
                .accountType(AccountType.PAYMENT)
                .accountHolderName("accountHolderName")
                .accountNumber("2212")
                .build();
        ResponseEntity<AccountDto> responseEntity = this.restTemplate.postForEntity(getUrl("/account"), accountDto, AccountDto.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @Test
    void should_return_duplication_error_when_use_same_account_multiple_times() {
        AccountDto accountDto = AccountDto.builder().balance(0d)
                .accountType(AccountType.PAYMENT)
                .accountHolderName("accountHolderName")
                .accountNumber("2213")
                .build();
        ResponseEntity<AccountDto> responseEntityFirst = this.restTemplate.postForEntity(getUrl("/account"), accountDto, AccountDto.class);
        ResponseEntity<AccountDto> responseEntitySecond = this.restTemplate.postForEntity("http://localhost:" + port + "/account", accountDto, AccountDto.class);
        assertEquals(HttpStatus.CONFLICT, responseEntityFirst.getStatusCode());
    }


    @Test
    void should_get_account_when_account_exist() {
        String accountNumber = "2213";
        AccountDto accountDto = AccountDto.builder().balance(0d)
                .accountType(AccountType.PAYMENT)
                .accountHolderName("accountHolderName")
                .accountNumber(accountNumber)
                .build();
        this.restTemplate.postForEntity(getUrl("/account"), accountDto, AccountDto.class);
        ResponseEntity<AccountDto> responseEntityGetAccount = this.restTemplate.getForEntity(getUrl("/account/" + accountNumber), AccountDto.class);
        assertEquals(HttpStatus.OK, responseEntityGetAccount.getStatusCode());
        assertEquals(accountDto, responseEntityGetAccount.getBody());
    }


    @Test
    void should_return_not_found_when_account_does_not_exist() {
        String accountNumber = "2214";
        ResponseEntity<AccountDto> responseEntityGetAccount = this.restTemplate.getForEntity(getUrl("/account/" + accountNumber), AccountDto.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityGetAccount.getStatusCode());
    }

}