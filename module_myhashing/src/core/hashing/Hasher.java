package core.hashing;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;



public class Hasher {

	/**
	 * 
	 * @param blockBytes
	 * @return
	 */
	static public String sha256( byte[] blockBytes ) 
	{
		try {
			byte[] hashBytes = MessageDigest.getInstance("SHA-256").digest( blockBytes );
			return Hex.encodeHexString(hashBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	static public String md5(String input)
	{
		return hash(input, "MD5");
	}
	
	/**
	 * 
	 * @param input
	 * @param salt
	 * @return
	 */
	static public String md5(String input, byte[] salt )
	{
		return hash(input, salt, "MD5");
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	static public String sha256(String input)
	{
		return hash(input, "SHA-256");
	}

	/**
	 * 
	 * @param input
	 * @param salt
	 * @return
	 */
	static public String sha256(String input, byte[] salt)
	{
		return hash(input, "SHA-256");
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	static public String sha384(String input)
	{
		return hash(input, "SHA-384");
	}
	
	/**
	 * 
	 * @param input
	 * @param salt
	 * @return
	 */
	static public String sha384(String input, byte[] salt)
	{
		return hash(input, "SHA-384");
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	static public String sha512(String input)
	{
		return hash(input, "SHA-512");
	}
	
	/**
	 * 
	 * @param input
	 * @param salt
	 * @return
	 */
	static public String sha512(String input, byte[] salt)
	{
		return hash(input, "SHA-512");
	}

	/**
	 * hash(String, String) : String
	 */
	private static String hash(String input, String algorithm) 
	{
		MessageDigest md;
		try 
		{
			//instantiate the MD object
			md = MessageDigest.getInstance(algorithm);
			//fetch input to MD
			md.update( input.getBytes() );
						
			//digest it
			byte[] hashBytes = md.digest();
			//convert to Hex format with Hex API from Apache common
			return String.valueOf(Hex.encodeHex(hashBytes));
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String hash(String input, byte[] salt, String algorithm) 
	{
		MessageDigest md;
		try 
		{
			//instantiate the MD object
			md = MessageDigest.getInstance(algorithm);
			//fetch input to MD
			md.update( input.getBytes() );
			md.update( salt );
			
			//digest it
			byte[] hashBytes = md.digest();
			//convert to Hex format with Hex API from Apache common
			return String.valueOf(Hex.encodeHex(hashBytes));
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
