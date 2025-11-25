package interface_adapter.earnings_history;

import interface_adapter.ViewModel;

public class EarningsHistoryViewModel extends ViewModel<EarningsHistoryState> {

    public static final String VIEW_NAME = "earnings history";

    public EarningsHistoryViewModel() {
        super(VIEW_NAME);
        // IMPORTANT: use your base ViewModel’s setState method
        setState(new EarningsHistoryState());
    }
}
