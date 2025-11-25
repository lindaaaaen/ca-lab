package use_case.earnings_history;

import entity.EarningsRecord;
import java.util.List;

public class GetEarningsHistoryInteractor implements GetEarningsHistoryInputBoundary {

    private final EarningsDataAccessInterface earningsGateway;
    private final GetEarningsHistoryOutputBoundary presenter;

    public GetEarningsHistoryInteractor(EarningsDataAccessInterface earningsGateway,
                                        GetEarningsHistoryOutputBoundary presenter) {
        this.earningsGateway = earningsGateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(GetEarningsHistoryInputData inputData) {
        String symbol = inputData.getSymbol();
        if (symbol == null || symbol.trim().isEmpty()) {
            presenter.presentFailure("Please enter a stock symbol.");
            return;
        }
        symbol = symbol.trim().toUpperCase();

        try {
            List<EarningsRecord> records = earningsGateway.getEarningsForLastYear(symbol);

            if (records.isEmpty()) {
                presenter.presentFailure("No results found for: " + symbol);
            } else {
                presenter.presentSuccess(
                        new GetEarningsHistoryOutputData(symbol, records)
                );
            }
        } catch (Exception e) {
            presenter.presentFailure("Error fetching earnings: " + e.getMessage());
        }
    }
}
