package data_access;

import entity.EarningsRecord;
import use_case.earnings_history.EarningsDataAccessInterface;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinnhubEarningsDataAccessObject implements EarningsDataAccessInterface {

    private final String baseUrl;   // e.g. "https://finnhub.io/api/v1"
    private final String apiKey;    // your Finnhub token
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public FinnhubEarningsDataAccessObject(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl.endsWith("/")
                ? baseUrl.substring(0, baseUrl.length() - 1)
                : baseUrl;
        this.apiKey = apiKey;
    }

    @Override
    public List<EarningsRecord> getEarningsForLastYear(String symbol) throws Exception {
        String encodedSymbol = URLEncoder.encode(symbol, StandardCharsets.UTF_8);
        String url = baseUrl + "/stock/earnings?symbol=" + encodedSymbol + "&token=" + apiKey;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Finnhub API error: " + response.statusCode() +
                            " body=" + response.body()
            );
        }

        String body = response.body();
        List<EarningsRecord> records = new ArrayList<>();

        // Very simple parser: treat each {...} as one earnings record
        Pattern blockPattern = Pattern.compile("\\{(.*?)}", Pattern.DOTALL);
        Matcher blockMatcher = blockPattern.matcher(body);

        while (blockMatcher.find()) {
            String block = blockMatcher.group(1);

            String period   = extract(block, "period");
            String date     = extract(block, "date");
            String actual   = extract(block, "actual");
            String estimate = extract(block, "estimate");
            String surprise = extract(block, "surprise");

            double actualVal   = parseDoubleSafe(actual);
            double estimateVal = parseDoubleSafe(estimate);
            Double surpriseVal = parseDoubleSafe(surprise);

            records.add(new EarningsRecord(
                    symbol,
                    period,
                    actualVal,
                    estimateVal,
                    surpriseVal,
                    date
            ));
        }

        return records;
    }

    /** Extracts the value of a field from a small JSON object string. */
    private String extract(String jsonBlock, String field) {
        Pattern pattern = Pattern.compile("\"" + field + "\":\\s*\"?(.*?)\"?(,|$)");
        Matcher matcher = pattern.matcher(jsonBlock);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /** Safely parse a double, returning NaN if it fails. */
    private double parseDoubleSafe(String value) {
        try {
            if (value == null || value.isEmpty()) {
                return Double.NaN;
            }
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
}
