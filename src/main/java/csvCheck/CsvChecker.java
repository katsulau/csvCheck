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

        try (Reader reader = Files.newBufferedReader(Paths.get("/Users/Shared/develop/projects/study/csvCheck/src/main/resources/test_result_202109161853.csv"));
             Writer writer = Files.newBufferedWriter(Paths.get("item2.csv"));
        ) {

            List<TestResult> testResultList = csvFileDao.read(reader);

            System.out.println(testResultList.size());

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

                        LocalDateTime targetDateTime = LocalDateTime.ofInstant(resultDate.toInstant(), ZoneId.systemDefault())
                                .plusHours(9);

                        System.out.println(targetDateTime);

                        CheckResult checkResult = new CheckResult();
                        checkResult.setExecutionDateTime(testResult.getExecutionDateTime());
                        checkResult.getExecutionDateTime();
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
