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

/*
		List<String> similarStrings = Arrays.asList(new String[]{
			" {sophos_internal_id}: from=<{email_address}>, size={size}, nrcpt=1 (queue active)",  
			" {sophos_internal_id}: from=<prvs={prvs}={email_address}>, size={size}, nrcpt=1 (queue active)"
		});
*/
/*		
		List<String> similarStrings = Arrays.asList(new String[]{
			" connect from localhost.localdomain[127.0.0.1]",  
			" disconnect from localhost.localdomain[127.0.0.1]"
		});
*/
		
				

/*
		String lcSubsequence = StringComparison.computeLCSunsequence(similarStrings.get(0), similarStrings.get(1));
		
		String lcSubstring = lcSubsequence;
		lcSubstring = StringComparison.computeLCSubsting(similarStrings.get(0), lcSubstring);
		lcSubstring = StringComparison.computeLCSubsting(similarStrings.get(1), lcSubstring);
		
		System.out.println("lcSubstring: "+lcSubstring);
		System.out.println("lcSubsequence: "+lcSubsequence);
		
		int index=lcSubsequence.indexOf(lcSubstring);
		System.out.println(index);
		System.out.println("Строки lcSubseqence после отделения:");
		String before = lcSubsequence.substring(0, index);
		System.out.println("до lcSubstring: \""+before+"\"");
		String after = lcSubsequence.substring(index+lcSubstring.length(), lcSubsequence.length());
		System.out.println("после lcSubstring: \""+after+"\"");
		String template = before + "{&}" + lcSubstring + "{&}" + after;
		System.out.println(template);
		
		////////////////////////////////////////////
		System.out.println();
		lcSubsequence = before;
		lcSubstring = lcSubsequence;
		lcSubstring = StringComparison.computeLCSubsting(similarStrings.get(0), lcSubstring);
		lcSubstring = StringComparison.computeLCSubsting(similarStrings.get(1), lcSubstring);
		System.out.println("lcSubstring: "+lcSubstring);
		
		index=lcSubsequence.indexOf(lcSubstring);
		System.out.println(index);
		System.out.println("Строки lcSubseqence после отделения:");
		before = lcSubsequence.substring(0, index);
		System.out.println("до lcSubstring: \""+before+"\"");
		after = lcSubsequence.substring(index+lcSubstring.length(), lcSubsequence.length());
		System.out.println("после lcSubstring: \""+after+"\"");
		template = before + "{&}" + lcSubstring + "{&}" + after;
		System.out.println(template);
*/
		System.out.println("united template: "+ test4(similarStrings));
	}

	private static String test4(List<String> similarStrings){
		String lcSubsequence = getLongestCommonSubsequenceForStringGroup(similarStrings);
		StringBuilder sb = new StringBuilder();
		buildTemplateRecursively(similarStrings, lcSubsequence, sb);
		// Сюда вернётся шаблон без завершающего {&}
		
		// Теперь тестовая проверка, сможет ли разобрать без переднего и заднего плейсхолдера
		// чтобы их не пихать во все шаблоны
		Pattern pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		if(!matchesAll(pattern, similarStrings)){
			System.out.println("doesn't match without trailing {&}");
			sb.append("{&}");
		} 
		sb.delete(0, 3);
		System.out.println("removed pre-placeholder: \""+ sb.toString() +"\"");
		pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		if(!matchesAll(pattern, similarStrings)){
			System.out.println("doesn't match without leading {&}");
			sb.insert(0, "{&}");
		}
		
		System.out.println("final match");
		pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		if(matchesAll(pattern, similarStrings)){
			System.out.println("matches all");
		} else {
			System.out.println("doesn't match");
		}
		return sb.toString();
	}

	
	private static String test3(List<String> similarStrings){
		String lcSubsequence = getLongestCommonSubsequenceForStringGroup(similarStrings);
		StringBuilder sb = new StringBuilder();
		buildTemplateRecursively(similarStrings, lcSubsequence, sb);
		// Сюда вернётся шаблон без завершающего {&}
		
		// Теперь тестовая проверка, сможет ли разобрать без переднего и заднего плейсхолдера
		// чтобы их не пихать во все шаблоны
		Pattern pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		if(!matchesAll(pattern, similarStrings)){
			System.out.println("doesn't match without trailing {&}");
			sb.append("{&}");
		} 
		sb.delete(0, 3);
		System.out.println("removed pre-placeholder: \""+ sb.toString() +"\"");
		pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		if(!matchesAll(pattern, similarStrings)){
			System.out.println("doesn't match without leading {&}");
			sb.insert(0, "{&}");
		}
		
		System.out.println("final match");
		pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		if(matchesAll(pattern, similarStrings)){
			System.out.println("matches all");
		} else {
			System.out.println("doesn't match");
		}
		return sb.toString();
	}
	
	private static void buildTemplateRecursively(List<String> similarStrings, String lcSubsequence, StringBuilder sb){
		System.out.println("current lcSubsequence: \"" + lcSubsequence + "\"");
		String lcSubstring = getLongestCommonSubstringForStringGroupAndLCS(similarStrings, lcSubsequence);
		System.out.println("current lcSubstring: \"" + lcSubstring + "\"");
		int index = lcSubsequence.indexOf(lcSubstring);
		System.out.println("current index of lcSubstring in lcSubsequence: "+ index);
		String left = lcSubsequence.substring(0, index);
		System.out.println("current left: \""+ left + "\"");
		String right = lcSubsequence.substring(index+lcSubstring.length(), lcSubsequence.length());
		System.out.println("current right: \""+ right + "\"");
		
		System.out.println();
		if(!left.isEmpty()){
			buildTemplateRecursively(similarStrings, left, sb);	
		}
		sb.append("{&}");
		sb.append(lcSubstring);
		System.out.println("current template: \""+sb.toString()+"\"");
		if(!right.isEmpty()){
			buildTemplateRecursively(similarStrings, right, sb);
		}
		
	}
	
	private static String getLongestCommonSubsequenceForStringGroup(List<String> similarStrings){
		if(similarStrings == null || similarStrings.isEmpty()){
			throw new IllegalArgumentException("similarStrings shouldn't be null or empty");
		}
		
		String lcs = similarStrings.get(0);
		for(String s: similarStrings){
			lcs = StringComparison.computeLCSunsequence(s, lcs);
		}
		
		return lcs;
	}
	
	private static String getLongestCommonSubstringForStringGroupAndLCS(List<String> similarStrings, String lcSubsequence){
		String lcs = lcSubsequence;
		
		for(String s: similarStrings){
			lcs = StringComparison.computeLCSubsting(s, lcs);
		}
		
		return lcs;
	}
	
	private static String getStringAtLeft(List<String> similarStrings, String left, StringBuilder sb){
		
		return null;
	}
	
	/**
	 * Вычисляет шаблон для двух переданных строк, обнаруживая их различия.
	 * Использует динамическое программирование. Смотри также реализацию метода {@link StringComparison#computeLCSunsequence(String, String)} 
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String computeTemplate(String s1, String s2) {
		// number of lines of each file
		int M = s1.length();
		int N = s2.length();

		// opt[i][j] = length of LCS of x[i..M] and y[j..N]
		int[][] opt = new int[M + 1][N + 1];

		// compute length of LCS and all subproblems via dynamic programming
		for (int i = M - 1; i >= 0; i--) {
			for (int j = N - 1; j >= 0; j--) {
				if (s1.charAt(i) == s2.charAt(j)) {
					opt[i][j] = opt[i + 1][j + 1] + 1;
				} else {
					opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
				}
			}
		}

		// recover template itself 
		int i = 0, j = 0;
		StringBuilder sb=new StringBuilder();
		boolean addedPlaceholder = false; 
		while (i < M && j < N) {
			if (s1.charAt(i) == s2.charAt(j)) {
				sb.append(s1.charAt(i));
				i++;
				j++;
				addedPlaceholder = false;
			} else {
				if(!addedPlaceholder){
					sb.append("{&}");
					addedPlaceholder = true;
				}
				if (opt[i + 1][j] >= opt[i][j + 1]) {
					i++;
				} else {
					j++;
				}
			}
		}

		return sb.toString();
	}
	
		private static void test2(List<String> similarStrings, String lcs){
		StringBuilder sb = new StringBuilder();
		int index;
		
		for(index=0; index<lcs.length(); index++){
			sb.append(lcs.charAt(index));
			System.out.println(sb);
			if(!containedInAll(similarStrings, sb.toString())){
				System.out.println("Not contained");
				break;
			}
		}
		
		sb.deleteCharAt(sb.length()-1);
		index--;
		System.out.println("\"" + sb.toString() + "\"");
		
		//-----------------------------------------------
		sb = new StringBuilder();
		for(index++; index<lcs.length(); index++){
			sb.append(lcs.charAt(index));
			System.out.println(sb);
			if(!containedInAll(similarStrings, sb.toString())){
				System.out.println("Not contained");
				break;
			}
		}
		
		sb.deleteCharAt(sb.length()-1);
		index--;
		System.out.println("\"" + sb.toString() + "\"");

		//-----------------------------------------------
		sb = new StringBuilder();
		for(index++; index<lcs.length(); index++){
			sb.append(lcs.charAt(index));
			System.out.println(sb);
			if(!containedInAll(similarStrings, sb.toString())){
				System.out.println("Not contained");
				break;
			}
		}
		
		sb.deleteCharAt(sb.length()-1);
		index--;
		System.out.println("\"" + sb.toString() + "\"");
	}

	private static boolean containedInAll(List<String> similarStrings, String substring){
		for(String s: similarStrings){
			if(!s.contains(substring)){
				return false;
			}
		}
		
		return true;
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
