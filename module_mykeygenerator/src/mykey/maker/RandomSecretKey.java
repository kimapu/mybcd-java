package mykey.maker;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;

public class RandomSecretKey {

	/**
	 * symmetric algorithm
	 */
	private static final String SYMM_ALGORITHM = "AES";

	public static Key create() {
		try {
			KeyGenerator kg = KeyGenerator.getInstance(SYMM_ALGORITHM);
			kg.init(256, new SecureRandom());
			return kg.generateKey();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
