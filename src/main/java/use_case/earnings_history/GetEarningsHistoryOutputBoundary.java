package use_case.earnings_history;

public interface GetEarningsHistoryOutputBoundary {
    void presentSuccess(GetEarningsHistoryOutputData outputData);
    void presentFailure(String errorMessage);
}
