package test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.PerformanceStats;

import ru.hse.performance.PerformanceUtil;
import util.ParseMessage;
import util.StringComparison;
import util.Templates;

/**
 * В данном тесте разрабатываем код для Templates.getUnitedTemplate
 * @author aaovchinnikov
 *
 */
public class Test6 {
	public static void main(String[] args) {

		List<String> similarStrings = Arrays.asList(new String[]{
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.164.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 1si9232024lfi.103 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.164.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 jn4si9227864lbc.203 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=264, delays=262/0/1.6/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 r9si11244175ioi.12 - gsmtp (in reply to RCPT TO command))",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[173.194.71.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 192si9255066lfh.71 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=624, delays=622/0.01/1.4/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 72si20143688iob.61 - gsmtp (in reply to RCPT TO command))",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.163.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 dx2si8644490lbc.94 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.203.27]:25, delay=1345, delays=1342/0/1.3/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.203.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 e97si20700057ioi.206 - gsmtp (in reply to RCPT TO command))"
		});

		String lcs = " {sophos_internal_id}: os gmail-smtp-in.l.google.com[3.1.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 si. - gsmtp (in reply to RCPT TO command)";

		PerformanceUtil.initialize();
/*		
		String regexp = "^ (.*)\\{(.*)s(.*)o(.*)p(.*)h(.*)o(.*)s(.*)_(.*)i(.*)n(.*)t(.*)e(.*)r(.*)n(.*)a(.*)l(.*)_(.*)i(.*)d(.*)\\}(.*):(.*) (.*)o(.*)s(.*) (.*)g(.*)m(.*)a(.*)i(.*)l(.*)-(.*)s(.*)m(.*)t(.*)p(.*)-(.*)i(.*)n(.*)\\.(.*)l(.*)\\.(.*)g(.*)o(.*)o(.*)g(.*)l(.*)e(.*)\\.(.*)c(.*)o(.*)m(.*)\\[(.*)3(.*)\\.(.*)1(.*)\\.(.*)2(.*)7(.*)\\](.*) (.*)s(.*)a(.*)i(.*)d(.*):(.*) (.*)4(.*)5(.*)0(.*)-(.*)4(.*)\\.(.*)2(.*)\\.(.*)1(.*) (.*)T(.*)h(.*)e(.*) (.*)u(.*)s(.*)e(.*)r(.*) (.*)y(.*)o(.*)u(.*) (.*)a(.*)r(.*)e(.*) (.*)t(.*)r(.*)y(.*)i(.*)n(.*)g(.*) (.*)t(.*)o(.*) (.*)c(.*)o(.*)n(.*)t(.*)a(.*)c(.*)t(.*) (.*)i(.*)s(.*) (.*)r(.*)e(.*)c(.*)e(.*)i(.*)v(.*)i(.*)n(.*)g(.*) (.*)m(.*)a(.*)i(.*)l(.*) (.*)a(.*)t(.*) (.*)a(.*) (.*)r(.*)a(.*)t(.*)e(.*) (.*)t(.*)h(.*)a(.*)t(.*) (.*)4(.*)5(.*)0(.*)-(.*)4(.*)\\.(.*)2(.*)\\.(.*)1(.*) (.*)p(.*)r(.*)e(.*)v(.*)e(.*)n(.*)t(.*)s(.*) (.*)a(.*)d(.*)d(.*)i(.*)t(.*)i(.*)o(.*)n(.*)a(.*)l(.*) (.*)m(.*)e(.*)s(.*)s(.*)a(.*)g(.*)e(.*)s(.*) (.*)f(.*)r(.*)o(.*)m(.*) (.*)b(.*)e(.*)i(.*)n(.*)g(.*) (.*)d(.*)e(.*)l(.*)i(.*)v(.*)e(.*)r(.*)e(.*)d(.*)\\.(.*) (.*)P(.*)l(.*)e(.*)a(.*)s(.*)e(.*) (.*)r(.*)e(.*)s(.*)e(.*)n(.*)d(.*) (.*)y(.*)o(.*)u(.*)r(.*) (.*)4(.*)5(.*)0(.*)-(.*)4(.*)\\.(.*)2(.*)\\.(.*)1(.*) (.*)m(.*)e(.*)s(.*)s(.*)a(.*)g(.*)e(.*) (.*)a(.*)t(.*) (.*)a(.*) (.*)l(.*)a(.*)t(.*)e(.*)r(.*) (.*)t(.*)i(.*)m(.*)e(.*)\\.(.*) (.*)I(.*)f(.*) (.*)t(.*)h(.*)e(.*) (.*)u(.*)s(.*)e(.*)r(.*) (.*)i(.*)s(.*) (.*)a(.*)b(.*)l(.*)e(.*) (.*)t(.*)o(.*) (.*)r(.*)e(.*)c(.*)e(.*)i(.*)v(.*)e(.*) (.*)m(.*)a(.*)i(.*)l(.*) (.*)a(.*)t(.*) (.*)t(.*)h(.*)a(.*)t(.*) (.*)4(.*)5(.*)0(.*)-(.*)4(.*)\\.(.*)2(.*)\\.(.*)1(.*) (.*)t(.*)i(.*)m(.*)e(.*),(.*) (.*)y(.*)o(.*)u(.*)r(.*) (.*)m(.*)e(.*)s(.*)s(.*)a(.*)g(.*)e(.*) (.*)w(.*)i(.*)l(.*)l(.*) (.*)b(.*)e(.*) (.*)d(.*)e(.*)l(.*)i(.*)v(.*)e(.*)r(.*)e(.*)d(.*)\\.(.*) (.*)F(.*)o(.*)r(.*) (.*)m(.*)o(.*)r(.*)e(.*) (.*)i(.*)n(.*)f(.*)o(.*)r(.*)m(.*)a(.*)t(.*)i(.*)o(.*)n(.*),(.*) (.*)p(.*)l(.*)e(.*)a(.*)s(.*)e(.*) (.*)4(.*)5(.*)0(.*)-(.*)4(.*)\\.(.*)2(.*)\\.(.*)1(.*) (.*)v(.*)i(.*)s(.*)i(.*)t(.*) (.*)4(.*)5(.*)0(.*) (.*)4(.*)\\.(.*)2(.*)\\.(.*)1(.*) (.*) (.*)h(.*)t(.*)t(.*)p(.*)s(.*):(.*)/(.*)/(.*)s(.*)u(.*)p(.*)p(.*)o(.*)r(.*)t(.*)\\.(.*)g(.*)o(.*)o(.*)g(.*)l(.*)e(.*)\\.(.*)c(.*)o(.*)m(.*)/(.*)m(.*)a(.*)i(.*)l(.*)/(.*)a(.*)n(.*)s(.*)w(.*)e(.*)r(.*)/(.*)6(.*)5(.*)9(.*)2(.*) (.*)s(.*)i(.*)\\.(.*) (.*)-(.*) (.*)g(.*)s(.*)m(.*)t(.*)p(.*) (.*)\\((.*)i(.*)n(.*) (.*)r(.*)e(.*)p(.*)l(.*)y(.*) (.*)t(.*)o(.*) (.*)R(.*)C(.*)P(.*)T(.*) (.*)T(.*)O(.*) (.*)c(.*)o(.*)m(.*)m(.*)a(.*)n(.*)d(.*)\\)(.*)$";
		System.out.println("regexp: "+ regexp);
		Pattern pattern = Pattern.compile(regexp);

		PerformanceUtil.printTimeForStep("before regexp matching");
		System.out.println(pattern.matcher(similarStrings.get(0)).matches());
		PerformanceUtil.printTimeForStep("after regexp matching");
*/
		test(similarStrings, lcs);

