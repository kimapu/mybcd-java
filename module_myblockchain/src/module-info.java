module module_myblockchain {
	exports core.myblockchain;

	requires gson;
	requires java.sql;
	requires module_myhashing;
}