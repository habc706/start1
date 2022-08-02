import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class aA {
    @Test
    public void de() throws ParseException {
        Date date = new Date();
        long datetime =  date.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        //2022-07-27 08:45:53
        String str = sdf.format(datetime);
        System.out.println(datetime);

        System.out.println(str);

        Date testdate = sdf.parse("2022-07-27 08:45:53");
        System.out.println(testdate);

        System.out.println(  testdate.compareTo(date));

        Calendar calendar = Calendar.getInstance();
        System.out.println(sdf.format(calendar.getTime()));

        calendar.add(Calendar.DAY_OF_MONTH,-1);
        System.out.println(sdf.format(calendar.getTime()));

    }
}
