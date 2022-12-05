package project_homework_myledger.exe1.solution;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.myblockchain.Block;
import core.myblockchain.Transaction;

public class App {
	

	public static void main(String[] args) {
		
		String dir = "src/project_homework_myledger/exe1";
		String myFile = "tranxList.txt";
		
		TransactionDA daTranx = new TransactionDA(dir, myFile);
		//show
		//daTranx.getAll().forEach( System.out :: println );
		
		List<String> tranxLst = daTranx.getAll();
		boolean noChain = !((new File(MyBlockchain.MASTER_BINARY).exists()) & (new File(MyBlockchain.LEDGER_FILE).exists()));
		if( noChain )
		{
			new File(MyBlockchain.MASTER_DIR+"/master").mkdir();
			//create genesis block
			MyBlockchain.genesis();
		}	
		else
		{
			//List<List<String>> subLst = Lists.partition(tranxLst, Transaction.SIZE);
			int partitionSize = Transaction.SIZE;
			List<List<String>> subLst = new ArrayList<>();
			for (int i = 0; i < tranxLst.size(); i += partitionSize) {
				subLst.add(tranxLst.subList(i, Math.min(i + partitionSize, tranxLst.size())));
			}
			
			for (List<String> lst : subLst) {
				Transaction bag = new Transaction();
				for (String line : lst) {
					bag.add(line);
				}
				//create nextBlock
				Block b1 = new Block( 
						MyBlockchain.get().getLast().getHeader().getCurrHash() 
						);
				b1.setTranx(bag);					
				MyBlockchain.nextBlock(b1);
				MyBlockchain.distribute();
			}
			
		}
		
	}

}
