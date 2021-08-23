package nl.rabobank.service.implementation;

import nl.rabobank.data.mapper.MongoAccountMapper;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.mapper.DtoAccountMapper;
import nl.rabobank.exception.AccountDuplicationException;
import nl.rabobank.exception.AccountNotFoundException;
import nl.rabobank.mongo.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private DtoAccountMapper dtoAccountMapper;

    @Mock
    private MongoAccountMapper mongoAccountMapper;

    @Mock
    private AccountRepository accountRepository;

    TestData testData = new TestData();

    @Test
    void should_create_account_when_account_new() {

        when(dtoAccountMapper.map(testData.getAccountDtoCreate())).thenReturn(testData.getPaymentAccountCreate());
        when(dtoAccountMapper.map(testData.getPaymentAccountCreate())).thenReturn(testData.getAccountDtoCreate());
        when(mongoAccountMapper.map(testData.getPaymentAccountCreate())).thenReturn(testData.getMongoAccountCreate());
        when(mongoAccountMapper.map(testData.getMongoAccountCreate())).thenReturn(testData.getPaymentAccountCreate());
        when(accountRepository.save(testData.getMongoAccountCreate())).thenReturn(testData.getMongoAccountCreate());

        AccountDto accountDtoCreated = accountService.create(testData.getAccountDtoCreate());
        assertEquals(testData.getAccountDtoCreate().getAccountNumber(), accountDtoCreated.getAccountNumber());
    }

    @Test
    void should_throw_account_duplication_exception_when_account_already_exist() {
        when(dtoAccountMapper.map(testData.getAccountDtoDuplicate())).thenReturn(testData.getPaymentAccountDuplicate());
        when(mongoAccountMapper.map(testData.getPaymentAccountDuplicate())).thenReturn(testData.getMongoAccountDuplicate());
        when(accountRepository.save(testData.getMongoAccountDuplicate())).thenThrow(DuplicateKeyException.class);

        AccountDuplicationException accountDuplicationException = assertThrows(
                AccountDuplicationException.class,
                () -> accountService.create(testData.getAccountDtoDuplicate()),
                "Account already exist than throw AccountDuplicationException"
        );
        assertEquals(testData.getAccountDtoDuplicate().getAccountNumber(), accountDuplicationException.getAccountNumber());
    }

    @Test
    void should_get_account_when_account_number_exist() {

        when(mongoAccountMapper.map(testData.getMongoAccountGet())).thenReturn(testData.getPaymentAccountGet());
        when(dtoAccountMapper.map(testData.getPaymentAccountGet())).thenReturn(testData.getAccountDtoGet());
        when(accountRepository.findByAccountNumber(testData.getAccountDtoGet().getAccountNumber())).thenReturn(Optional.of(testData.getMongoAccountGet()));

        AccountDto accountDtoCreated = accountService.getAccount(testData.getAccountDtoGet().getAccountNumber());
        assertEquals(testData.getAccountDtoGet().getAccountNumber(), accountDtoCreated.getAccountNumber());
    }

    @Test
    void should_return_account_not_found_exception_when_account_number_does_not_exist() {

        when(accountRepository.findByAccountNumber(testData.getDoesNotExistAccountNumber())).thenReturn(Optional.empty());
        AccountNotFoundException accountNotFoundException = assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getAccount(testData.getDoesNotExistAccountNumber()),
                "Account doen not exist than throw AccountNotFoundException"
        );

        assertEquals(testData.getDoesNotExistAccountNumber(), accountNotFoundException.getAccountNumber());
    }

}