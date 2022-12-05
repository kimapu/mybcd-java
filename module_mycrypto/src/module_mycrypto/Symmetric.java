package module_mycrypto;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;

public class Symmetric {
	private Cipher cipher;

	public Symmetric() {
		this("AES");
	}

	private Symmetric(String algorithm) {
		try {
			cipher = Cipher.getInstance(algorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String data, Key key) throws Exception {
		String cipherText = null;
		// init
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// encrypt
		byte[] cipherBytes = cipher.doFinal(data.getBytes());
		// convert to string
		cipherText = Base64.getEncoder().encodeToString(cipherBytes);
		return cipherText;
	}

	public String decrypt(String cipherText, Key key) throws Exception {
		// init
		cipher.init(Cipher.DECRYPT_MODE, key);
		// convert to byte[]
		byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
		// decrypt
		byte[] dataBytes = cipher.doFinal(cipherBytes);
		return new String(dataBytes);
	}
}
