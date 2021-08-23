package nl.rabobank.web.error;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ErrorMessage {

    @NonNull
    private final String message;
}