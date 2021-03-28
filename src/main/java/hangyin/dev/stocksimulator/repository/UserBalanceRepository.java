package hangyin.dev.stocksimulator.repository;

import hangyin.dev.stocksimulator.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    UserBalance getUserBalanceByUserId(String userId);
}
