package nl.rabobank.mongo.document.account;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@Document(collection = "account")
@TypeAlias("savings_account")
@NoArgsConstructor
public class MongoSavingsAccount extends MongoAccount
{

}
