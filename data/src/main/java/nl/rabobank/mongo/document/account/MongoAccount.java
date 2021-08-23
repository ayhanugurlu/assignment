package nl.rabobank.mongo.document.account;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@SuperBuilder
@Data
@Document(collection = "account")
@NoArgsConstructor
public abstract class MongoAccount {
    @Id
    private String id;

    @Indexed(unique = true)
    private String accountNumber;

    private String accountHolderName;
    private Double balance;
}
