package nl.rabobank.mongo.document.authorizations;

import lombok.Builder;
import lombok.Value;
import nl.rabobank.mongo.document.account.MongoAccount;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder(toBuilder = true)
@Document(collection = "power-of-attorney")
@CompoundIndexes({
        @CompoundIndex(name = "granteeName_account", def = "{'granteeName' : 1, 'account.id': 1}",unique = true)
})
public class MongoPowerOfAttorney
{
    @Id
    private String id;

    String granteeName;
    String grantorName;
    @DBRef
    MongoAccount account;
    MongoAuthorization authorization;
}
