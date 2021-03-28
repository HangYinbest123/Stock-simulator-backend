package hangyin.dev.stocksimulator.entity;

import javax.persistence.*;

@Entity
public class UserOwnedStock {
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;

    @ManyToOne
    private User user;

    private String symbol;

    private long quantity;

    private double unitCost;

}
