package use_case.earnings_history;

import entity.EarningsRecord;
import java.util.List;

public interface EarningsDataAccessInterface {
    /** Returns earnings records for the last ~1 year for the given symbol. */
    List<EarningsRecord> getEarningsForLastYear(String symbol) throws Exception;
}

