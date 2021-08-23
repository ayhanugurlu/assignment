package nl.rabobank.exception;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class PowerOfAttorneyDuplicationException extends RuntimeException {
    private final String accountNumber;
    private final String granteeName;
}
