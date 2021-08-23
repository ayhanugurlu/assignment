package nl.rabobank.dto.authorizations;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(as = PowerOfAttorneyDto.class)

public class PowerOfAttorneyDto
{
    String granteeName;
    String grantorName;
    String accountNumber;
    AuthorizationDto authorization;
}
