package com.augusto.services;


import com.augusto.domain.transaction.Transactions;
import com.augusto.domain.user.User;
import com.augusto.dtos.TransactionDTO;
import com.augusto.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService service;
    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transactions createTransaction(TransactionDTO transaction)throws Exception{
        User sender = this.service.findById(transaction.senderId());
        User receiver = this.service.findById(transaction.receiverId());

        service.validatedTransaction(sender,transaction.value());


        boolean isAuthorized = authorizeTransaction(sender, transaction.value());
        if(!isAuthorized){
            throw new Exception("Transação não autorizada");
        }

        Transactions newTransaction = new Transactions();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.service.saveUser(sender);
        this.service.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");


        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value)throws Exception{
        ResponseEntity<Map> responses =restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

        if(responses.getStatusCode() == HttpStatus.OK){
            String message = (String) responses.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else return false;
    }

}
