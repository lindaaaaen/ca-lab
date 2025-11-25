package app;

import data_access.FinnhubEarningsDataAccessObject;
import entity.EarningsRecord;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestEarningsPopup {

    public static void main(String[] args) throws Exception {
        String apiKey = "d4977ehr01qshn3kvpt0d4977ehr01qshn3kvptg";

        FinnhubEarningsDataAccessObject earningsDAO =
                new FinnhubEarningsDataAccessObject(
                        "https://finnhub.io/api/v1",
                        apiKey
                );

        String symbol = "AAPL";

        List<EarningsRecord> records =
                earningsDAO.getEarningsForLastYear(symbol);

        // ===== Build nicely formatted text =====
        StringBuilder sb = new StringBuilder();
        sb.append("Earnings data for ").append(symbol).append("\n");
        sb.append("====================================\n\n");

        if (records.isEmpty()) {
            sb.append("No earnings records returned.\n");
        } else {
            for (EarningsRecord r : records) {
                sb.append(String.format(
                        "Period:      %s%n" +
                                "Actual EPS:  %.2f%n" +
                                "Estimate EPS: %.2f%n" +
                                "Surprise:    %s%n" +
                                "Report Date: %s%n",
                        r.getPeriod(),
                        r.getActualEps(),
                        r.getEstimateEps(),
                        (r.getSurprise() == null || r.getSurprise().isNaN()
                                ? "N/A"
                                : String.format("%.2f", r.getSurprise())),
                        r.getReportDate()
                ));
                sb.append("\n"); // blank line between records
            }
        }

        // ===== Create a nicer-looking popup =====
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setCaretPosition(0); // scroll to top

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Company Earnings",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
