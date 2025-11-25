package entity;

public class EarningsRecord {
    private final String symbol;
    private final String period;      // e.g. "2024-Q2" or a date string
    private final double actualEps;
    private final double estimateEps;
    private final Double surprise;    // can be null
    private final String reportDate;

    public EarningsRecord(String symbol,
                          String period,
                          double actualEps,
                          double estimateEps,
                          Double surprise,
                          String reportDate) {
        this.symbol = symbol;
        this.period = period;
        this.actualEps = actualEps;
        this.estimateEps = estimateEps;
        this.surprise = surprise;
        this.reportDate = reportDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPeriod() {
        return period;
    }

    public double getActualEps() {
        return actualEps;
    }

    public double getEstimateEps() {
        return estimateEps;
    }

    public Double getSurprise() {
        return surprise;
    }

    public String getReportDate() {
        return reportDate;
    }

    public String toString() {
        String surpriseText = (surprise == null || surprise.isNaN())
                ? "N/A"
                : String.format("%.2f", surprise);

        return String.format(
                "Period: %s | Actual EPS: %.2f | Estimate EPS: %.2f | Surprise: %s | Report Date: %s",
                period,
                actualEps,
                estimateEps,
                surpriseText,
                reportDate
        );
    }
}

