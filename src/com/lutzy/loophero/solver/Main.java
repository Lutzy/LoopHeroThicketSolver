package com.lutzy.loophero.solver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Main {
	private static final int THREADS = 8;
	
	public static void main(String[] args) {
		List<Board> boards = new ArrayList<>();
		
		//Rivers have to start on the edge of a map, so I believe there should be only 8 start positions we need to actually check
		//everything else should be a mirror of one of these
		boards.add(new Board(0, 0));
		boards.add(new Board(0, 1));
		boards.add(new Board(0, 2));
		boards.add(new Board(1, 0));
		boards.add(new Board(2, 0));
		boards.add(new Board(3, 0));
		boards.add(new Board(4, 0));
		boards.add(new Board(5, 0));
		
		List<CompletableFuture<Solution>> solutions = new ArrayList<>();
		
		long startTime = System.currentTimeMillis();
		
		for(int i=0; i<THREADS; i++) {
			Solver solver = new Solver();
			CompletableFuture<Solution> solution = solver.solve(boards.get(i));
			solutions.add(solution);
		}
		
		Solution optimal = solutions.stream()
				.map(CompletableFuture::join)
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList()).get(0);
		
		System.out.println("The optimal solution to the thicket problem is: ");
		optimal.getBoard().printBoard();
		
		System.out.println("Ran for " + (System.currentTimeMillis() - startTime) + " ms");
	}
}
