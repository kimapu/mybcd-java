package core.myblockchain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

import com.google.gson.GsonBuilder;

public class Blockchain {

	//data-structure
	private static LinkedList<Block> db = new LinkedList<>();

	private static Blockchain _instance;
	public static Blockchain getInstance( String chainFile )
	{
		if( _instance == null )
			_instance = new Blockchain( chainFile );
		return _instance;
	}
	
	private String ledgerFile;
	private String chainFile;
	private Blockchain( String chainFile ) {
		super();
		this.chainFile = chainFile+".bin";
		this.ledgerFile = "myledger.txt";
		
		System.out.println( this.chainFile );
		System.out.println( this.ledgerFile );
		
	}

	/**
	 * genesis()
	 * - very-first block
	 */
	public void genesis()
	{
		Block genesis = new Block("0");
		db.add(genesis);
		persist();
		distribute();
	}
	
	/**
	 * nextBlock()
	 */
	public void nextBlock( Block newBlock )
	{
		/* START */
		/* CREATE MERKLE ROOT */
		MerkleTree mt = MerkleTree.getInstance( newBlock.getTranx().getTranxLst() );
		mt.build();
		String merkleRoot = mt.getRoot();
		newBlock.getTranx().setMerkleRoot(merkleRoot);
		/* END */
		
		db = get();
		newBlock.getHeader().setIndex( db.getLast().getHeader().getIndex() + 1 );
		db.add(newBlock);
		persist();
	}
	
	
	/**
	 * get()
	 * - read the blockchain from the bin file
	 */
	public LinkedList<Block> get()
	{
		try 
		(
			FileInputStream fin = new FileInputStream( this.chainFile );
			ObjectInputStream in = new ObjectInputStream( fin );
		)
		{
			return (LinkedList<Block>)in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * persist()
	 * - write the blockchain to the bin file
	 */
	private void persist()
	{
		try 
		(
			FileOutputStream fout = new FileOutputStream( this.chainFile );
			ObjectOutputStream out = new ObjectOutputStream( fout );
		)
		{
			out.writeObject(db);
			System.out.println( ">> Master file updated!" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * distribute()
	 *  - show the ledger using gson prettyprint...
	 */
	public void distribute()
	{
		/**
		 * convert the chain to String using API
		 */
		String chain = new GsonBuilder().setPrettyPrinting().create().toJson(db);
		System.out.println( chain );
		try {
			Files.write(
					Paths.get( this.ledgerFile ), //targeted file
					chain.getBytes(), //content
					StandardOpenOption.CREATE //file mode
 					);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
