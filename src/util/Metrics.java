package util;

public class Metrics {

	public static final int LevenshteinDistance = 1 << 0;
	public static final int OverlapCoefficient = 1 << 1;

	/**
	 * 
	 * @param s1
	 * @param s2
	 * @param metric
	 * @param metricThreshold значение метрики, после которого строки начинают считаться похожими
	 * @return
	 */
	public static boolean checkMetric(String s1, String s2, int metric, int metricThreshold) {
		switch (metric) {
		case LevenshteinDistance:
			return computeLevenshteinDistance(s1, s2) < metricThreshold;

		case OverlapCoefficient:
			int coef = computeOverlapCoefficient(s1, s2);
			return  coef > metricThreshold;

		}
		return false;
	}

	private static int computeLevenshteinDistance(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		int[] D1;
		int[] D2 = new int[n + 1];

		for (int i = 0; i <= n; i++)
			D2[i] = i;

		for (int i = 1; i <= m; i++) {
			D1 = D2;
			D2 = new int[n + 1];
			for (int j = 0; j <= n; j++) {
				if (j == 0)
					D2[j] = i;
				else {
					int cost = (s1.charAt(i - 1) != s2.charAt(j - 1)) ? 1 : 0;
					if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
						D2[j] = D2[j - 1] + 1;
					else if (D1[j] < D1[j - 1] + cost)
						D2[j] = D1[j] + 1;
					else
						D2[j] = D1[j - 1] + cost;
				}
			}
		}
		return D2[n];
	}

	private static int computeOverlapCoefficient(String s1, String s2) {
		String lcs=StringComparison.computeLCS(s1, s2);
		int base = Math.min(s1.length(), s2.length());
		
		return base == 0 ? 0 : (int) lcs.length()*100/base;
	}

}
