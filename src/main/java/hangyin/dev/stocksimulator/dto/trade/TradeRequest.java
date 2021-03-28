package hangyin.dev.stocksimulator.dto.trade;

import javax.validation.constraints.NotNull;

public class TradeRequest {
    @NotNull
    private TradeType tradeType;
    @NotNull
    private long quantity;
    @NotNull
    private double unitCost;
    @NotNull
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public long getQuantity() {
        return quantity;
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
}
