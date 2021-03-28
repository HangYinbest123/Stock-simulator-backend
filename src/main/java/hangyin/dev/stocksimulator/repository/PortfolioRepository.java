package hangyin.dev.stocksimulator.repository;

import hangyin.dev.stocksimulator.entity.User;
import hangyin.dev.stocksimulator.entity.UserOwnedStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<UserOwnedStock, Long> {
    UserOwnedStock getUserOwnedStockBySymbolAndUser(String symbol, User user);

    List<UserOwnedStock> getUserOwnedStocksByUser(User user);
}
