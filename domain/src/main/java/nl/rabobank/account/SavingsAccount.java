package nl.rabobank.account;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SavingsAccount implements Account
{
    String accountNumber;
    String accountHolderName;
    Double balance;
}
