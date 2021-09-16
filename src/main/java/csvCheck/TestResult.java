package csvCheck;



import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
public class TestResult {


    @CsvBindByPosition(position = 0)
    private String executionDateTime;


    @CsvBindByPosition(position = 1)
    private String targetExtractDate;


    @CsvBindByPosition(position = 2)
    private String utcStartDate;


    @CsvBindByPosition(position = 3)
    private String utcEndDate;
}
