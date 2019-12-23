package com.strickers.creditcard.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Random;

/**
 * 
 * @author Sujal
 *
 */
public class Utils {

	private Utils() {}

	
	/**
	 * getCurrentDate()
	 *
	 * @return
	 */
	public static LocalDate getCurrentDate() {
		return LocalDate.now();
	}

	private static final Random random = new Random();

	/**
	 * generateRandom()
	 *
	 * @param size
	 * @return
	 */
	public static int generateRandom(int size) {
		return random.nextInt(size);
	}
	
	public static String calculatePercentageInString(Long base, Long divisor) {
		if(divisor==0)
			divisor=1L;
		Double num = (base * 100.0) / divisor;
		DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(num);
	}
	
	public static Double calculatePercentage(Long base, Long divisor) {
		if(divisor==0)
			divisor=1L;
		Double output = (base * 100.0) / divisor;
		Double percentage = (double) Math.round(output * 100) / 100;
        return percentage;
	}
}
