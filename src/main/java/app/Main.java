package app;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.*;
import data_access.FinnhubEarningsDataAccessObject;
import entity.EarningsRecord;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addLoggedInView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .addChangePasswordUseCase()
                .addEarningsHistoryUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
        String apiKey = "d4977ehr01qshn3kvpt0d4977ehr01qshn3kvptg";

        FinnhubEarningsDataAccessObject earningsDAO =
                new FinnhubEarningsDataAccessObject(
                        "https://finnhub.io/api/v1",
                        apiKey
                );

        // Direct API call
        List<EarningsRecord> records =
                earningsDAO.getEarningsForLastYear("AAPL");

        // Print results
        System.out.println("Earnings data for AAPL:");
        System.out.println("------------------------");

        for (EarningsRecord record : records) {
            System.out.println(record);
        }

        if (records.isEmpty()) {
            System.out.println("No earnings records returned.");
        }
    }
}




