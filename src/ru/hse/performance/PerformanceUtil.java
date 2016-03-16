package ru.hse.performance;

public class PerformanceUtil {

	private static long beginTime;
	private static long stepTime;
	private static long endTime;
	private static boolean initialized=false;
	
	/**
	 * Инициализирет счётчики. Должен вызываться в начала программы.
	 */
	public static void initialize(){
		beginTime = System.currentTimeMillis();
		stepTime = beginTime;
		endTime = beginTime;
		initialized=true;
	}
	
	/**
	 * Печатает время, которое было затрачено на выполнение части кода от предыдущего вызова этого метода до текущего.
	 * Если счётчики не были инициализированы, то инициализирует их
	 * @param stepName
	 */
	public static void printTimeForStep(String stepName){
		if(!initialized){
			initialize();
		}
		endTime = System.currentTimeMillis();
		System.out.println(stepName+" finished in: " + ((float) (endTime - stepTime) / 1000) + " seconds");
		stepTime=endTime;
	}
	
	/**
	 * Выводит общее время выполнения, рассчитанное как разность между временем инициализации счётчиков и текущим временем.
	 * Если счётчики не были инициализированы, то инициализирует их  
	 */
	public static void printTotalTime(){
		if(!initialized){
			initialize();
		}
		endTime = System.currentTimeMillis();
		System.out.println("Total time: " + ((float) (endTime - beginTime) / 1000) + " seconds");
	}
}
