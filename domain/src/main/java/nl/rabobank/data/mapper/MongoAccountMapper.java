package nl.rabobank.data.mapper;

import nl.rabobank.account.Account;
import nl.rabobank.mongo.document.account.MongoAccount;

public interface MongoAccountMapper {
    Account map(MongoAccount mongoAccount);
    MongoAccount map(Account account);


}
