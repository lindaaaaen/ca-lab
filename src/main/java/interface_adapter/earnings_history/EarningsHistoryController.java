package interface_adapter.earnings_history;

import use_case.earnings_history.GetEarningsHistoryInputBoundary;
import use_case.earnings_history.GetEarningsHistoryInputData;

public class EarningsHistoryController {

    private final GetEarningsHistoryInputBoundary interactor;

    public EarningsHistoryController(GetEarningsHistoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void onSearch(String symbol) {
        interactor.execute(new GetEarningsHistoryInputData(symbol));
    }
}
