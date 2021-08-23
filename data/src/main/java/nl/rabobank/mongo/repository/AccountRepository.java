package nl.rabobank.mongo.repository;

import nl.rabobank.mongo.document.account.MongoAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<MongoAccount,String> {

    Optional<MongoAccount> findByAccountNumber(String accountNumber);

}
