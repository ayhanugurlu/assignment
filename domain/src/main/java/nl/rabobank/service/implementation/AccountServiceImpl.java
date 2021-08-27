package nl.rabobank.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.data.mapper.MongoAccountMapper;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.mapper.DtoAccountMapper;
import nl.rabobank.exception.AccountDuplicationException;
import nl.rabobank.exception.AccountNotFoundException;
import nl.rabobank.mongo.document.account.MongoAccount;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.service.AccountService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final DtoAccountMapper dtoAccountMapper;

    private final MongoAccountMapper mongoAccountMapper;

    private final AccountRepository accountRepository;

    @Override
    public AccountDto getAccount(final String accountNumber) {
        log.debug("getAccount accountNumber {}", accountNumber);
        Optional<MongoAccount> mongoAccountOptional = accountRepository.findByAccountNumber(accountNumber);
        var account = mongoAccountOptional.map(mongoAccountMapper::map).orElseThrow(() -> new AccountNotFoundException(accountNumber));
        return dtoAccountMapper.map(account);
    }

    @Override
    public AccountDto create(final AccountDto accountDTO) {
        log.debug("create accountNumber {}", accountDTO.getAccountNumber());
        var account = dtoAccountMapper.map(accountDTO);
        var mongoAccount = saveAccount(mongoAccountMapper.map(account));
        return dtoAccountMapper.map(mongoAccountMapper.map(mongoAccount));
    }

    private MongoAccount saveAccount(final MongoAccount mongoAccount) {
        log.debug("saveAccount accountNumber {}", mongoAccount.getAccountNumber());
        try {
            return accountRepository.save(mongoAccount);
        } catch (DuplicateKeyException ex) {
            throw new AccountDuplicationException(mongoAccount.getAccountNumber());
        }
    }


}
