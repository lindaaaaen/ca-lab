package interface_adapter.earnings_history;

import entity.EarningsRecord;
import java.util.ArrayList;
import java.util.List;

public class EarningsHistoryState {
    private String symbol = "";
    private List<EarningsRecord> records = new ArrayList<>();
    private String errorMessage = "";

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public List<EarningsRecord> getRecords() { return records; }
    public void setRecords(List<EarningsRecord> records) { this.records = records; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}

