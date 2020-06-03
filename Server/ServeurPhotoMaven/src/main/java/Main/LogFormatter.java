package Main;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.*;

public class LogFormatter extends Formatter {
	
	
	   public String format(LogRecord record) {
	      StringBuffer s = new StringBuffer(1000);
	      Date d = new Date(record.getMillis());
	      DateFormat df = DateFormat.getDateTimeInstance(
	                          DateFormat.LONG, DateFormat.MEDIUM, Locale.FRANCE);  
	      s.append(df.format(d) + " ");
	      s.append(formatMessage(record));
	      return s.toString();
	   }
}
