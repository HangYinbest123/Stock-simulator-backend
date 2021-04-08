package hangyin.dev.stocksimulator.service;

import hangyin.dev.stocksimulator.dto.AddBalanceRequest;
import hangyin.dev.stocksimulator.dto.balance.AddBalanceReason;
import hangyin.dev.stocksimulator.entity.User;
import hangyin.dev.stocksimulator.entity.UserBalance;
import hangyin.dev.stocksimulator.repository.UserBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
public class BalanceService {
    @Autowired
    UserBalanceRepository userBalanceRepository;
    @Autowired
    UserService userService;

    public UserBalance getUserBalanceByUserId(String userId){
        User user = userService.findUserByUserId(userId);
        if(user == null){
            // exception: user doesn't exist in db
            return null;
        }

        if(userBalanceRepository.getUserBalanceByUserId(userId) == null){
            UserBalance userBalance = new UserBalance();
            userBalance.setBalance(0);
            userBalance.setUser(user);
            userBalance.setUserId(userId);
            userBalanceRepository.save(userBalance);
            return userBalance;
        }else{
            return userBalanceRepository.getUserBalanceByUserId(userId);
        }
    }

    public UserBalance addUserBalance(String userId, AddBalanceRequest addBalanceRequest, AddBalanceReason reason) throws Exception {
        UserBalance userBalance = getUserBalanceByUserId(userId);
        double updatedBalanceAmount = userBalance.getBalance() + addBalanceRequest.getAmount();
        if(reason == AddBalanceReason.DirectDeposit){
            double updatedDirectDeposit = userBalance.getDirectDeposit() + addBalanceRequest.getAmount();
            userBalance.setDirectDeposit(updatedDirectDeposit);
        }
        if(updatedBalanceAmount < 0){
            throw new Exception("updated balance can't be less than 0: ");
        }else{
            userBalance.setBalance(updatedBalanceAmount);
            userBalanceRepository.save(userBalance);
            return userBalance;
        }
    }
}
