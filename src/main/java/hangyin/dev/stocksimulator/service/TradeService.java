package hangyin.dev.stocksimulator.service;

import hangyin.dev.stocksimulator.dto.AddBalanceRequest;
import hangyin.dev.stocksimulator.dto.trade.TradeRequest;
import hangyin.dev.stocksimulator.dto.trade.TradeType;
import hangyin.dev.stocksimulator.entity.User;
import hangyin.dev.stocksimulator.entity.UserBalance;
import hangyin.dev.stocksimulator.entity.UserOwnedStock;
import hangyin.dev.stocksimulator.repository.PortfolioRepository;
import hangyin.dev.stocksimulator.repository.UserBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    UserBalanceRepository userBalanceRepository;
    @Autowired
    UserService userService;
    @Autowired
    BalanceService balanceService;

    public UserOwnedStock trade(String userId, TradeRequest tradeRequest) throws Exception {
        User user = userService.findUserByUserId(userId);
        if(user == null){
            // exception, user not exist
            return null;
        }
        UserOwnedStock userOwnedStock = portfolioRepository.getUserOwnedStockBySymbolAndUser(tradeRequest.getSymbol(), user);
        if (tradeRequest.getTradeType() == TradeType.Buy) {
            userOwnedStock = buy(userId, tradeRequest, userOwnedStock);
            if(userOwnedStock != null){
                portfolioRepository.save(userOwnedStock);
                return userOwnedStock;
            }
        } else {
            userOwnedStock = sell(userId, tradeRequest, userOwnedStock);
            return userOwnedStock;
        }
        return null;
    }

    // transactional??
    public UserOwnedStock buy(String userId, TradeRequest tradeRequest, UserOwnedStock userOwnedStock) throws Exception {

        UserBalance userBalance = userBalanceRepository.getUserBalanceByUserId(userId);
        double totalCost = tradeRequest.getUnitCost() * tradeRequest.getQuantity();
        if(userBalance.getBalance() > totalCost){
            // deduct balance
            AddBalanceRequest addBalanceRequest = new AddBalanceRequest();
            addBalanceRequest.setAmount(-1 * totalCost);
            balanceService.addUserBalance(userId, addBalanceRequest);

            if(userOwnedStock != null){
                //user owns this stock
                long updatedQuantity = userOwnedStock.getQuantity() + tradeRequest.getQuantity();
                double updatedTotalCost = userOwnedStock.getQuantity() * userOwnedStock.getUnitCost() + tradeRequest.getQuantity() * tradeRequest.getUnitCost();
                double updatedUnitCost = updatedTotalCost/updatedQuantity;
                userOwnedStock.setUnitCost(updatedUnitCost);
                userOwnedStock.setQuantity(updatedQuantity);
            }else{
                // user didn't own this stock
                userOwnedStock = new UserOwnedStock();
                userOwnedStock.setUser(userService.findUserByUserId(userId));
                userOwnedStock.setQuantity(tradeRequest.getQuantity());
                userOwnedStock.setSymbol(tradeRequest.getSymbol());
                userOwnedStock.setUnitCost(tradeRequest.getUnitCost());
            }
            return userOwnedStock;
        }else{
            // exception: not enough fund
            return null;
        }
    }

    public UserOwnedStock sell(String userId, TradeRequest tradeRequest, UserOwnedStock userOwnedStock) throws Exception {
        if(userOwnedStock == null){
            // exception, user didn't own this stock
            return null;
        }

        long quantityAfterSell = userOwnedStock.getQuantity() + tradeRequest.getQuantity();
        if(quantityAfterSell < 0){
            // exception, user doesn't have enough stocks to sell
            return null;
        }
        userOwnedStock.setQuantity(quantityAfterSell);
        portfolioRepository.save(userOwnedStock);

        // add money
        AddBalanceRequest addBalanceRequest = new AddBalanceRequest();
        addBalanceRequest.setAmount(tradeRequest.getQuantity() * tradeRequest.getUnitCost());
        balanceService.addUserBalance(userId, addBalanceRequest);
        return userOwnedStock;
    }

    public List<UserOwnedStock> getUserPortfolio(User user){
        return portfolioRepository.getUserOwnedStocksByUser(user);
    }

}
