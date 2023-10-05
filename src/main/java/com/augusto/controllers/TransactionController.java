package com.augusto.controllers;


import com.augusto.domain.transaction.Transactions;
import com.augusto.dtos.TransactionDTO;
import com.augusto.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;


    @PostMapping
    public ResponseEntity<Transactions>createTransaction(@RequestBody TransactionDTO transactionDTO)throws Exception{
        Transactions newTransaction = this.service.createTransaction(transactionDTO);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
}
