package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import util.ParseMessage;
import util.StringComparison;

/**
 * Идея была идти не рекурсивно, а по краю дерева с помощью while. Что-то херня выходит
 * Одним словом, дерево то получается то же самое. Возвращаемся к Test6
 * @author sansey
 *
 */
public class Test13 {

	public static void main(String[] args) {
		List<String> similarStrings = Arrays.asList(new String[]{
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.164.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 1si9232024lfi.103 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.164.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 jn4si9227864lbc.203 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=264, delays=262/0/1.6/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 r9si11244175ioi.12 - gsmtp (in reply to RCPT TO command))",
//				  {sophos_internal_id}: ostgmail-smtp-in.l.google.com[3.1.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 si. - gsmtp (in reply to RCPT TO command)
//				  {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[173.194.71.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 192si9255066lfh.71 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=624, delays=622/0.01/1.4/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 72si20143688iob.61 - gsmtp (in reply to RCPT TO command))",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.163.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 dx2si8644490lbc.94 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.203.27]:25, delay=1345, delays=1342/0/1.3/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.203.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 e97si20700057ioi.206 - gsmtp (in reply to RCPT TO command))"
		});

		test(similarStrings);
	}
	
	private static void test(List<String> similarStrings){
		String lcSubsequence = getLongestCommonSubsequenceForStringGroup(similarStrings);
		StringBuilder sb = new StringBuilder();
		String lcSubstring = getLongestCommonSubstringForStringGroupAndLCS(similarStrings, lcSubsequence);
		
		sb.append("{&}");
		sb.append(lcSubstring);
		sb.append("{&}");
		
		System.out.println("current template: \""+sb+"\"");
		Pattern pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		System.out.println("matches all?: "+ matchesAll(pattern, similarStrings));
		System.out.println();
//////////////////////////////////	
		List<String> leftSubstrings = buildLeftSubstrings(similarStrings, lcSubstring);
		System.out.println("current string list:");
		for(String s: leftSubstrings){
			System.out.println(s);
		}

		String left = getNextLeftString(leftSubstrings, lcSubstring);
		sb.insert(0, left);
		sb.insert(0, "{&}");
		System.out.println("current template: \""+sb+"\"");
		pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		System.out.println("matches all?: "+ matchesAll(pattern, similarStrings));
		System.out.println();
////////////////////////////
		lcSubsequence = getLongestCommonSubsequenceForStringGroup(leftSubstrings);
		lcSubstring = getLongestCommonSubstringForStringGroupAndLCS(leftSubstrings, lcSubsequence);
		leftSubstrings = buildLeftSubstrings(leftSubstrings, left);
		System.out.println("current string list:");
		for(String s: leftSubstrings){
			System.out.println(s);
		}

		left = getNextLeftString(leftSubstrings, lcSubstring);
		sb.insert(0, left);
		sb.insert(0, "{&}");
		System.out.println("current template: \""+sb+"\"");
		pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(sb.toString());
		System.out.println("matches all?: "+ matchesAll(pattern, similarStrings));
		System.out.println();
	}

	private static String getNextLeftString(List<String> leftSubstrings, String lcSubstring){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String lcSubsequence = getLongestCommonSubsequenceForStringGroup(leftSubstrings);
		System.out.println("current lcSubsequence: \"" + lcSubsequence + "\"");
		String lcSubstringHere = getLongestCommonSubstringForStringGroupAndLCS(leftSubstrings, lcSubsequence);
		System.out.println("current lcSubstring: \"" + lcSubstringHere + "\"");
//		int index;
		
		List<String> rightSubstrings = buildRightSubstrings(leftSubstrings, lcSubstringHere);
		for(String s: rightSubstrings){
			System.out.println(s);
		}
		System.out.println();
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			lcSubsequence = getLongestCommonSubsequenceForStringGroup(rightSubstrings);
			System.out.println("current lcSubsequence: \"" + lcSubsequence + "\"");
			lcSubstringHere = getLongestCommonSubstringForStringGroupAndLCS(rightSubstrings, lcSubsequence);
			System.out.println("current lcSubstring: \"" + lcSubstringHere + "\"");
//			int index;

			if(lcSubstringHere.length()==1){
				break;
			}
			
			rightSubstrings = buildRightSubstrings(leftSubstrings, lcSubstringHere);
			for(String s: rightSubstrings){
				System.out.println(s);
			}
			System.out.println();
		}
		return lcSubstringHere;
