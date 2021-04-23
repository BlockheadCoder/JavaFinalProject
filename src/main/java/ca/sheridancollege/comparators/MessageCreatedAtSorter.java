package ca.sheridancollege.comparators;

import java.util.Date;
import java.util.Comparator;

import ca.sheridancollege.beans.Message;

/* not currently used/necessary, may be helpful in the future */
public class MessageCreatedAtSorter implements Comparator<Message>{

	@Override
	public int compare(Message o1, Message o2) {
		Date m1Date = o1.getCreated_at();
		Date m2Date = o2.getCreated_at();
			
		return m1Date.compareTo(m2Date);
	}
}
