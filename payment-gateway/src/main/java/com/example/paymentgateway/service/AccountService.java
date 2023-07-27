package com.example.paymentgateway.service;

import com.example.paymentgateway.dao.AccountDao;
import com.example.paymentgateway.entity.Account;
import com.example.paymentgateway.exception.InsuffientAmount;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;

    public  double getBalance(String email){
       return getByEmail(email)
               .orElseThrow(EntityNotFoundException::new)
               .getAmount();
    }
    @Transactional
    public double deposit(String email,double amount){
        double balance=getBalance(email);
        double updateAmount= (balance + amount);
        Optional<Account> accountOptional= getByEmail(email);
        if (accountOptional.isPresent()){
            Account account1=accountOptional.get(); //DB ka id pr lr
            account1.setAmount(updateAmount);
            accountDao.save(account1);
            return updateAmount;
        }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    @Transactional
    public  double withDraw(String email,double amount){
        double balance= getBalance(email);
        if (balance<amount) {
            throw new InsuffientAmount();
        }
        double updateAmount = (balance - amount);
        Optional<Account> accountOptional= getByEmail(email);
        if(accountOptional.isPresent()){
           Account account= accountOptional.get();
           account.setAmount(updateAmount);
           accountDao.save(account);
           return  updateAmount;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void transfer(String fromEmail,String  toEmail,double amount){
        withDraw(fromEmail,amount);
        deposit(toEmail,amount);
    }

    private Optional<Account> getByEmail(String email) {
        return accountDao.findByEmail(email);
    }
}
