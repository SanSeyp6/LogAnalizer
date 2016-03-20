package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import util.ParseMessage;
import util.StringComparison;

/**
 * Продолжение идеи Test6 что строить рекурсивно дерево из lcSubstring-ов. Тогда шаблон будет иметь вид {&}lcSubstring{&}lcSubstring{&}...{&}
 *
 */
public class Test14 {
	public static void main(String[] args) {

		List<String> similarStrings = Arrays.asList(new String[]{
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.164.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 1si9232024lfi.103 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.164.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 jn4si9227864lbc.203 - gsmtp (in reply to RCPT TO command)",
// 	        	  {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}
//				  {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}				
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=264, delays=262/0/1.6/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 r9si11244175ioi.12 - gsmtp (in reply to RCPT TO command))",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[173.194.71.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 192si9255066lfh.71 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=624, delays=622/0.01/1.4/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 72si20143688iob.61 - gsmtp (in reply to RCPT TO command))",
				" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.163.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 dx2si8644490lbc.94 - gsmtp (in reply to RCPT TO command)",
				" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.203.27]:25, delay=1345, delays=1342/0/1.3/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.203.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 e97si20700057ioi.206 - gsmtp (in reply to RCPT TO command))"
		});
		// template =" {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}"
		 //            {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}
// TEST CASES
/*
		List<String> similarStrings = Arrays.asList(new String[]{
			" {sophos_internal_id}: from=<{email_address}>, size={size}, nrcpt=1 (queue active)",  
			" {sophos_internal_id}: from=<prvs={prvs}={email_address}>, size={size}, nrcpt=1 (queue active)"
		});
		// template = " {sophos_internal_id}: from=<{&}{email_address}>, size={size}, nrcpt=1 (queue active)"
*/
/*		
		List<String> similarStrings = Arrays.asList(new String[]{
			" connect from localhost.localdomain[127.0.0.1]",  
			" disconnect from localhost.localdomain[127.0.0.1]"
		});
		// template = " {&}connect from localhost.localdomain[127.0.0.1]"
*/
		
//		System.out.println("united template: "+ test3(similarStrings));
		
		String template = " {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}";
		Pattern pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(template);
		if(matchesAll(pattern, similarStrings)){
			System.out.println("matches all");
		} else {
			System.out.println("doesn't match");
		}

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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("current string list:");
		for(String s: similarStrings){
			System.out.println(s);
		}
		System.out.println("current lcSubsequence: \"" + lcSubsequence + "\"");
		String lcSubstring = getLongestCommonSubstringForStringGroupAndLCS(similarStrings, lcSubsequence);
		System.out.println("current lcSubstring: \"" + lcSubstring + "\"");
		int index;
		
		if(lcSubstring.isEmpty()){
			System.out.println("lcSubstring is empty");
			sb.append("{&}");
			sb.append(lcSubsequence);
			System.out.println("current template: \""+sb.toString()+"\"");
			return;
		}else if(lcSubstring.length()==1){
			System.out.println("lcSubstring is 1 symbol long!");
			for(int i=0; i<lcSubsequence.length(); i++){
				sb.append("{&}");
				sb.append(lcSubsequence.charAt(i));
			}
			System.out.println("current template: \""+sb.toString()+"\"");
			System.out.println();
			return;
		} else {
			index = lcSubsequence.indexOf(lcSubstring);
		}
		System.out.println("current index of lcSubstring in lcSubsequence: "+ index);
		String left = lcSubsequence.substring(0, index);
		System.out.println("current left: \""+ left + "\"");
		String right = lcSubsequence.substring(index+lcSubstring.length(), lcSubsequence.length());
		System.out.println("current right: \""+ right + "\"");

		System.out.println();
		if(!left.isEmpty()){
				buildTemplateRecursively(buildLeftSubstrings(similarStrings, lcSubstring), left, sb);	
		}
		sb.append("{&}");
		sb.append(lcSubstring);
		System.out.println("current template: \""+sb.toString()+"\"");
		if(!right.isEmpty()){
//			buildTemplateRecursively(similarStrings, right, sb);
			buildTemplateRecursively(buildRightSubstrings(similarStrings, lcSubstring), right, sb);	
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
	
	private static boolean matchesAll(Pattern pattern, List<String> similarStrings){
		for(String s: similarStrings){
			if(!pattern.matcher(s).matches()){
				System.out.println("match fails at: "+s);
				return false;
			}
		}
		
		return true;
	}


}
