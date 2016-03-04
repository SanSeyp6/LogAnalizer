package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ParseMessage;
import util.StringComparison;
import util.Templates;

public class Test3 {
	public static void main(String[] args) {

		List<String> similarStrings = Arrays.asList(new String[]{
				"{sophos_internal_id}: from=<{email_address}>, size={size}, nrcpt=1 (queue active)",  
				"{sophos_internal_id}: from=<prvs={prvs}={email_address}>, size={size, nrcpt=1 (queue active)"
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
	
	
	private static String uniteTemplates(List<String> templates) {
		String unitedTemplate = templates.get(0);
		String tmp;
		int placeholdersCount = 0;

		for(String template: templates){
			unitedTemplate = Templates.getTemplate(template, StringComparison.computeLCS(unitedTemplate, template));
		}
		
		System.out.println(unitedTemplate);
		
		// now replace placehoders with numbers in united template
		do {
			//TODO может стоит переделать на StringBuilder?
			tmp = unitedTemplate;
			unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" + placeholdersCount + "}");
			placeholdersCount++;
		} while (!tmp.equals(unitedTemplate));

		return unitedTemplate;
	}

}
