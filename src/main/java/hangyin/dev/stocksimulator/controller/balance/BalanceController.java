package hangyin.dev.stocksimulator.controller.balance;

import hangyin.dev.stocksimulator.dto.AddBalanceRequest;

import hangyin.dev.stocksimulator.dto.balance.AddBalanceReason;
import hangyin.dev.stocksimulator.entity.UserBalance;
import hangyin.dev.stocksimulator.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping(path = "user/{userid}")
public class BalanceController {
    @Autowired
    BalanceService balanceService;

    @GetMapping("/balance")
    public ResponseEntity<?> getUserBalance(@PathVariable String userid) {
        UserBalance userBalance = balanceService.getUserBalanceByUserId(userid);
        return new ResponseEntity<>(userBalance, HttpStatus.OK);
    }

    @PutMapping("/balance")
    public ResponseEntity<?> addUserBalance(@PathVariable String userid, @RequestBody AddBalanceRequest addBalanceRequest) throws Exception {
        UserBalance userBalance = balanceService.addUserBalance(userid, addBalanceRequest, AddBalanceReason.DirectDeposit);
        return new ResponseEntity<>(userBalance, HttpStatus.OK);
    }


}
