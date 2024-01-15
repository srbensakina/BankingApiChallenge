package com.chanllenge.bankingapi.controller;

import com.chanllenge.bankingapi.dto.BankAccountDto;
import com.chanllenge.bankingapi.dto.CreateBankAccountRequest;
import com.chanllenge.bankingapi.dto.TransferHistoryResponse;
import com.chanllenge.bankingapi.model.BankAccount;
import com.chanllenge.bankingapi.service.BankAccountService;
import com.chanllenge.bankingapi.service.TransferHistoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final TransferHistoryService transferHistoryService;


    @PostMapping("/create")
    public ResponseEntity<?> createBankAccount(
            @RequestBody CreateBankAccountRequest createBankAccountRequest) {

        try {
            BankAccount bankAccount = bankAccountService.createBankAccount(createBankAccountRequest);
            return new ResponseEntity<>(bankAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferAmounts(@RequestParam Float amountTransferred, @RequestParam Long transferredTo, @RequestParam Long transferredFrom) {
        try {
            bankAccountService.transferAmounts(amountTransferred, transferredTo, transferredFrom);
            return new ResponseEntity<>("Amount Transferred successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/transfer_history/{account_id}")
    public ResponseEntity<?> getTransferHistory(@PathVariable Long account_id) {
        try {
            List<TransferHistoryResponse> transferHistoryList = transferHistoryService.getTransferHistory(account_id);
            return new ResponseEntity<>(transferHistoryList, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<BankAccountDto>> getAllBankAccounts(){
        return new ResponseEntity<>(bankAccountService.getAllBankAccounts() , HttpStatus.OK);
    }

    @GetMapping("{bankAccountId}")
    public ResponseEntity<?> getBankAccountById(@PathVariable Long bankAccountId){
        try {
            return new ResponseEntity<>(bankAccountService.getBankAccountById(bankAccountId), HttpStatus.OK);
        }catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.OK);
        }
    }
}



