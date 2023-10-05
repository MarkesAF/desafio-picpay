package com.augusto.services;

import com.augusto.domain.user.User;
import com.augusto.domain.user.UserType;
import com.augusto.dtos.UserDTO;
import com.augusto.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validatedTransaction(User sender, BigDecimal amount)throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuario Lojista não esta autorizado a realizar transferencias");
        }
        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findById(Long id)throws Exception{
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new Exception("Usuario nao encontrado"));
    }
    public void saveUser(User user){
        this.repository.save(user);
    }
    public User createUser(UserDTO user){
        User data = new User(user);
        this.saveUser(data);
        return data;
    }
    public List<User>findAll(){
       return this.repository.findAll();
    }
}
