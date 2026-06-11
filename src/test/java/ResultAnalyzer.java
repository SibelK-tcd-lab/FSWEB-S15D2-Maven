import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResultAnalyzer implements TestWatcher, AfterAllCallback {

    private List<TestResultStatus> testResultsStatus = new ArrayList<>();
    private static final String taskId = "149";

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED;
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        testResultsStatus.add(TestResultStatus.DISABLED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        testResultsStatus.add(TestResultStatus.FAILED);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

        Map<TestResultStatus, Long> summary = testResultsStatus.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long success = summary.get(TestResultStatus.SUCCESSFUL) != null
                ? summary.get(TestResultStatus.SUCCESSFUL)
                : 0;

        long failure = summary.get(TestResultStatus.FAILED) != null
                ? summary.get(TestResultStatus.FAILED)
                : 0;

        long total = success + failure;

        double score = 0.0;
        if (total != 0) {
            score = (double) success / total;
        }

        String userId = "307446";

        JSONObject json = new JSONObject();
        json.put("score", score);
        json.put("taskId", taskId);
        json.put("userId", userId);

        sendTestResult(json.toString());
    }

    private void sendTestResult(String result) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("https://backend.api.workintech.com.tr/nextgen/taskLog/saveJavaTasks");
            StringEntity params = new StringEntity(result);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.close();
        }
    }
}