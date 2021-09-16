package csvCheck;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * csvファイルとやりとりを行うクラス
 */
public class ItemFileDao {

    public void write(Writer writer, List<CheckResult> beans) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        StatefulBeanToCsv<CheckResult> beanToCsv = new StatefulBeanToCsvBuilder<CheckResult>(writer).build();
        beanToCsv.write(beans);
    }

    public List<TestResult> read(Reader reader) throws CsvException {
        CsvToBean<TestResult> csvToBean = new CsvToBeanBuilder<TestResult>(reader).withType(TestResult.class).build();
        return csvToBean.parse();
    }
}
