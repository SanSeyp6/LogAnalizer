package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import util.StringComparison;

/**
 * Продолжение идей Test6 и Test14. Тут пробуем построить дерево всех вариантов
 * и его обсчитать до первого подходящего шаблона.
 * 
 * @author pih
 *
 */
public class Test16 {

	public static final List<String> TEST_CASE_1 = Arrays.asList(new String[]{
			" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.164.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 1si9232024lfi.103 - gsmtp (in reply to RCPT TO command)",
			" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.164.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 jn4si9227864lbc.203 - gsmtp (in reply to RCPT TO command)",
//	       	  {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}
//			  {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}				
			" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=264, delays=262/0/1.6/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 r9si11244175ioi.12 - gsmtp (in reply to RCPT TO command))",
			" {sophos_internal_id}: host gmail-smtp-in.l.google.com[173.194.71.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 192si9255066lfh.71 - gsmtp (in reply to RCPT TO command)",
			" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.23.26]:25, delay=624, delays=622/0.01/1.4/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.23.26] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 72si20143688iob.61 - gsmtp (in reply to RCPT TO command))",
			" {sophos_internal_id}: host gmail-smtp-in.l.google.com[64.233.163.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 dx2si8644490lbc.94 - gsmtp (in reply to RCPT TO command)",
			" {sophos_internal_id}: to=<su.p.e.r.v.iso.r.20.1.5.invino@gmail.com>, relay=alt1.gmail-smtp-in.l.google.com[74.125.203.27]:25, delay=1345, delays=1342/0/1.3/0.94, dsn=4.2.1, status=deferred (host alt1.gmail-smtp-in.l.google.com[74.125.203.27] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 e97si20700057ioi.206 - gsmtp (in reply to RCPT TO command))"
	});
	// template =" {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}"
	//             {sophos_internal_id}: {&}o{&}s{&}t{&}gmail-smtp-in.l.google.com[{&}3.{&}1.{&}2{&}7{&}] said: 450-4.2.1 The user you are trying to contact is receiving mail at a rate that 450-4.2.1 prevents additional messages from being delivered. Please resend your 450-4.2.1 message at a later time. If the user is able to receive mail at that 450-4.2.1 time, your message will be delivered. For more information, please 450-4.2.1 visit 450 4.2.1  https://support.google.com/mail/answer/6592 {&}si{&}.{&} - gsmtp (in reply to RCPT TO command){&}


	public static final List<String> TEST_CASE_2 = Arrays.asList(new String[]{
		" {sophos_internal_id}: from=<{email_address}>, size={size}, nrcpt=1 (queue active)",  
		" {sophos_internal_id}: from=<prvs={prvs}={email_address}>, size={size}, nrcpt=1 (queue active)"
	});
	// template = " {sophos_internal_id}: from=<{&}{email_address}>, size={size}, nrcpt=1 (queue active)"

		
	public static final List<String> TEST_CASE_3 = Arrays.asList(new String[]{
		" connect from localhost.localdomain[127.0.0.1]",  
		" disconnect from localhost.localdomain[127.0.0.1]"
	});
	// template = " {&}connect from localhost.localdomain[127.0.0.1]"

	
	public static void main(String[] args) {
		List<String> similarStrings = TEST_CASE_1;
		CaseTreeNode root=new CaseTreeNode(similarStrings);
	}
	
	
	/**
	 * Класс описывает один узел в дереве возможных вариантов при построении общего шаблона
	 * @author pih
	 *
	 */
	private static class CaseTreeNode{
		private final List<String> similarStrings;
		private final String lcSequence;
		private final Set<String> lcStrings;
		private List<CaseTreeNode> children;
		private final List<List<Integer>> positions;
		
		public CaseTreeNode(List<String> similarStrings) {
			this.similarStrings = similarStrings;
			this.lcSequence = StringComparison.computeLCSubsequenceForStringGroup(similarStrings);
			this.lcStrings = StringComparison.computeAllLCSubstringsForStringGroup(similarStrings);
			this.positions = computePositions();
		}
		
		private List<List<Integer>> computePositions() {
			List<List<Integer>> returnList = new ArrayList<List<Integer>>();
			List<Integer> variant;
			
			for(String lcString: lcStrings){
				for(String s: similarStrings){
					
				}
			}
			return null;
		}

		public List<String> getSimilarStrings() {
			return similarStrings;
		}
		
		public String getLcSequence() {
			return lcSequence;
		}
		
		public Set<String> getLcStrings() {
			return lcStrings;
		}
		
		public List<CaseTreeNode> getChildren() {
			if(children == null){
				children = computeChildren();
			}
			return children;
		}

		private List<CaseTreeNode> computeChildren() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
