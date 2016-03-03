package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Templates {
	
	public static String getTemplate(String s, String lcs) {
		StringBuilder sb = new StringBuilder();
		int i = 0, k = 0;

		while (i < s.length() && k < lcs.length()) {
			if (s.charAt(i) == lcs.charAt(k)) {
				sb.append(lcs.charAt(k));
				i++;
				k++;
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

	public static String uniteTemplates(List<String> templates) {
		String unitedTemplate = templates.get(0);
		String tmp;
		int i = 0, j = 0;
		int placeholdersCount = 0;

		for (String s : templates) {
			if (!unitedTemplate.equals(s)) {
				i = 0;
				j = 0;
				StringBuilder sb = new StringBuilder();
				while (i < unitedTemplate.length() && j < s.length()) {
					if (unitedTemplate.charAt(i) == s.charAt(j)) {
						sb.append(unitedTemplate.charAt(i));
						i++;
						j++;
					} else {
						if (unitedTemplate.charAt(i) == '{' && unitedTemplate.charAt(i + 1) == '&' && unitedTemplate.charAt(i + 2) == '}') {
							sb.append("{&}");
							i += 3;
						} else if (s.charAt(j) == '{' && s.charAt(j + 1) == '&' && s.charAt(j + 2) == '}') {
							sb.append("{&}");
							j += 3;
						} else {
							System.err.println("Templates:" + templates);
							System.err.println("UnitedTemplate:" + unitedTemplate);
							System.err.println("CurrentTemplate:" + s);
							System.err.println("i:" + i);
							System.err.println("j:" + j);
							throw new IllegalStateException("Unexpected chars in templates");
						}
					}
				}
				// если что-то длинней другого, добавляем плейсхолдер
				if ((i < unitedTemplate.length() && j == s.length()) || (i == unitedTemplate.length() && j < s.length())) {
					sb.append("{&}");
				}
				unitedTemplate = sb.toString();
			}
		}

		// now replace placehoders with numbers in united template
		do {
			tmp = unitedTemplate;
			unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" + placeholdersCount + "}");
			placeholdersCount++;
		} while (!tmp.equals(unitedTemplate));

		return unitedTemplate;
	}

}
