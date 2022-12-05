package project_mycli_app;

import java.io.File;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import core.hashing.Hasher;
import core.hashing.Salt;
import core.myblockchain.Block;
import core.myblockchain.Blockchain;
import core.myblockchain.MerkleTree;
import core.myblockchain.Transaction;
import module_mycrypto.Asymmetric;
import module_mycrypto.Symmetric;
import module_mysignature.MySignature;
import mykey.access.KeyPairAccess;
import mykey.maker.MyKeyPair;
import mykey.maker.PredefinedCharsSecretKey;
import mykey.maker.RandomSecretKey;

public class Tester {
	
	/**
	 * tstBlockchainWithSignature
	 */
	public static void tstBlockchainWithAsymmSignature() throws Exception
	{
		Asymmetric asymm = new Asymmetric();
		MySignature sig = new MySignature();
		
		MyKeyPair.create();
		PublicKey publicKey = MyKeyPair.getPublicKey();
		PrivateKey privateKey = MyKeyPair.getPrivateKey();
		
		Blockchain bc = Blockchain.getInstance( Constant.MASTER_BC_ASYMM_SIGNATURE_BINARY );
		
		String tranx1 = "alice|bob|credit|100.0";
		String tranx2 = "alice|bob|debit|200.0";
		
		String sig1 = sig.sign(tranx1, privateKey);
		String sig2 = sig.sign(tranx2, privateKey);
		
		String encrypted_tranx1 = asymm.encrypt(tranx1, publicKey);
		String encrypted_tranx2 = asymm.encrypt(tranx1, publicKey);
		
		String signed_encrypted_tranx1 = String.join("|", encrypted_tranx1, sig1);
		String signed_encrypted_tranx2 = String.join("|", encrypted_tranx2, sig2);
		
		if ( !(new File(Constant.MASTER_BC_ASYMM_SIGNATURE_DIR).exists()) ) {
			//make master dir
			new File( Constant.MASTER_BC_ASYMM_SIGNATURE_DIR ).mkdir();
			//create genesis block
			bc.genesis();
		} else {
			Transaction tranxLst = new Transaction();
			tranxLst.add(signed_encrypted_tranx1);
			tranxLst.add(signed_encrypted_tranx2);
			
			String prevHash = bc.get().getLast().getHeader().getCurrHash();
			Block b1 = new Block( prevHash );
			b1.setTranx(tranxLst);
			
			bc.nextBlock(b1);
			bc.distribute();
		}
	}
	
	
	/**
	 * tstBlockchainWithSignature
	 */
	public static void tstBlockchainWithSignature() throws Exception
	{
		MySignature sig = new MySignature();
		
		MyKeyPair.create();
		PublicKey publicKey = MyKeyPair.getPublicKey();
		PrivateKey privateKey = MyKeyPair.getPrivateKey();
		
		Blockchain bc = Blockchain.getInstance( Constant.MASTER_BC_SIGNATURE_BINARY );
		
		String tranx1 = "alice|bob|credit|100.0";
		String tranx2 = "alice|bob|debit|200.0";
		
		String sig1 = sig.sign(tranx1, privateKey);
		String signed_tranx1 = String.join("|", tranx1, sig1);
		String sig2 = sig.sign(tranx2, privateKey);
		String signed_tranx2 = String.join("|", tranx2, sig2);
		
		if ( !(new File(Constant.MASTER_BC_SIGNATURE_DIR).exists()) ) {
			//make master dir
			new File( Constant.MASTER_BC_SIGNATURE_DIR ).mkdir();
			//create genesis block
			bc.genesis();
		} else {
			Transaction tranxLst = new Transaction();
			tranxLst.add(signed_tranx1);
			tranxLst.add(signed_tranx2);
			
			String prevHash = bc.get().getLast().getHeader().getCurrHash();
			Block b1 = new Block( prevHash );
			b1.setTranx(tranxLst);
			
			bc.nextBlock(b1);
			bc.distribute();
		}
	}
	
	/**
	 * tstMySignature()
	 */
	public static void tstMySignature() throws Exception
	{

		MyKeyPair.create();
		PublicKey publicKey = MyKeyPair.getPublicKey();
		PrivateKey privateKey = MyKeyPair.getPrivateKey();
		
		MySignature sig = new MySignature();
		
		String data = "simple digital signature demo";
		System.out.println("Data: " + data);  
		
		String signature = sig.sign(data, privateKey);
		System.out.println( "Signature: "+ signature );
		
		System.out.println("\n> Transfering...");
		TimeUnit.SECONDS.sleep(3);
		System.out.println("> Transfered.");
		
		//verify
		System.out.println();
		boolean isValid = sig.verify(data, signature, publicKey);
		System.out.println( (isValid)? ">> Correct!" : ">> Incorrect!" );
		
		System.out.println("> Done...");
		
	}
	
