package csvCheck;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvException;

public class CsvChecker {

    public static void main(String[] args) {

        ItemFileDao csvFileDao = new ItemFileDao();

        try (Reader reader = Files.newBufferedReader(Paths.get("/Users/Shared/develop/projects/study/csvCheck/src/main/resources/test_result_202109171155.csv"));
             Writer writer = Files.newBufferedWriter(Paths.get("item2.csv"));
        ) {

            List<TestResult> testResultList = csvFileDao.read(reader);

            List<CheckResult> checkResultList =
                    testResultList.stream().map(testResult -> {

                        // 2021-09-10T16:00:00.184Z
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Date resultDate  = null;
                        try {
                            resultDate = sdf.parse(testResult.getExecutionDateTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        LocalDateTime executionDateTime = LocalDateTime.ofInstant(resultDate.toInstant(), ZoneId.systemDefault())
                                .plusHours(9);

                        LocalDateTime targetExtractDateTime = executionDateTime.minusDays(30);

                        String trimmedTargetDateTime = targetExtractDateTime.toString().substring(0, 10);

                        CheckResult checkResult = new CheckResult();

                        if (trimmedTargetDateTime.equals(testResult.getTargetExtractDate())) {
                            checkResult.setIsCorrectExtractDate("◯");
                        } else {
                            checkResult.setIsCorrectExtractDate("✕");
                        }

                        String trimmedUtcStartDate = testResult.getUtcStartDate().substring(0, 10);

                        LocalDateTime utcStartDate = executionDateTime.minusDays(31);

                        String utcStartDateString = utcStartDate.toString().substring(0, 10);

                        if (trimmedUtcStartDate.equals(utcStartDateString)) {
                            checkResult.setIsCorrectUtcStartDate("◯");
                        } else {
                            checkResult.setIsCorrectUtcStartDate("✕");
                        }

                        String trimmedUtcEndDate = testResult.getUtcEndDate().substring(0, 10);

                        if (trimmedUtcEndDate.equals(trimmedTargetDateTime)) {
                            checkResult.setIsCorrectUtcEndDate("◯");
                        } else {
                            checkResult.setIsCorrectUtcEndDate("✕");
                        }

                        checkResult.setExecutionDateTime(testResult.getExecutionDateTime());
                        checkResult.setTargetExtractDate(testResult.getTargetExtractDate());
                        checkResult.setUtcStartDate(testResult.getUtcStartDate());
                        checkResult.setUtcEndDate(testResult.getUtcEndDate());
                        return checkResult;
                    }).collect(Collectors.toList());

            csvFileDao.write(writer, checkResultList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

    }
}