/*		
//////////////////
		lcSubsequence = getLongestCommonSubsequenceForStringGroup(rightSubstrings);
		System.out.println("current lcSubsequence: \"" + lcSubsequence + "\"");
		lcSubstringHere = getLongestCommonSubstringForStringGroupAndLCS(rightSubstrings, lcSubsequence);
		System.out.println("current lcSubstring: \"" + lcSubstringHere + "\"");
//		int index;
		
		rightSubstrings = buildRightSubstrings(leftSubstrings, lcSubstringHere);
		for(String s: rightSubstrings){
			System.out.println(s);
		}
		System.out.println();
//////////////////////////////////////
		lcSubsequence = getLongestCommonSubsequenceForStringGroup(rightSubstrings);
		System.out.println("current lcSubsequence: \"" + lcSubsequence + "\"");
		lcSubstringHere = getLongestCommonSubstringForStringGroupAndLCS(rightSubstrings, lcSubsequence);
		System.out.println("current lcSubstring: \"" + lcSubstringHere + "\"");
//		int index;
		
		rightSubstrings = buildRightSubstrings(leftSubstrings, lcSubstringHere);
		for(String s: rightSubstrings){
			System.out.println(s);
		}
		System.out.println();
//////////////////////////////////////
		lcSubsequence = getLongestCommonSubsequenceForStringGroup(rightSubstrings);
		System.out.println("current lcSubsequence: \"" + lcSubsequence + "\"");
		lcSubstringHere = getLongestCommonSubstringForStringGroupAndLCS(rightSubstrings, lcSubsequence);
		System.out.println("current lcSubstring: \"" + lcSubstringHere + "\"");
//		int index;
		
		rightSubstrings = buildRightSubstrings(leftSubstrings, lcSubstringHere);
		for(String s: rightSubstrings){
			System.out.println(s);
		}
		System.out.println();
//////////////////////////////////////

		
		
		
		return "";
*/		
	} 
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	private static String getLongestCommonSubsequenceForStringGroup(List<String> similarStrings){
		if(similarStrings == null || similarStrings.isEmpty()){
			throw new IllegalArgumentException("similarStrings shouldn't be null or empty");
		}
		
		String lcs = similarStrings.get(0);
		for(String s: similarStrings){
			lcs = StringComparison.computeLCSubsequence(s, lcs);
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

	/**
	 * Проверяет, соответствуют ли все строки в группе переданному регулярному выражению
	 * @param pattern
	 * @param similarStrings
	 * @return
	 */
	private static boolean matchesAll(Pattern pattern, List<String> similarStrings){
		for(String s: similarStrings){
			if(!pattern.matcher(s).matches()){
				System.out.println("match fails at: "+s);
				return false;
			}
		}
		
		return true;
	}

	private static List<String> buildLeftSubstrings(List<String> strings, String lcSubstring){
		List<String> leftSubstrings = new ArrayList<String>(strings.size());
		int index;
		for(String s: strings){
			index = s.indexOf(lcSubstring);
			leftSubstrings.add(s.substring(0, index));
		}
		return leftSubstrings;
	}
	
	private static List<String> buildRightSubstrings(List<String> strings, String lcSubstring){
		List<String> rightSubstrings = new ArrayList<String>(strings.size());
		int index;
		for(String s: strings){
			index = s.indexOf(lcSubstring);
			index +=lcSubstring.length();
			rightSubstrings.add(s.substring(index, s.length()));
		}
		return rightSubstrings;
	}

}