/*		for(String s: similarStrings){
			System.out.println(getTemplate(s, lcs));	
		}
*/		
		
	}
	

	private static void test(List<String> similarStrings, String lcs){
		StringBuilder sb = new StringBuilder("^.*$");
		int offset=1;
		Pattern pattern;
		boolean matchesAll;
		boolean specialCharAdded;
	
		for(int i=0; i<lcs.length(); i++){
			if(ParseMessage.SPECIAL_CHARS_STRING.indexOf(lcs.charAt(i))!=-1){
				sb.insert(offset, '\\');
				offset++;
				specialCharAdded = true;
				System.out.println("special character added");
			} else {
				specialCharAdded = false;
			}
			sb.insert(offset, lcs.charAt(i));
			offset++;
			
			System.out.println("regexp: "+sb);
			pattern = Pattern.compile(sb.toString());
			matchesAll = matchesAll(pattern, similarStrings);
			System.out.println("matchesAll: "+ matchesAll);
			if(!matchesAll){
				if(specialCharAdded){
					sb.insert(offset-2, ".*?");
					offset+=3; // length of (.*) 
				} else {
					sb.insert(offset-1, ".*?");
					offset+=3; // length of (.*) 
				}
				System.out.println("replacing regexp: "+ sb);
				pattern = Pattern.compile(sb.toString());
				System.out.println("additional match check: " + matchesAll(pattern, similarStrings));
				System.out.println();
			}
		}
		
	}
	
	private static boolean matchesAll(Pattern pattern, List<String> similarStrings){
		for(String s: similarStrings){
			if(!pattern.matcher(s).matches()){
				System.out.println("match fails at: "+s);
				return false;
			}
		}
		
		return true;
	}

	/**
	 * На основе переданного LCS из сообщения делает шаблон, подменяя места,
	 * которые в LCS отсутствуют на {&}. Можно также использовать на шаблонах с
	 * именоваными плейсхолдерами
	 * 
	 * @param s
	 * @param lcs
	 * @return
	 */
	// TODO очевидно надо рефакторить этот код :'(
	public static String getTemplate(String s, String lcs) {
		StringBuilder sb = new StringBuilder();
		int i = 0, k = 0;
		String sPlaceholder, lcsPlaceholder;

		while (i < s.length() && k < lcs.length()) {
			if (s.charAt(i) == lcs.charAt(k)) {
				// Хитрая логика, чтобы когда в lcs и message есть шаблоны, всё
				// нормально работало. Просто посимвольное сравнение оказалось
				// не катит
				if (s.charAt(i) == '{') {
					if ((s.indexOf('}', i) != -1) && (lcs.indexOf('}', k) != -1)) {
						sPlaceholder = s.substring(i, s.indexOf('}', i) + 1);
						lcsPlaceholder = lcs.substring(k, lcs.indexOf('}', k) + 1);
						if ((isNamedPlaceholder(sPlaceholder) || sPlaceholder.equals("{&}"))
								&& (isNamedPlaceholder(lcsPlaceholder) || lcsPlaceholder.equals("{&}"))) {
							if (sPlaceholder.equals(lcsPlaceholder)) {
								sb.append(sPlaceholder);
								i += sPlaceholder.length();
								k += sPlaceholder.length();
							} else {
								sb.append(sPlaceholder);
								i += sPlaceholder.length();
							}
						} else {
							sb.append(lcs.charAt(k));
							i++;
							k++;
						}
					} else {
						sb.append(lcs.charAt(k));
						i++;
						k++;
					}
					// конец хитрой логики =)
				} else {
					sb.append(lcs.charAt(k));
					i++;
					k++;
				}
			} else {
				sb.append("{&}");
				while (s.charAt(i) != lcs.charAt(k)) {
					i++;
				}
			}
		}

		if (i < s.length()) {
			sb.append("{&}");
		}

		return sb.toString();
	}

	/**
	 * Проверяет, является ли переданная строка плейсхолдером, т.е. имеет ли вид
	 * {placeholder_name}
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNamedPlaceholder(String string) {
		return PLACEHOLDER_REGEX.matcher(string).matches();
	}
	private static final Pattern PLACEHOLDER_REGEX = Pattern.compile("^\\{\\w*\\}$");

}
