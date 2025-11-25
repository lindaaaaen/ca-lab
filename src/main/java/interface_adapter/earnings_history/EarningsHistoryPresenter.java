package interface_adapter.earnings_history;

import interface_adapter.ViewManagerModel;
import use_case.earnings_history.GetEarningsHistoryOutputBoundary;
import use_case.earnings_history.GetEarningsHistoryOutputData;

public class EarningsHistoryPresenter implements GetEarningsHistoryOutputBoundary {

    private final EarningsHistoryViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public EarningsHistoryPresenter(EarningsHistoryViewModel viewModel,
                                    ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentSuccess(GetEarningsHistoryOutputData outputData) {
        EarningsHistoryState state = viewModel.getState();
        state.setSymbol(outputData.getSymbol());
        state.setRecords(outputData.getRecords());
        state.setErrorMessage("");

        viewModel.setState(state);
        viewModel.firePropertyChange();

        // Switch to earnings view (if using a separate screen)
        viewManagerModel.setState(EarningsHistoryViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void presentFailure(String errorMessage) {
        EarningsHistoryState state = viewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setRecords(java.util.List.of());

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }
}
