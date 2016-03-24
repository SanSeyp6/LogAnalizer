package test;

import java.util.Arrays;
import java.util.List;

import util.StringComparison;

/**
 * Данный тест проверяет работу метода {@link StringComparison#computeAllLCSubstringsForStringGroup(java.util.List)}
 * @author pih
 *
 */
public class Test19 {

	public static void main(String[] args) {
		List<String> similarStrings = Arrays.asList(

				"ho",
				"ho",
				"to=<",
				"ho",
				"to=<",
				"ho",
				"to=<"
/*				
				"st ", 
				"st ", 
				"=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.",
				"st ",
				"=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.",
				"st ", 
				".r.20.1.5.invino@gmail.com>, relay=alt1."
*/
/*				
				"64.233.164.27",
				"64.233.164.27",
				"74.125.23.26]:25, delay=264, delays=262/0/1.6/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26",
				"173.194.71.27",
				"74.125.23.26]:25, delay=624, delays=622/0.01/1.4/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26",
				"64.233.163.27",
				"74.125.203.27]:25, delay=1345, delays=1342/0/1.3/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.203.27]"
*/
/*								
				"164.27",
				"164.27",
				"26",
				"194.71.27",
				"26]:25, delay=624, delays=622/0.01/1.4/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26",
				"163.27",
				"27]:25, delay=1345, delays=1342/0/1.3/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.203.27"
//[2]
*/		
				
/*								
				"64.233.164",
				"64.233.164",
				"74.125",
				"173.194.71",
				"74.125.23.26]:25, delay=624, delays=622/0.01/1.4/0.94, dsn=4",
				"64.233.163",
				"74.125.203.27]:25, delay=1345, delays=1342/0/1.3/0.94, dsn=4"
//[.1, 4.]
*/				
		);

		System.out.println(StringComparison.computeAllLCSubstringsForStringGroup(similarStrings));
	}

}
