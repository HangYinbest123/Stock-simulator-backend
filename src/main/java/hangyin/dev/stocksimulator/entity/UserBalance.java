package hangyin.dev.stocksimulator.entity;


import javax.persistence.*;

@Entity
public class UserBalance {
    @Id
    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private double balance;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
