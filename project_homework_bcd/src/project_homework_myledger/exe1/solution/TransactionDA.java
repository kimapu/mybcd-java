package project_homework_myledger.exe1.solution;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TransactionDA {


	private final String PATH_DIR;
	private final String PATH_FILE;
	private final String location;
	
	public TransactionDA(String pATH_DIR, String pATH_FILE) {
		super();
		PATH_DIR = pATH_DIR;
		PATH_FILE = pATH_FILE;
		this.location = String.join("/", PATH_DIR, PATH_FILE);
	}

	public List<String> getAll()
	{
		try {
			return Files.readAllLines( Paths.get(location) );
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
