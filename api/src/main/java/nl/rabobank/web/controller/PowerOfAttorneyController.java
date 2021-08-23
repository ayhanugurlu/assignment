package nl.rabobank.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.dto.authorizations.PowerOfAttorneyDto;
import nl.rabobank.service.PowerOfAttorneyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/powerofattorney")
public class PowerOfAttorneyController {

    private final PowerOfAttorneyService powerOfAttorneyService;

    @ApiOperation(value = "Get PowerOfAttorney with account number and grantee name", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account PowerOfAttorney found"),
            @ApiResponse(code = 404, message = "Account PowerOfAttorney not found"),
            @ApiResponse(code = 500, message = "Unknown internal error")
    }
    )
    @GetMapping("/{accountNumber}/{granteeName}")
    public ResponseEntity<PowerOfAttorneyDto> getPowerOfAttorney(@PathVariable String accountNumber,@PathVariable String granteeName){
        return  ResponseEntity.ok(powerOfAttorneyService.getPowerOfAttorney(accountNumber,granteeName));
    }

    @ApiOperation(value = "Get PowerOfAttorneys with account number", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account PowerOfAttorneys found"),
            @ApiResponse(code = 500, message = "Unknown internal error")
    }
    )
    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<PowerOfAttorneyDto>> getPowerOfAttorneys(@PathVariable String accountNumber){
        return  ResponseEntity.ok(powerOfAttorneyService.getPowerOfAttorneys(accountNumber));
    }

    @ApiOperation(value = "Create PowerOfAttorney", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "PowerOfAttorney is created"),
            @ApiResponse(code = 404, message = "Account not found"),
            @ApiResponse(code = 409, message = "PowerOfAttorney duplication"),
            @ApiResponse(code = 500, message = "Unknown internal error")
    }
    )
    @PostMapping
    public ResponseEntity<Void> createPowerOfAttorney(@Valid @RequestBody PowerOfAttorneyDto powerOfAttorneyDto){
        powerOfAttorneyService.create(powerOfAttorneyDto);
        var location = URI.create(String.format("/powerofattorney/%s/%s", powerOfAttorneyDto.getAccountNumber(),powerOfAttorneyDto.getGranteeName()));
        return  ResponseEntity.created(location).build();
    }
}
