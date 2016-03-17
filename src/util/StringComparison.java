package util;

public class StringComparison {

	/**
	 * Вычисляет наибольшую общую подстроку. Взято с
	 * https://ru.wikipedia.org/wiki/%D0%9D%D0%B0%D0%B8%D0%B1%D0%BE%D0%BB%D1%8C%
	 * D1%88%D0%B0%D1%8F_%D0%BE%D0%B1%D1%89%D0%B0%D1%8F_%D0%BF%D0%BE%D0%B4%D1%81
	 * %D1%82%D1%80%D0%BE%D0%BA%D0%B0
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

	public static String computeLCSunsequence(String s1, String s2) {
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
		String lcs = computeLCSunsequence(s1, s2);
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
