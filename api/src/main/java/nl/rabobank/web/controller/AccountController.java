package nl.rabobank.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import nl.rabobank.dto.account.AccountDto;
import nl.rabobank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @ApiOperation(value = "Get account with account number", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account found and return"),
            @ApiResponse(code = 404, message = "Account not found"),
            @ApiResponse(code = 500, message = "Unknown internal error"),
    }
    )
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @ApiOperation(value = "Create account", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Account created"),
            @ApiResponse(code = 409, message = "Account already exist"),
            @ApiResponse(code = 500, message = "Unknown internal error")
    }
    )
    @PostMapping("/")
    public ResponseEntity<Void> createAccount(@Valid @RequestBody AccountDto accountDTO) {
        accountService.create(accountDTO);
        var location = URI.create(String.format("/account/%s", accountDTO.getAccountNumber()));
        return ResponseEntity.created(location).build();
    }
}
