package comparators;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Locale;

import ca.sheridancollege.beans.Message;

public class MessageCreatedAtSorter implements Comparator<Message>{

	@Override
	public int compare(Message o1, Message o2) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS", Locale.ENGLISH);
		
		try {
			Date m1Date = format.parse(o1.getCreated_at());
			Date m2Date = format.parse(o2.getCreated_at());
			
			return m1Date.compareTo(m2Date);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
