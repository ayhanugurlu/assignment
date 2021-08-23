package nl.rabobank.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rabobank.RaboAssignmentApplication;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.account.AccountType;
import nl.rabobank.dto.authorizations.AuthorizationDto;
import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = RaboAssignmentApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PowerOfAttorneyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }

    private static final String ACCOUNT_NUMBER = "1212";
    private static final String ACCOUNT_NUMBER_NOT_FOUND = "1213";
    private static final String ACCOUNT_NUMBER_DUPLICATION = "1214";
    private static final String ACCOUNT_NUMBER_GETS = "1215";
    private static final String ACCOUNT_NUMBER_GET = "1217";
    private static final String ACCOUNT_NUMBER_GET_DOES_NOT_EXIST = "1216";


    @Test
    void should_create_power_of_attorney_when_sent_related_account_does_not_exist() {
        PowerOfAttorneyDto powerOfAttorneyDto = PowerOfAttorneyDto.builder()
                .accountNumber(ACCOUNT_NUMBER_NOT_FOUND)
                .grantorName("grantorName")
                .granteeName("granteeName")
                .authorization(AuthorizationDto.READ)
                .build();
        ResponseEntity<PowerOfAttorneyDto> responseEntity = this.restTemplate.postForEntity(getUrl("/powerofattorney"), powerOfAttorneyDto, PowerOfAttorneyDto.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void should_create_power_of_attorney_when_sent_power_of_attorney_dto() {
        createAccount(ACCOUNT_NUMBER);
        PowerOfAttorneyDto powerOfAttorneyDto = PowerOfAttorneyDto.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .grantorName("grantorName")
                .granteeName("granteeName")
                .authorization(AuthorizationDto.READ)
                .build();
        ResponseEntity<PowerOfAttorneyDto> responseEntity = this.restTemplate.postForEntity(getUrl("/powerofattorney"), powerOfAttorneyDto, PowerOfAttorneyDto.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void should_return_duplication_error_when_use_same_power_of_attorney_multiple_times() {
        createAccount(ACCOUNT_NUMBER_DUPLICATION);
        PowerOfAttorneyDto powerOfAttorneyDto = PowerOfAttorneyDto.builder()
                .accountNumber(ACCOUNT_NUMBER_DUPLICATION)
                .grantorName("grantorName")
                .granteeName("granteeName")
                .authorization(AuthorizationDto.READ)
                .build();
        this.restTemplate.postForEntity(getUrl("/powerofattorney"), powerOfAttorneyDto, PowerOfAttorneyDto.class);
        ResponseEntity<PowerOfAttorneyDto> responseEntity = this.restTemplate.postForEntity(getUrl("/powerofattorney"), powerOfAttorneyDto, PowerOfAttorneyDto.class);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    void should_return_list_of_power_of_attorney_when_power_of_attorney_exist() throws JsonProcessingException {
        createAccount(ACCOUNT_NUMBER_GETS);
        createPowerOfAttorney(ACCOUNT_NUMBER_GETS);
        ResponseEntity<List<PowerOfAttorneyDto>> responseEntity = restTemplate.exchange(getUrl("/powerofattorney/" + ACCOUNT_NUMBER_GETS),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<PowerOfAttorneyDto>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
    }

    @Test
    void should_return_empty_power_of_attorney_list_when_power_of_attorney_does_not_exist() {
        ResponseEntity<List<PowerOfAttorneyDto>> responseEntity = restTemplate.exchange(getUrl("/powerofattorney/" + ACCOUNT_NUMBER_GET_DOES_NOT_EXIST),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<PowerOfAttorneyDto>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    void should_return_power_of_attorney_when_send_existing_granteeName_account_number() {
        createAccount(ACCOUNT_NUMBER_GET);
        createPowerOfAttorney(ACCOUNT_NUMBER_GET);
        ResponseEntity<PowerOfAttorneyDto> responseEntity = restTemplate.getForEntity(getUrl("/powerofattorney/" + ACCOUNT_NUMBER_GET + "/granteeName"), PowerOfAttorneyDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ACCOUNT_NUMBER_GET, responseEntity.getBody().getAccountNumber());
    }

    @Test
    void should_return_power_of_attorney_when_send_does_not_existing_granteeName_account_number() {
        ResponseEntity<PowerOfAttorneyDto> responseEntity = restTemplate.getForEntity(getUrl("/powerofattorney/" + ACCOUNT_NUMBER_GET_DOES_NOT_EXIST + "/granteeName"), PowerOfAttorneyDto.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private void createAccount(String accountNumber) {
        AccountDto accountDto = AccountDto.builder().balance(0d)
                .accountType(AccountType.PAYMENT)
                .accountHolderName("accountHolderName")
                .accountNumber(accountNumber)
                .build();

        this.restTemplate.postForEntity(getUrl("/account"), accountDto, AccountDto.class);
    }

    private void createPowerOfAttorney(String accountNumber) {
        PowerOfAttorneyDto powerOfAttorneyDto = PowerOfAttorneyDto.builder()
                .accountNumber(accountNumber)
                .grantorName("grantorName")
                .granteeName("granteeName")
                .authorization(AuthorizationDto.READ)
                .build();
        this.restTemplate.postForEntity(getUrl("/powerofattorney"), powerOfAttorneyDto, PowerOfAttorneyDto.class);

    }


}