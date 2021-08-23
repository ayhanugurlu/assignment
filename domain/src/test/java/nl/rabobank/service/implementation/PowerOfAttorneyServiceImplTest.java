package nl.rabobank.service.implementation;

import nl.rabobank.data.mapper.MongoAccountMapper;
import nl.rabobank.data.mapper.MongoPowerOfAttorneyMapper;
import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;
import nl.rabobank.dto.mapper.DtoAccountMapper;
import nl.rabobank.dto.mapper.DtoPowerOfAttorneyMapper;
import nl.rabobank.exception.PowerOfAttorneyNotFoundException;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.mongo.repository.PowerOfAttorneyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PowerOfAttorneyServiceImplTest {

    @InjectMocks
    private PowerOfAttorneyServiceImpl powerOfAttorneyService;

    @Mock
    private DtoAccountMapper dtoAccountMapper;

    @Mock
    private MongoAccountMapper mongoAccountMapper;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PowerOfAttorneyRepository powerOfAttorneyRepository;

    @Mock
    private DtoPowerOfAttorneyMapper dtoPowerOfAttorneyMapper;

    @Mock
    private MongoPowerOfAttorneyMapper mongoPowerOfAttorneyMapper;

    private TestData testData = new TestData();

    @Test
    void should_create_power_of_attorney_when_send_new_power_of_attorney() {
        when(accountRepository.findByAccountNumber(testData.getPowerOfAttorneyDtoCreate().getAccountNumber())).thenReturn(Optional.of(testData.getMongoAccountCreate()));
        when(dtoPowerOfAttorneyMapper.map(testData.getPowerOfAttorneyDtoCreate(), null)).thenReturn(testData.getPowerOfAttorneyCreate());
        when(mongoPowerOfAttorneyMapper.map(testData.getPowerOfAttorneyCreate(), testData.getMongoAccountCreate())).thenReturn(testData.getMongoPowerOfAttorneyCreate());
        when(mongoPowerOfAttorneyMapper.map(testData.getMongoPowerOfAttorneyCreate())).thenReturn(testData.getPowerOfAttorneyCreate());
        when(dtoPowerOfAttorneyMapper.map(testData.getPowerOfAttorneyCreate())).thenReturn(testData.getPowerOfAttorneyDtoCreate());
        when(powerOfAttorneyRepository.save(testData.getMongoPowerOfAttorneyCreate())).thenReturn(testData.getMongoPowerOfAttorneyCreate());

        PowerOfAttorneyDto powerOfAttorneyDto = powerOfAttorneyService.create(testData.getPowerOfAttorneyDtoCreate());
        assertEquals(testData.getPowerOfAttorneyDtoCreate(), powerOfAttorneyDto);
    }

    @Test
    void should_return_empty_list_when_account_does_not_exist() {
        when(powerOfAttorneyRepository.findByAccount_AccountNumber(testData.getDoesNotExistAccountNumber()))
                .thenReturn(new ArrayList<>());
        List<PowerOfAttorneyDto> powerOfAttorneyDtos = powerOfAttorneyService.getPowerOfAttorneys(testData.getDoesNotExistAccountNumber());
        assertTrue(powerOfAttorneyDtos.isEmpty());
    }

    @Test
    void should_return_list_when_power_of_attorney_exist() {
        when(powerOfAttorneyRepository.findByAccount_AccountNumber(testData.getMongoAccountGet().getAccountNumber())).thenReturn(testData.getMongoPowerOfAttorneys());
        when(mongoPowerOfAttorneyMapper.map(testData.getMongoPowerOfAttorneyGet())).thenReturn(testData.getPowerOfAttorneyGet());
        when(dtoPowerOfAttorneyMapper.map(testData.getPowerOfAttorneyGet())).thenReturn(testData.getPowerOfAttorneyDtoGet());

        List<PowerOfAttorneyDto> powerOfAttorneyDtos = powerOfAttorneyService.getPowerOfAttorneys(testData.getMongoAccountGet().getAccountNumber());
        assertEquals(1, powerOfAttorneyDtos.size());
    }

    @Test
    void should_return_power_of_attorney_when_power_of_attorney_exist() {
        when(powerOfAttorneyRepository.findByAccount_AccountNumber_AndGranteeName(testData.getPowerOfAttorneyDtoGet().getAccountNumber(), testData.getPowerOfAttorneyDtoGet().getGranteeName())).thenReturn(Optional.of(testData.getMongoPowerOfAttorneyGet()));
        when(mongoPowerOfAttorneyMapper.map(testData.getMongoPowerOfAttorneyGet())).thenReturn(testData.getPowerOfAttorneyGet());
        when(dtoPowerOfAttorneyMapper.map(testData.getPowerOfAttorneyGet())).thenReturn(testData.getPowerOfAttorneyDtoGet());

        PowerOfAttorneyDto powerOfAttorneyDto = powerOfAttorneyService.getPowerOfAttorney(testData.getPowerOfAttorneyDtoGet().getAccountNumber(), testData.getPowerOfAttorneyDtoGet().getGranteeName());
        assertEquals(testData.getPowerOfAttorneyDtoGet().getAccountNumber(), powerOfAttorneyDto.getAccountNumber());
    }

    @Test
    void should_return_power_of_attorney_not_found_exception_when_account_numberandgrantee_does_not_exist() {
        when(powerOfAttorneyRepository.findByAccount_AccountNumber_AndGranteeName(testData.getDoesNotExistAccountNumber(), testData.getDoesNotExistGranteeName())).thenReturn(Optional.empty());
        PowerOfAttorneyNotFoundException powerOfAttorneyNotFoundException = assertThrows(
                PowerOfAttorneyNotFoundException.class,
                () -> powerOfAttorneyService.getPowerOfAttorney(testData.getDoesNotExistAccountNumber(), testData.getDoesNotExistGranteeName()),
                "Account doen not exist than throw AccountNotFoundException"
        );

        assertEquals(testData.getDoesNotExistAccountNumber(), powerOfAttorneyNotFoundException.getAccountNumber());
        assertEquals(testData.getDoesNotExistGranteeName(), powerOfAttorneyNotFoundException.getGranteeName());
    }

}