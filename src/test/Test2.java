package test;

import java.util.List;

import util.ParseMessage;
import util.StringComparison;

public class Test2 {
	public static void main(String[] args) {
		//String template = " 569ADE{0}_70796_244{1}_1: {2} times: r=0.{3}s u=0.1{4}s s=0{5}";
		String template = " 569ADE{0}_70796_244{1}_1: {2} times: r={conn_times_r}s u=0.1{4}s s=0{5}";
		String message = " 569ADE01_70796_2442_1: conn times: r=0.36s u=0.13s s=0.01s";
		String templateAfterReplace = " 569ADE_70796_244_1:  times: r=s u=0.1s s=0";
	
		System.out.println(template);
		System.out.println(templateAfterReplace);
		System.out.println(StringComparison.computeDiff(template, templateAfterReplace));
		
		//Pattern.compile(regex).matcher(str).replaceFirst(repl) 
/*		
		System.out.println();
		
		Pattern pattern = Pattern.compile("\\{\\w*\\}");
		Matcher matcher = pattern.matcher(template);
		if(matcher.find()){
			System.out.println("matches");
			System.out.println(matcher.group());
			template = matcher.replaceFirst("");
		}
		System.out.println(template);
	 */
		

		List<String> placeholders =  ParseMessage.getPlaceholders(template);
		System.out.println(placeholders);
		
/*		
		Map<String,String> map = ParseMessage.parseMessageAgainstTemplate(message, template);
		System.out.println(map);
*/		
	}
}
