package application;

import entity.NumberType;

public class Util {
	public static String formatQueueNumber(int type, int number) {
    	String prefix = type == NumberType.Preferential ? "P" : "N";
    	return prefix + String.format("%04d", number);
	}
}
