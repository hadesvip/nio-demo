import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

/**
 * Created by wangyong on 2017/8/25.
 */
public class BigDecimalTest {


  @Test
  public void percentTest() {

    BigDecimal first = new BigDecimal("366");
    BigDecimal second = new BigDecimal("566");

    BigDecimal percentNum = new BigDecimal(
        second.subtract(first)
            .divide(second, 2, BigDecimal.ROUND_HALF_UP).toString());

    System.out.println(percentNum);

    NumberFormat percent = NumberFormat.getPercentInstance();
    System.out.println(percentNum.doubleValue());
    String percentStr = percent.format(percentNum);
    System.out.println(percentStr);
  }


  @Test
  public void timeTest() {
    String dateStr = "2017-08-23 17:30:25";
   // System.out.println(new Date(dateStr));
    DateTime.parse(dateStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
    System.out.println(
        DateTime.parse(dateStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate());

  }

}
