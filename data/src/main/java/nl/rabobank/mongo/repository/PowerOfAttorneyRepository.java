package nl.rabobank.mongo.repository;

import nl.rabobank.mongo.document.authorizations.MongoPowerOfAttorney;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PowerOfAttorneyRepository extends MongoRepository<MongoPowerOfAttorney,String> {

    List<MongoPowerOfAttorney> findByAccount_AccountNumber(String accountNumber);

    Optional<MongoPowerOfAttorney> findByAccount_AccountNumber_AndGranteeName(String accountNumber, String granteeName);

}
