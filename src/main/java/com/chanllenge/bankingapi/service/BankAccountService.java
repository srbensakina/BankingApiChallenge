package com.chanllenge.bankingapi.service;

import com.chanllenge.bankingapi.dto.BankAccountDto;
import com.chanllenge.bankingapi.dto.CreateBankAccountRequest;
import com.chanllenge.bankingapi.model.BankAccount;
import com.chanllenge.bankingapi.model.Customer;
import com.chanllenge.bankingapi.model.TransferHistory;
import com.chanllenge.bankingapi.repository.BankAccountRepository;
import com.chanllenge.bankingapi.repository.CustomerRepository;
import com.chanllenge.bankingapi.repository.TransferHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;

    private final TransferHistoryRepository transferHistoryRepository;

    @Transactional
    public BankAccount createBankAccount(CreateBankAccountRequest createBankAccountRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(createBankAccountRequest.getCustomerId());

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            BankAccount bankAccount = BankAccount.builder()
                    .balance(createBankAccountRequest.getInitialDeposit()).customer(customer).build();

            customer.getBankAccounts().add(bankAccount);

            customerRepository.save(customer);

            return bankAccountRepository.save(bankAccount);
        } else {
            throw new EntityNotFoundException("Customer not found with id: " + createBankAccountRequest.getCustomerId());
        }
    }

    @Transactional
    public void transferAmounts(Float amountTransferred, Long transferredTo, Long transferredFrom) {
        Optional<BankAccount> optionalBankAccountTransferredTo = bankAccountRepository.findById(transferredTo);
        Optional<BankAccount> optionalBankAccountTransferredFrom = bankAccountRepository.findById(transferredFrom);

        if (optionalBankAccountTransferredFrom.isPresent() && optionalBankAccountTransferredTo.isPresent()) {
            BankAccount bankAccountTo = optionalBankAccountTransferredTo.get();
            BankAccount bankAccountFrom = optionalBankAccountTransferredFrom.get();

            if (bankAccountTo.getId().equals(bankAccountFrom.getId())) {
                throw new IllegalArgumentException("Cannot transfer money to the same account");
            }

            bankAccountFrom.setBalance(bankAccountFrom.getBalance() - amountTransferred);
            bankAccountTo.setBalance(bankAccountTo.getBalance() + amountTransferred);

            bankAccountRepository.save(bankAccountFrom);
            bankAccountRepository.save(bankAccountTo);

            TransferHistory transferHistory = TransferHistory.builder()
                    .amount(amountTransferred).destinationAccount(bankAccountTo).sourceAccount(bankAccountFrom).transferDateTime(LocalDateTime.now()).build();

            transferHistoryRepository.save(transferHistory);
        } else {
            throw new EntityNotFoundException("Bank Account not found");
        }
    }

    public List<BankAccountDto> getAllBankAccounts() {


        List<BankAccount> accountsList = (List<BankAccount>) bankAccountRepository.findAll();

        return accountsList.stream().map(accounts -> new BankAccountDto(accounts.getId(), accounts.getBalance(), accounts.getCustomer().getId())).collect(Collectors.toList());

    }

    public BankAccountDto getBankAccountById(Long bankAccountId) {

        return bankAccountRepository.findById(bankAccountId)
                .map(bankAccount -> new BankAccountDto(bankAccount.getId(), bankAccount.getBalance(), bankAccount.getCustomer().getId()))
                .orElseThrow(() -> new EntityNotFoundException("No bank account with id:" + bankAccountId));
    }
}
