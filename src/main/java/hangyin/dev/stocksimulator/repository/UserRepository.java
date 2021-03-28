package hangyin.dev.stocksimulator.repository;

import hangyin.dev.stocksimulator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByThirdPartyId (String thirdPartyId);

    User findUserByUserId(String userId);
}
