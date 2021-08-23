package nl.rabobank.dto.authorizations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PowerOfAttorneyDto
{
    String granteeName;
    String grantorName;
    String accountNumber;
    AuthorizationDto authorization;
}
