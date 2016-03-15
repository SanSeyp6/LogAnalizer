package test;

import java.util.Arrays;
import java.util.List;

import util.Metrics;

/**
 * В данном тесте будем разбираться с работой метрик и делать композитную
 * метрику. Опыты показали, что на длинных строках у меня не работает
 * Левенштейн. :'(
 */

public class Test7 {
	public static void main(String[] args) {
		List<String> messages = Arrays.asList(new String[]{
				" EDA8D186090A_697C881B: to=<glyasha@aol.com>, relay=mailin-02.mx.aol.com[152.163.0.100]:25, delay=204830, delays=204823/0.01/7.3/0, dsn=4.0.0, status=deferred (host mailin-02.mx.aol.com[152.163.0.100] refused to talk to me: 554- (RTR:BL)  https://postmaster.aol.com/error-codes#554rtrbl 554  Connecting IP: 92.242.58.7)",
				" 9F4AF184B371_69AE8AEB: to=<eponomarev@hse.ru>, relay=mx1.virtmx-0004[92.242.58.174]:25, conn_use=2, delay=0.47, delays=0.17/0/0/0.29, dsn=2.6.0, status=sent (250 2.6.0 <569ae8ae.1RxL+IQtlIzv+YSo%icinga@icinga.hse.ru> [InternalId=19903305] Queued mail for delivery)",
				" 08D0E184B38C_69AE8AFB: to=<avladov@hse.ru>, relay=mx1.virtmx-0004[92.242.58.174]:25, conn_use=3, delay=0.51, delays=0.13/0/0/0.38, dsn=2.6.0, status=sent (250 2.6.0 <569ae8ae.1ZVQS6feU5is3j7e%icinga@icinga.hse.ru> [InternalId=19903308] Queued mail for delivery)",
				" 2B499184B394_69AE8AFB: to=<enagaitzeva@hse.ru>, relay=mx2.virtmx-0004[92.242.58.175]:25, conn_use=2, delay=0.49, delays=0.12/0/0/0.37, dsn=2.6.0, status=sent (250 2.6.0 <569ae8ae.wSoWIqggaS78lR0i%icinga@icinga.hse.ru> [InternalId=15984541] Queued mail for delivery)",
				" 78F97184B398_69AE8AFB: to=<aaovchinnikov@hse.ru>, relay=mx1.virtmx-0004[92.242.58.174]:25, conn_use=2, delay=0.42, delays=0.12/0/0/0.3, dsn=2.6.0, status=sent (250 2.6.0 <569ae8af.TX+yEczse6PEfzCR%icinga@icinga.hse.ru> [InternalId=19903309] Queued mail for delivery)",
				" 9EF8D184B3A3_69AE8AFB: to=<akuznecov@hse.ru>, relay=mx1.virtmx-0004[92.242.58.174]:25, conn_use=4, delay=0.47, delays=0.12/0/0/0.35, dsn=2.6.0, status=sent (250 2.6.0 <569ae8af./VjA2Fwq0XbrJYmK%icinga@icinga.hse.ru> [InternalId=19903310] Queued mail for delivery)",
				" B16B8184B3A5_69AE8AFB: to=<akarasev@hse.ru>, relay=mx2.virtmx-0004[92.242.58.175]:25, conn_use=3, delay=0.47, delays=0.11/0/0/0.36, dsn=2.6.0, status=sent (250 2.6.0 <569ae8af.C/z9PWqPCVLBvWI8%icinga@icinga.hse.ru> [InternalId=15984543] Queued mail for delivery)",
				" D3D6E184B3B5_69AE8AFB: to=<vbogatyrev@hse.ru>, relay=mx1.virtmx-0004[92.242.58.174]:25, conn_use=3, delay=0.45, delays=0.13/0/0/0.32, dsn=2.6.0, status=sent (250 2.6.0 <569ae8af.qM58aGrePEv7vg1s%icinga@icinga.hse.ru> [InternalId=19903311] Queued mail for delivery)",
				" 089FB184B3B6_69AE8B0B: to=<achelyadinov@hse.ru>, relay=mx1.virtmx-0004[92.242.58.174]:25, conn_use=5, delay=0.39, delays=0.12/0/0/0.27, dsn=2.6.0, status=sent (250 2.6.0 <569ae8af.I0h0Mbjx0bgQyGVC%icinga@icinga.hse.ru> [InternalId=19903312] Queued mail for delivery)",
				" 308FB184B383_69AE8B0B: to=<eponomarev@hse.ru>, relay=mx1.virtmx-0004[92.242.58.174]:25, conn_use=4, delay=0.43, delays=0.15/0/0/0.28, dsn=2.6.0, status=sent (250 2.6.0 <569ae8b0.oVAvuUqwdgtJcx0Y%icinga@icinga.hse.ru> [InternalId=19903313] Queued mail for delivery)"
		}); 

		for(String message: messages){
			System.out.println("message length: "+ message.length() + "; message: "+ message);
		}
		
		System.out.println("Levenshtein Distance");
		printCompuations(Metrics.LevenshteinDistance, messages);
		
		System.out.println("\n------------------------------------------");
		System.out.println("OverLap Coefficient");
		printCompuations(Metrics.OverlapCoefficient, messages);

	}
	
	private static void printCompuations(int metric, List<String> messages){
		String s1,s2;
		
		for(int j=0; j< messages.size(); j++){
			System.out.print("\t"+j);
		}
		System.out.println();
		
		for(int i=0; i< messages.size(); i++){
			s1=messages.get(i);
			System.out.print(i);
			for(int j=0; j< messages.size(); j++){
				s2 = messages.get(j);
				System.out.print("\t"+Metrics.computeSimilarityMetric(metric, s1, s2));
			}
			System.out.println();
		}
	}
}
