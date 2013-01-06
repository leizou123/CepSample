package com.lei.util;

/**
 * Stock Trade CEP with Esper
 * 
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/tutorial.html
 *
 * @author <a href=mailto:lei.lee.zou@gmail.com">stones333</a>
 * @version 1.0
 */

public class MathUtil {

	public static float getAvg(float[] array) {
		if (array==null || array.length==0) {
			return 0;
		}

	    float sum = 0;
	    for (int i = 0; i < array.length; i++) {
	        sum += array[i];
	    }
	    return sum / (float) array.length;
	}

	public static double getAvg(double[] array) {
		if (array==null || array.length==0) {
			return 0;
		}

	    double sum = 0;
	    for (int i = 0; i < array.length; i++) {
	        sum += array[i];
	    }
	    return sum / (double) array.length;
	}

}
