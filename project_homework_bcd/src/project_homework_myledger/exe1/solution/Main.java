package project_homework_myledger.exe1.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<Integer> yourlist = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

		int partitionSize = 3;
		List<List<Integer>> partitions = new ArrayList<>();

		for (int i = 0; i < yourlist.size(); i += partitionSize) {
			partitions.add(yourlist.subList(i, Math.min(i + partitionSize, yourlist.size())));
		}
		
		partitions.forEach( System.out :: println );

	}

}
