package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.StringComparison;
import util.Templates;

public class Test3 {
	public static void main(String[] args) {

		List<String> similarStrings = Arrays.asList(new String[]{
				 " warning: {ip_address}: hostname zhitishko-bytie.ru verification failed: hostname nor servname provided, or not known", 
				 " warning: {ip_address}: hostname poplar-poplar.ru verification failed: hostname nor servname provided, or not known"
		});


		// Нахождение LCS для списка похожих строк
		String lcs = similarStrings.get(0);
		for (String s : similarStrings) {
			lcs = StringComparison.computeLCS(lcs, s);
		}
		System.out.println("lcs:"+ lcs);
		
		// На основе полученного LCS строим шаблоны сообщений по списку
		// похожих сообщений.
		List<String> templateList = new ArrayList<String>();
		for (String s : similarStrings) {
			templateList.add(Templates.getTemplate(s, lcs));
		}
		System.out.println("templateList: "+ templateList);
		
		// объединяем шаблоны, получая один общий шаблон
		String unitedTemplate = Templates.uniteTemplates(templateList);

		System.out.println("unitedTemplate: " + unitedTemplate);
		
	}
	
	
}
