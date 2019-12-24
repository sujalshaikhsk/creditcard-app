package com.strickers.creditcard.utils;

import java.math.RoundingMode;
import java.security.spec.KeySpec;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author Sujal
 *
 */
public class Utils {

	public Utils() {

	}

	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private static KeySpec ks;
	private static SecretKeyFactory skf;
	private static Cipher cipher;
	static byte[] arrayBytes;
	private static String myEncryptionKey;
	private static String myEncryptionScheme;
	static SecretKey key;

	static {
		try {
			myEncryptionKey = "ThisIsSpartaThisIsSparta";
			myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;

			arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);

			ks = new DESedeKeySpec(arrayBytes);
			skf = SecretKeyFactory.getInstance(myEncryptionScheme);
			cipher = Cipher.getInstance(myEncryptionScheme);
			key = skf.generateSecret(ks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		if (divisor == 0)
			divisor = 1L;
		Double num = (base * 100.0) / divisor;
		DecimalFormat df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(num);
	}

	public static Double calculatePercentage(Long base, Long divisor) {
		if (divisor == 0)
			divisor = 1L;
		Double output = (base * 100.0) / divisor;
		return (double) Math.round(output * 100) / 100;
	}

	public static String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public static String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

	public static Integer calculateAge(LocalDate dateOfBirth) {
		Integer years = LocalDate.now().getYear() - dateOfBirth.getYear();
		return years;
	}

	public static Integer generateOtp() {
		int randomPin = (int) (Math.random() * 9000) + 1000;
		String otp = String.valueOf(randomPin);
		Integer otpnum = Integer.parseInt(otp);
		return otpnum;
	}
	
	public static Integer generateCVV() {
		int randomPin = (int) (Math.random() * 900) + 100;
		String otp = String.valueOf(randomPin);
		Integer otpnum = Integer.parseInt(otp);
		return otpnum;
	}
}
