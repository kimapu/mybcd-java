package mykey.maker;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

public class PredefinedCharsSecretKey {


	/**
	 * symmetric algorithm
	 */
	private static final String SYMM_ALGORITHM = "AES";
	
	/**
	 * predefined chars (secret)
	 */
	private static final String SECRET_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static Key create()
	{
		int keysize = 16;
		return new SecretKeySpec( Arrays.copyOf(SECRET_CHARS.getBytes(), keysize), SYMM_ALGORITHM );
	}
	
	
}
