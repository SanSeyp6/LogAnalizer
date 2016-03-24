package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StringComparison {

	/**
	 * Вычисляет наибольшую общую подстроку. Взято с
	 * https://ru.wikipedia.org/wiki/%D0%9D%D0%B0%D0%B8%D0%B1%D0%BE%D0%BB%D1%8C%
	 * D1%88%D0%B0%D1%8F_%D0%BE%D0%B1%D1%89%D0%B0%D1%8F_%D0%BF%D0%BE%D0%B4%D1%81
	 * %D1%82%D1%80%D0%BE%D0%BA%D0%B0 Наткнулся на то, что оптимальных решений
	 * может быть несколько. Это приводит к тому, что для дальших вычислений
	 * шаблона может быть возвращён не та подстрока Как возвращать все варианты
	 * я не придумал пока
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String computeLCSubsting(String a, String b) {
		if (a == null || b == null || a.length() == 0 || b.length() == 0) {
			return "";
		}

		if (a.equals(b)) {
			return a;
		}

		int[][] matrix = new int[a.length()][];

		int maxLength = 0;
		int maxI = 0;

		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = new int[b.length()];
			for (int j = 0; j < matrix[i].length; j++) {
				if (a.charAt(i) == b.charAt(j)) {
					if (i != 0 && j != 0) {
						matrix[i][j] = matrix[i - 1][j - 1] + 1;
					} else {
						matrix[i][j] = 1;
					}
					if (matrix[i][j] > maxLength) {
						maxLength = matrix[i][j];
						maxI = i;
					}
				}
			}
		}
		return a.substring(maxI - maxLength + 1, maxI + 1);
	}

	/**
	 * Модифицированный метод {@link #computeLCSubsting(String, String)},
	 * который находит все существующие общие подстроки наибольшей длины без
	 * повторов
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Set<String> computeAllLCSubstings(String a, String b) {
		if (a == null || b == null || a.length() == 0 || b.length() == 0) {
			return Collections.emptySet();
		}

		Set<String> returnList = new HashSet<String>();
		if (a.equals(b)) {
			returnList.add(a);
			return returnList;
		}

		int[][] matrix = new int[a.length()][];
		int maxLength = 0;
		List<Integer> maxIs = new ArrayList<Integer>(a.length());

		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = new int[b.length()];
			for (int j = 0; j < matrix[i].length; j++) {
				if (a.charAt(i) == b.charAt(j)) {
					if (i != 0 && j != 0) {
						matrix[i][j] = matrix[i - 1][j - 1] + 1;
					} else {
						matrix[i][j] = 1;
					}
					if (matrix[i][j] > maxLength) {
						maxLength = matrix[i][j];
						maxIs.clear();
						maxIs.add(Integer.valueOf(i));
					} else if (matrix[i][j] == maxLength) {
						maxIs.add(Integer.valueOf(i));
					}
				}
			}
		}

		for (Integer maxI : maxIs) {
			returnList.add(a.substring(maxI - maxLength + 1, maxI + 1));
		}

		return returnList;
	}

	/**
	 * Возвращает наибольшую общую подпоследовательность символов для группы
	 * строк. ВАЖНО! Таких подпоследовательностей может быть более, чем одна,
	 * метод возвращет одну из них, при этом не определено какую именно.
	 * 
	 * @param similarStrings
	 * @return
	 */
	public static String computeLCSubsequenceForStringGroup(List<String> similarStrings) {
		if (similarStrings == null || similarStrings.isEmpty()) {
			throw new IllegalArgumentException("similarStrings shouldn't be null or empty");
		}

		String lcs = similarStrings.get(0);
		for (String s : similarStrings) {
			lcs = StringComparison.computeLCSubsequence(s, lcs);
		}

		return lcs;
	}

	public static String computeLongestCommonSubstringForStringGroupAndLCS(List<String> similarStrings,
			String lcSubsequence) {
		String lcs = lcSubsequence;

		for (String s : similarStrings) {
			lcs = StringComparison.computeLCSubsting(s, lcs);
		}

		return lcs;
	}

	public static Set<String> computeAllLCSubstringsForStringGroup(List<String> similarStrings) {
		if (similarStrings == null || similarStrings.isEmpty()) {
			throw new IllegalArgumentException("Arguments should be null or empty");
		}
		if(similarStrings.size() < 2){
			throw new IllegalArgumentException("similaStrings list should contain at least 2 elements");
		}
		
		Set<String> lcStrings;
		Set<String> tmpSet = Collections.emptySet();
		Set<String> tmpSet2 = new HashSet<String>();
		Iterator<String> simStrIterator = similarStrings.iterator();
		Iterator<String> lcStrIterator;
		String s0 = simStrIterator.next();
		String s1 = simStrIterator.next();
		String simStr,s;
		
		lcStrings = computeAllLCSubstings(s0, s1);
//		System.out.println("lcStrings for \""+s0+"\" and \""+s1+"\": "+ lcStrings);
		if(lcStrings.isEmpty()){
			return Collections.emptySet();		
		}

		for(; simStrIterator.hasNext(); ){
			simStr=simStrIterator.next();
			for(lcStrIterator=lcStrings.iterator(); lcStrIterator.hasNext(); ){
				s = lcStrIterator.next();
				tmpSet = computeAllLCSubstings(simStr, s); 
//				System.out.println("after " +simStr+ " and "+s+": " + tmpSet);
				if(tmpSet.isEmpty()){
					lcStrIterator.remove();		
				} else {
					tmpSet2.addAll(tmpSet);
				}
			}
			lcStrings = tmpSet2;
			tmpSet2 = new HashSet<String>();
			if(lcStrings.isEmpty()){
				return Collections.emptySet();
			}
//			lcStrings = tmpSet;
		}
		//retainLongestStringsInSet(lcStrings);
		//retainShortestStringsInSet(lcStrings);
//		System.out.println(lcStrings);
		return lcStrings;
	}
	
	private static void retainLongestStringsInSet(Set<String> stringSet) {
//		System.out.println("---------------retainShortestStringsInSet---------------");
//		System.out.println(stringSet);
		if(stringSet.isEmpty()){
			return;
		}
		Iterator<String> iterator = stringSet.iterator(); 
		String str=iterator.next();
//		System.out.println("str: "+str);
		int maxLength =str.length();
		
		while(iterator.hasNext()){
			str=iterator.next();
			if (str.length() > maxLength) {
				maxLength = str.length();
			}
		}

//		System.out.println(minLength);
		
		iterator = stringSet.iterator();
		while(iterator.hasNext()){
			str=iterator.next();
			if (str.length() < maxLength) {
				iterator.remove();
			}
		}
//		System.out.println("--end-of-------retainShortestStringsInSet---------------");
	}

	private static void retainShortestStringsInSet(Set<String> stringSet) {
//		System.out.println("---------------retainShortestStringsInSet---------------");
//		System.out.println(stringSet);
		if(stringSet.isEmpty()){
			return;
		}
		Iterator<String> iterator = stringSet.iterator(); 
		String str=iterator.next();
//		System.out.println("str: "+str);
		int minLength =str.length();
		
		while(iterator.hasNext()){
			str=iterator.next();
			if (str.length() < minLength) {
				minLength = str.length();
			}
		}

//		System.out.println(minLength);
		
		iterator = stringSet.iterator();
		while(iterator.hasNext()){
			str=iterator.next();
			if (str.length() > minLength) {
				iterator.remove();
			}
		}
//		System.out.println("--end-of-------retainShortestStringsInSet---------------");
	}

	
	public static String computeLCSubsequence(String s1, String s2) {
		// number of lines of each file
		int M = s1.length();
		int N = s2.length();

		// opt[i][j] = length of LCS of x[i..M] and y[j..N]
		int[][] opt = new int[M + 1][N + 1];

		// compute length of LCS and all subproblems via dynamic programming
		for (int i = M - 1; i >= 0; i--) {
			for (int j = N - 1; j >= 0; j--) {
				if (s1.charAt(i) == s2.charAt(j)) {
					opt[i][j] = opt[i + 1][j + 1] + 1;
				} else {
					opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
				}
			}
		}

		// recover LCS itself
		int i = 0, j = 0;
		StringBuilder sb = new StringBuilder();
		while (i < M && j < N) {
			if (s1.charAt(i) == s2.charAt(j)) {
				sb.append(s1.charAt(i));
				i++;
				j++;
			} else if (opt[i + 1][j] >= opt[i][j + 1]) {
				i++;
			} else {
				j++;
			}
		}

		return sb.toString();
	}

	/**
	 * Функция возвращает строку, полученную в результате удаления из строки s1
	 * общей подпоследовательности символов строк s1 и s2, то есть
	 * result=diff(s1,lcs(s1,s2))
	 * 
	 * @param s1
	 *            - строка, из которой производится вычитание
	 * @param s2
	 * @return
	 */
	public static String computeDiff(String s1, String s2) {
		String lcs = computeLCSubsequence(s1, s2);
		int i = 0;
		int j = 0;
		int M = s1.length();
		int N = lcs.length();

		StringBuilder sb = new StringBuilder();
		// так как N<M (или N==M в случае одинаковых строк), то идём по lcs
		if (N == M) {
			return "";
		}
		while (j < N) {
			if (s1.charAt(i) == lcs.charAt(j)) {
				i++;
				j++;
			} else {
				sb.append(s1.charAt(i++));
			}
		}

		// Добавляем всё, что осталось в строке s1, когда lcs кончился
		while (i < M) {
			sb.append(s1.charAt(i++));
		}

		return sb.toString();
	}

}
