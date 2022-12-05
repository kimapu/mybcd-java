package module_mycrypto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class Asymmetric {
	private Cipher cipher;

	public Asymmetric() {
		this("RSA");
	}

	private Asymmetric(String algorithm) {
		try {
			cipher = Cipher.getInstance(algorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String data, PublicKey key) throws Exception {
		String cipherText = null;
		// init
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// encrypt
		byte[] cipherBytes = cipher.doFinal(data.getBytes());
		// convert to string
		cipherText = Base64.getEncoder().encodeToString(cipherBytes);
		return cipherText;
	}

	public String decrypt(String cipherText, PrivateKey key) throws Exception {
		// init
		cipher.init(Cipher.DECRYPT_MODE, key);
		// convert to byte[]
		byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
		// decrypt
		byte[] dataBytes = cipher.doFinal(cipherBytes);
		return new String(dataBytes);
	}
}
