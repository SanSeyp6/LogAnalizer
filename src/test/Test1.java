package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.StringComparison;

public class Test1 {

	public static void main(String[] args) {
		
		List<String> similarStrings=Arrays.asList(new String[]{
				" 234751855B85_65808F2B: to=<info@{hostname}>, relay=none, delay=144818, delays={delays_1}/{delays_2}/30/0, dsn=4.4.1, status=deferred (connect to {hostname}[{ip_address}]:25: Operation timed out)",
				" 234751855B85_65808F2B: to=<ship@{hostname}>, relay=none, delay=144818, delays={delays_1}/{delays_2}/30/0, dsn=4.4.1, status=deferred (connect to {hostname}[{ip_address}]:25: Operation timed out)",
				" 234751855B85_65808F2B: to=<admin@{hostname}>, relay=none, delay=144818, delays={delays_1}/{delays_2}/30/0, dsn=4.4.1, status=deferred (connect to {hostname}[{ip_address}]:25: Operation timed out)",
				" 2344B1855B72_65808F2B: to=<info@{hostname}>, relay=none, delay=144818, delays={delays_1}/{delays_2}/30/0, dsn=4.4.1, status=deferred (connect to {hostname}[{ip_address}]:25: Operation timed out)",
				" 2344B1855B72_65808F2B: to=<info@{hostname}>, relay=none, delay=144818, delays={delays_1}/{delays_2}/30/0, dsn=4.4.1, status=deferred (connect to {hostname}[{ip_address}]:25: Operation timed out)",
				" 2344B1855B72_65808F2B: to=<info@{hostname}>, relay=none, delay=144818, delays={delays_1}/{delays_2}/30/0, dsn=4.4.1, status=deferred (connect to {hostname}[{ip_address}]:25: Operation timed out)",
				" 26C771855B8A_65808F2B: to=<info@{hostname}>, relay=none, delay=144818, delays={delays_1}/{delays_2}/30/0, dsn=4.4.1, status=deferred (connect to {hostname}[{ip_address}]:25: Operation timed out)"		
		});
		
		// Нахождение LCS для списка похожих строк
		String lcs = similarStrings.get(0);
		for (String s : similarStrings) {
			lcs = StringComparison.computeLCS(lcs, s);
			System.out.println("lcs: "+lcs);
		}

		System.out.println();
		
		// На основе полученного LCS строим шаблоны сообщений по списку
		// похожих сообщений.
		List<String> templateList = new ArrayList<String>();
		for (String s : similarStrings) {
			templateList.add(getTemplate(s, lcs));
			System.out.println("template: "+getTemplate(s, lcs));
		}


	}

	private static String getTemplate(String s, String lcs){
		StringBuilder sb=new StringBuilder();
		int i=0, k=0;
		
		while(i<s.length() && k<lcs.length()){
			if(s.charAt(i)==lcs.charAt(k)){
				sb.append(lcs.charAt(k));
				i++;
				k++;
			} else {
				sb.append("{&}");
				while(s.charAt(i)!=lcs.charAt(k)){
						i++;
				}
			}
		}

		if(i<s.length()){
			sb.append("{&}");
		}

		return sb.toString();
	}

}
