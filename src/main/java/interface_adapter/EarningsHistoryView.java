package interface_adapter;

import interface_adapter.earnings_history.EarningsHistoryController;
import interface_adapter.earnings_history.EarningsHistoryState;
import interface_adapter.earnings_history.EarningsHistoryViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EarningsHistoryView extends JPanel implements PropertyChangeListener {

    private final EarningsHistoryViewModel viewModel;
    private EarningsHistoryController controller;

    private final JTextField symbolField = new JTextField(10);
    private final JButton searchButton = new JButton("Search");
    private final JTextArea resultsArea = new JTextArea(10, 40);
    private final JLabel errorLabel = new JLabel();

    public EarningsHistoryView(EarningsHistoryViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(new JLabel("Symbol:"));
        top.add(symbolField);
        top.add(searchButton);
        add(top, BorderLayout.NORTH);

        resultsArea.setEditable(false);
        add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        errorLabel.setForeground(Color.RED);
        add(errorLabel, BorderLayout.SOUTH);

        searchButton.addActionListener(e ->
                controller.onSearch(symbolField.getText()));
    }

    public String getViewName() {
        return EarningsHistoryViewModel.VIEW_NAME;
    }

    public void setController(EarningsHistoryController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EarningsHistoryState state = (EarningsHistoryState) evt.getNewValue();
        symbolField.setText(state.getSymbol());
        errorLabel.setText(state.getErrorMessage());

        StringBuilder sb = new StringBuilder();
        state.getRecords().forEach(r -> sb
                .append(r.getPeriod())
                .append("  Actual: ").append(r.getActualEps())
                .append("  Est: ").append(r.getEstimateEps())
                .append("  Date: ").append(r.getReportDate())
                .append("\n"));
        resultsArea.setText(sb.toString());
    }
}
