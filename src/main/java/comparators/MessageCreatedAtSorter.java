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
		Date m1Date = o1.getCreated_at();
		Date m2Date = o2.getCreated_at();
			
		return m1Date.compareTo(m2Date);
	}
}
