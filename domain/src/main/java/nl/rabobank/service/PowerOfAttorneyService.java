package nl.rabobank.service;

import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;

import java.util.List;

public interface PowerOfAttorneyService {

    PowerOfAttorneyDto getPowerOfAttorney(String accountNumber, String granteeName);

    List<PowerOfAttorneyDto> getPowerOfAttorneys(String accountNumber);

    PowerOfAttorneyDto create(PowerOfAttorneyDto powerOfAttorneyDto);
}
