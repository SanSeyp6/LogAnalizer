package test;

import java.util.regex.Pattern;

import util.ParseMessage;

public class Test12 {

	public static void main(String[] args) {
		String s = " {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=264, delays=262/0/1.6/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 r9si11244175ioi.12 - gsmtp (in reply to RCPT TO command))";
	//	"  {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}"
	//	String regexp = " {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}";
	//	String regexp = " {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3{&}.1{&}.2{&}7{&}"; //] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}";
		String regexp = " {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3{&}.1{&}.2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}"; //si{&}.{&} - gsmtp (in reply to RCPT TO command){&}";
		Pattern pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(regexp); 
		//Pattern.compile("(.*?)/(.*?)").matcher(s);
		System.out.println("matches: "+ pattern.matcher(s).matches());
	}

}
