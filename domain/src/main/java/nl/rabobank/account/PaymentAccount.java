package nl.rabobank.account;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentAccount implements Account
{
    String accountNumber;
    String accountHolderName;
    Double balance;
}
