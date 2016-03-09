package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import util.ParseMessage;
import util.StringComparison;
import util.Templates;

public class Test3 {
	public static void main(String[] args) {

		List<String> similarStrings = Arrays.asList(new String[]{
				"{sophos_internal_id}: from=<{email_address}>, size={size}, nrcpt=1 (queue active)",  
				"{sophos_internal_id}: from=<prvs={prvs}={email_address}>, size={size}, nrcpt=1 (queue active)"
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
		String unitedTemplate = uniteTemplates(templateList);

		System.out.println("unitedTemplate: " + unitedTemplate);
		
	}
	
	private static String uniteTemplates(List<String> templates){
		if (templates == null || templates.isEmpty()){
			throw new IllegalArgumentException("templates list is null or empty!");
		}
		
		String unitedTemplate=templates.get(0);
		int i,j;
		
		String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
		String.format(WITH_DELIMITER, "\\{(\\w*|&)\\}");
		
		List<String> untedTemplateList = new ArrayList<String>(Arrays.asList(templates.get(0).split(String.format(WITH_DELIMITER, "\\{(\\w*|&)\\}"))));
		List<String> currentTemplateList;
		
		for(String template: templates){
			currentTemplateList = new ArrayList<String>(Arrays.asList(template.split(String.format(WITH_DELIMITER, "\\{(\\w*|&)\\}"))));
			if (currentTemplateList.equals(untedTemplateList)){
				System.out.println("spiski odinakovye");
			} else {
				i=0;
				j=0;
				while(i<untedTemplateList.size() && j<currentTemplateList.size()){
					if(untedTemplateList.get(i).equals(currentTemplateList.get(j))){
						
					}
				}
				
				System.out.println(template);
				for(String s: currentTemplateList){
					System.out.println(s);
				}
			}
		}
		
		
		

		return unitedTemplate;
	}
}
