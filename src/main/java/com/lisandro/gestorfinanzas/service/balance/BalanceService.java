package com.lisandro.gestorfinanzas.service.balance;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.model.Balance;
import com.lisandro.gestorfinanzas.model.UserSec;
import com.lisandro.gestorfinanzas.repository.IBalanceRepository;
import com.lisandro.gestorfinanzas.service.user.IUserService;

@Service
public class BalanceService implements IBalanceService {

    private final IBalanceRepository balanceRepository;
    private final IUserService userService;
    
    public BalanceService(IBalanceRepository balanceRepository, IUserService userService) {
        this.balanceRepository = balanceRepository;
        this.userService = userService;
    }

    @Override
    public List<Balance> findAll() {
        return balanceRepository.findAll();
    }

    @Override
    public Balance findById(Long id) {
        return balanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Balance no encontrado con id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        balanceRepository.deleteById(id);
    }

    @Override
    public Balance save(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public Balance findByUsername(String username) {
        UserSec user = userService.findByUsername(username);
        if (user.getBalance() == null) {
            throw new RuntimeException("El usuario no tiene balance asociado");
        }
        return user.getBalance();
    }

    @Override
    public Balance updateBalance(String username, double ars, double dolares) {
        Balance balance = findByUsername(username);
        balance.setArs(ars);
        balance.setDolares(dolares);
        return balanceRepository.save(balance);
    }

}