	/**
	 * tstASymm()
	 */
	public static void tstASymm() throws Exception
	{
		Asymmetric asymm = new Asymmetric();
		
		if( !new File("KeyPair").exists()  )
			System.out.println("> No keys found!");
		else {
			//encrypt
			PublicKey pubKey = KeyPairAccess.getPublicKey( Constant.PUBLIC_KEY_FILE );

			String msg = "hello";
			String encrypted = asymm.encrypt(msg, pubKey);
			System.out.println( String.join("|", msg, encrypted) );
//			
			TimeUnit.SECONDS.sleep(3);
			//decrypt
			PrivateKey prvKey = KeyPairAccess.getPrivateKey( Constant.PRIVATE_KEY_FILE );
			String decrypted = asymm.decrypt(encrypted, prvKey);
			System.out.println("\n> Decrypted: "+ decrypted);
		
		}
	}
	
	/**
	 * tstSymm()
	 */
	public static void tstSymm()
	{
		Symmetric symm = new Symmetric();
		
		Key myKey = PredefinedCharsSecretKey.create();
		
		//assume that a string data
		String data = "how do u do?";
		System.out.println( "Data: "+ data );
		
		try {
		
			String encrypted = symm.encrypt(data, myKey);
			System.out.println( "Encrypted: "+ encrypted );
			
			TimeUnit.SECONDS.sleep(3);

			String decrypted = symm.decrypt(encrypted, myKey);
			System.out.println("\nDecrypted: "+ decrypted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("> Process complete!");
		}
		
	}
	
	/**
	 * tstMerkleTree()
	 */
	public static void tstKeygen()
	{
		Key predefinedKey = PredefinedCharsSecretKey.create();
		Key randomKey = RandomSecretKey.create();
		
		System.out.println( "Predefined Key: "+ Base64.getEncoder().encodeToString( predefinedKey.getEncoded() ) );
		System.out.println( "Random Key: "+ Base64.getEncoder().encodeToString( randomKey.getEncoded() ) );
		
		System.out.println();
		
		MyKeyPair.create();
//		view
		System.out.println( "Public key: "+  
				Base64.getEncoder().encodeToString( MyKeyPair.getPublicKey().getEncoded() ));
		MyKeyPair.put( MyKeyPair.getPublicKey().getEncoded(), "KeyPair/PublicKey" );
		
		System.out.println();
		System.out.println( "Private key: "+  
				Base64.getEncoder().encodeToString( MyKeyPair.getPrivateKey().getEncoded() ));
		MyKeyPair.put( MyKeyPair.getPrivateKey().getEncoded(), "KeyPair/PrivateKey" );
		
		
	}
	
	/**
	 * tstMerkleTree()
	 */
	public static void tstMerkleTree()
	{
		String[] arr = {
			"alice|bob|credit|rm10",
			"alice|bob|debit|rm20",
			"alice|bob|credit|rm30",
			"alice|bob|debit|rm40"
		};
		
		MerkleTree mt = MerkleTree.getInstance( Arrays.asList(arr) );
		mt.build();
		
		String root = mt.getRoot();
		
		System.out.println( "Root: " + root );
		//Root: e3bedf5688986ce3a90a21b8b7716777b66478a936a2fa2e96db0561399eedcc

		
	}
	
	public static void tstBlockchain()
	{
		Blockchain bc = Blockchain.getInstance( Constant.MASTER_BINARY );
		
		String tranx1 = "alice|bob|credit|100.0";
		String tranx2 = "alice|bob|debit|200.0";
		
		if ( !(new File(Constant.MASTER_DIR).exists()) ) {
			//make master dir
			new File( Constant.MASTER_DIR ).mkdir();
			//create genesis block
			bc.genesis();
		} else {
			Transaction tranxLst = new Transaction();
			tranxLst.add(tranx1);
			tranxLst.add(tranx2);
			
			String prevHash = bc.get().getLast().getHeader().getCurrHash();
			Block b1 = new Block( prevHash );
			b1.setTranx(tranxLst);
			
			bc.nextBlock(b1);
			bc.distribute();
		}
	}
	
	public static void tstBlock()
	{
		//very-first block called genesis block
		Block genesis = new Block("0");
		System.out.println( genesis );
		
		String tranx1 = "alice|bob|rm10";
		String tranx2 = "helen|bob|rm20";
		
		Transaction tranxLst = new Transaction();
		tranxLst.add(tranx1);
		tranxLst.add(tranx2);
	
		//block-1
		Block b1 = new Block(genesis.getHeader().getCurrHash());
		b1.setTranx(tranxLst);
		System.out.println( b1 );
		
	}

	public static void tstHashingWithSalt()
	{
		String pwd1 = "helloworld";
		byte[] salt = Salt.generate();
		String hash1 = Hasher.md5(pwd1, salt);
		System.out.println( String.join("|", "MD5", hash1) );
		System.out.println( String.join("|", "SHA-256", Hasher.sha256(pwd1, salt)) );
		System.out.println( String.join("|", "SHA-384", Hasher.sha384(pwd1, salt)) );
		System.out.println( String.join("|", "SHA-512", Hasher.sha512(pwd1, salt)) );
		
	}
	
	public static void tstHashing()
	{
		String pwd1 = "helloworld";
		String hash1 = Hasher.md5(pwd1);
		System.out.println( String.join("|", "MD5", hash1) );
		System.out.println( String.join("|", "SHA-256", Hasher.sha256(pwd1)) );
		System.out.println( String.join("|", "SHA-384", Hasher.sha384(pwd1)) );
		System.out.println( String.join("|", "SHA-512", Hasher.sha512(pwd1)) );
		
	}

}
