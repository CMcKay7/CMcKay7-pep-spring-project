package com.example.service;
import com.example.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(String username, String password) throws IllegalArgumentException{
        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if(password == null || password.length() < 4){
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        if(existingAccount.isPresent()){
            throw new IllegalArgumentException("Username is already in use");
        }

        Account newAccount = new Account(username, password);
        return accountRepository.save(newAccount);
    }

    public Account loginAccount(String username, String password) throws IllegalArgumentException{
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        if(!existingAccount.isPresent()){
            throw new IllegalArgumentException();
        }

        Account account = existingAccount.get();
        if(!account.getPassword().equals(password)){
            throw new IllegalArgumentException();
        }

        return account;
    }
}
