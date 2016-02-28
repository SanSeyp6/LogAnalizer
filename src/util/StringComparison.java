package util;



public class StringComparison {

	public static String computeLCS(String s1, String s2) {
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
		StringBuilder sb=new StringBuilder();
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
		String lcs = computeLCS(s1, s2);
		int i = 0;
		int j = 0;
		int M = s1.length();
		int N = lcs.length();

		StringBuilder sb = new StringBuilder();
		// так как N<M (или N==M в случае одинаковых строк), то идём по lcs
		if(N==M){
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
		while (i < M)  {
			sb.append(s1.charAt(i++));
		}

		return sb.toString();
	}

}
