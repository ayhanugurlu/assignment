package nl.rabobank.data.mapper;

import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.mongo.document.account.MongoAccount;
import nl.rabobank.mongo.document.authorizations.MongoPowerOfAttorney;

public interface MongoPowerOfAttorneyMapper {

    PowerOfAttorney map(MongoPowerOfAttorney mongoPowerOfAttorney);
    MongoPowerOfAttorney map(PowerOfAttorney paymentAccountDto, MongoAccount mongoAccount);
}
