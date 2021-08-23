package nl.rabobank.exception;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class AccountNotFoundException extends RuntimeException {
    private final String accountNumber;
}
