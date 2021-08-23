package nl.rabobank.exception;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class PowerOfAttorneyNotFoundException extends RuntimeException {
    private final String accountNumber;
    private final String granteeName;
}
