package csvCheck;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
public class CheckResult {

    @CsvBindByName(column = "executionDateTime")
    @CsvBindByPosition(position = 0)
    private String executionDateTime;


    @CsvBindByName(column = "targetExtractDate")
    @CsvBindByPosition(position = 1)
    private String targetExtractDate;


    @CsvBindByName(column = "utcStartDate")
    @CsvBindByPosition(position = 2)
    private String utcStartDate;


    @CsvBindByName(column = "utcEndDate")
    @CsvBindByPosition(position = 3)
    private String utcEndDate;

    @CsvBindByName(column = "isCorrectExtractDate")
    @CsvBindByPosition(position = 4)
    private String isCorrectExtractDate;

    @CsvBindByName(column = "isCorrectUtcStartDate")
    @CsvBindByPosition(position = 5)
    private String isCorrectUtcStartDate;

    @CsvBindByName(column = "isCorrectUtcEndDate")
    @CsvBindByPosition(position = 6)
    private String isCorrectUtcEndDate;
}
