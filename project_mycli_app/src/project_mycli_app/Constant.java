package project_mycli_app;

public class Constant {
	/**
	 * constant variables
	 */			
	
	//master-binary-dir
	static final String MASTER_DIR = "master";
	static final String MASTER_BC_SIGNATURE_DIR = "master-bc-signature";
	static final String MASTER_BC_ASYMM_SIGNATURE_DIR = "master-bc-asymm-signature";
	
	//master-binary-file
	static final String MASTER_BINARY = MASTER_DIR+"/chain";
	static final String MASTER_BC_SIGNATURE_BINARY = MASTER_BC_SIGNATURE_DIR+"/chain";
	static final String MASTER_BC_ASYMM_SIGNATURE_BINARY = MASTER_BC_ASYMM_SIGNATURE_DIR+"/chain";
	
	//my-public-key-file
	static final String PUBLIC_KEY_FILE = "KeyPair/PublicKey";
	
	//my-private-key-file
	static final String PRIVATE_KEY_FILE = "KeyPair/PrivateKey";
}
