package nl.rabobank.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto
{
    @NotBlank(message = "Account number is mandatory")
    String accountNumber;

    @NotBlank(message = "Account holder name is mandatory")
    String accountHolderName;

    @Min(value = 0,message = "Account balance must be bigger than 0")
    Double balance;

    @NotNull(message = "Account Type is mandatory")
    AccountType accountType;
}
