package nl.rabobank.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.data.mapper.MongoPowerOfAttorneyMapper;
import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;
import nl.rabobank.dto.mapper.DtoPowerOfAttorneyMapper;
import nl.rabobank.exception.AccountNotFoundException;
import nl.rabobank.exception.PowerOfAttorneyDuplicationException;
import nl.rabobank.exception.PowerOfAttorneyNotFoundException;
import nl.rabobank.mongo.document.account.MongoAccount;
import nl.rabobank.mongo.document.authorizations.MongoPowerOfAttorney;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.mongo.repository.PowerOfAttorneyRepository;
import nl.rabobank.service.PowerOfAttorneyService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PowerOfAttorneyServiceImpl implements PowerOfAttorneyService {

    private final PowerOfAttorneyRepository powerOfAttorneyRepository;

    private final AccountRepository accountRepository;

    private final DtoPowerOfAttorneyMapper dtoPowerOfAttorneyMapper;

    private final MongoPowerOfAttorneyMapper mongoPowerOfAttorneyMapper;

    @Override
    public PowerOfAttorneyDto getPowerOfAttorney(String accountNumber, String granteeName) {
        log.debug("getPowerOfAttorney accountNumber {} granteeName {}",accountNumber,granteeName);
        Optional<MongoPowerOfAttorney> mongoPowerOfAttorneyOptional = powerOfAttorneyRepository.findByAccount_AccountNumber_AndGranteeName(accountNumber,granteeName);
        return mongoPowerOfAttorneyOptional.map(mongoPowerOfAttorneyMapper::map)
                .map(dtoPowerOfAttorneyMapper::map)
                .orElseThrow(()-> new PowerOfAttorneyNotFoundException(accountNumber,granteeName));
    }

    @Override
    public List<PowerOfAttorneyDto> getPowerOfAttorneys(String accountNumber) {
        log.debug("getPowerOfAttorneys accountNumber {}",accountNumber);
        List<MongoPowerOfAttorney> powerOfAttorneys = powerOfAttorneyRepository.findByAccount_AccountNumber(accountNumber);
        return powerOfAttorneys.stream()
                .map(mongoPowerOfAttorneyMapper::map)
                .map(dtoPowerOfAttorneyMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public PowerOfAttorneyDto create(PowerOfAttorneyDto powerOfAttorneyDto) {
        log.debug("create accountNumber {} granteeName {}",powerOfAttorneyDto.getAccountNumber(),powerOfAttorneyDto.getGranteeName());
        Optional<MongoAccount> mongoAccountOptional = accountRepository.findByAccountNumber(powerOfAttorneyDto.getAccountNumber());
        var mongoAccount = mongoAccountOptional.orElseThrow(() -> new AccountNotFoundException(powerOfAttorneyDto.getAccountNumber()));
        var powerOfAttorney = dtoPowerOfAttorneyMapper.map(powerOfAttorneyDto,null);
        var mongoPowerOfAttorney = savePowerOfAttorney(mongoPowerOfAttorneyMapper.map(powerOfAttorney,mongoAccount));
        return dtoPowerOfAttorneyMapper.map(mongoPowerOfAttorneyMapper.map(mongoPowerOfAttorney));
    }


    private MongoPowerOfAttorney savePowerOfAttorney(MongoPowerOfAttorney mongoPowerOfAttorney){
        log.debug("savePowerOfAttorney accountNumber {} granteeName {}",mongoPowerOfAttorney.getAccount().getAccountNumber(),mongoPowerOfAttorney.getGranteeName());
        try{
            return powerOfAttorneyRepository.save(mongoPowerOfAttorney);
        }catch (DuplicateKeyException ex){
            throw new PowerOfAttorneyDuplicationException(mongoPowerOfAttorney.getGranteeName(),mongoPowerOfAttorney.getAccount().getAccountNumber());
        }
    }
}
