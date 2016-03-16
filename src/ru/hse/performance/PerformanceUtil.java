package ru.hse.performance;

public class PerformanceUtil {

	private static long beginTime;
	private static long stepTime;
	private static long endTime;
	private static boolean initialized=false;
	
	public static void initialize(){
		beginTime = System.currentTimeMillis();
		stepTime = beginTime;
		endTime = beginTime;
		initialized=true;
	}
	
	public static void printTimeForStep(String stepName){
		if(!initialized){
			initialize();
		}
		endTime = System.currentTimeMillis();
		System.out.println(stepName+" finished in: " + ((float) (endTime - stepTime) / 1000) + " seconds");
		stepTime=endTime;
	}
	
	public static void printTotalTime(){
		if(!initialized){
			initialize();
		}
		endTime = System.currentTimeMillis();
		System.out.println("Total time: " + ((float) (endTime - beginTime) / 1000) + " seconds");
	}
}
