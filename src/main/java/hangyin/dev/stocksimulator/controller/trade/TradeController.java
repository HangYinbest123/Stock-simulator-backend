package hangyin.dev.stocksimulator.controller.trade;

import hangyin.dev.stocksimulator.dto.trade.TradeRequest;
import hangyin.dev.stocksimulator.entity.UserOwnedStock;
import hangyin.dev.stocksimulator.service.TradeService;
import hangyin.dev.stocksimulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping(path="user/{userId}")
public class TradeController {
    @Autowired
    TradeService tradeService;
    @Autowired
    UserService userService;

    @PostMapping(path = "/trade")
    public ResponseEntity<?> trade(@PathVariable String userId, @RequestBody TradeRequest tradeRequest) throws Exception {
        UserOwnedStock userOwnedStock = tradeService.trade(userId, tradeRequest);
        if(userOwnedStock == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(userOwnedStock, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/portfolio")
    public ResponseEntity<?> getUserPortfolio(@PathVariable String userId) throws Exception {
        List<UserOwnedStock> userOwnedStocks = tradeService.getUserPortfolio(userService.findUserByUserId(userId));
        return new ResponseEntity<>(userOwnedStocks, HttpStatus.OK);
    }
}
