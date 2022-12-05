package mykey.maker;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MyKeyPair {

	private static final String algorithm = "RSA";
	
	private KeyPairGenerator keygen;
	private KeyPair keyPair;
	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	
	public static PublicKey getPublicKey() {
		return publicKey;
	}
	
	public static PrivateKey getPrivateKey() {
		return privateKey;
	}

	public MyKeyPair() {
		try {
			keygen = KeyPairGenerator.getInstance(algorithm);
			keygen.initialize(1024);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * create
	 */
	public static void create()
	{
		MyKeyPair myKeyMaker = new MyKeyPair();
		//generate keyPair
		myKeyMaker.keyPair = myKeyMaker.keygen.generateKeyPair();
		//get public key
		publicKey = myKeyMaker.keyPair.getPublic();
		//get private key
		privateKey  = myKeyMaker.keyPair.getPrivate();
		
	}
	
	/**
	 * put the key in a specified file path
	 */
	public static void put( byte[] keyBytes, String path )
	{
		File f = new File(path );
		f.getParentFile().mkdirs();
		try {
			Files.write(Paths.get(path), keyBytes, StandardOpenOption.CREATE);
			System.out.println( "Keypair exported to '"+ path + "'" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
