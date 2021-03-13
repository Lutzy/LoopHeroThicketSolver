package com.lutzy.loophero.solver;

import static com.lutzy.loophero.solver.Board.NUM_COLS;
import static com.lutzy.loophero.solver.Board.NUM_ROWS;
import static com.lutzy.loophero.solver.Board.RIVER;

import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Solver {

	public CompletableFuture<Solution> solve(Board board) {
		return CompletableFuture.supplyAsync(new Supplier<Solution>() {

			@Override
			public Solution get() {
				Stack<Board> boards = new Stack<>();
				
				int highestScoreFound = -1;
				Board bestBoard = new Board();

				board.fillThickets();
				boards.push(board);

				while(!boards.isEmpty()) {
					Board currentBoard = boards.pop();
					
					int currentScore = currentBoard.calculateScore();

					if(currentScore > highestScoreFound) {
						bestBoard = new Board(deepCopy(currentBoard.getBoard()));
						highestScoreFound = currentScore;
						bestBoard.printBoard();
					}

					//add left
					if (currentBoard.getLastColCursor()-1 >= 0 && currentBoard.getBoard()[currentBoard.getLastRowCursor()][currentBoard.getLastColCursor()-1] != RIVER) {
						Board left = new Board(deepCopy(currentBoard.getBoard()));
						left.addRiver(currentBoard.getLastRowCursor(), currentBoard.getLastColCursor()-1);
						left.setLastColCursor(currentBoard.getLastColCursor()-1);
						left.setLastRowCursor(currentBoard.getLastRowCursor());
						boards.push(left);
					}
					//add right
					if (currentBoard.getLastColCursor()+1 < NUM_COLS && currentBoard.getBoard()[currentBoard.getLastRowCursor()][currentBoard.getLastColCursor()+1] != RIVER) {
						Board right = new Board(deepCopy(currentBoard.getBoard()));
						right.addRiver(currentBoard.getLastRowCursor(), currentBoard.getLastColCursor()+1);
						right.setLastColCursor(currentBoard.getLastColCursor()+1);
						right.setLastRowCursor(currentBoard.getLastRowCursor());
						boards.push(right);
					}
					//add up
					if (currentBoard.getLastRowCursor()-1 >= 0 && currentBoard.getBoard()[currentBoard.getLastRowCursor()-1][currentBoard.getLastColCursor()] != RIVER) {
						Board up = new Board(deepCopy(currentBoard.getBoard()));
						up.addRiver(currentBoard.getLastRowCursor()-1, currentBoard.getLastColCursor());
						up.setLastColCursor(currentBoard.getLastColCursor());
						up.setLastRowCursor(currentBoard.getLastRowCursor()-1);
						boards.push(up);
					}
					//add down
					if (currentBoard.getLastRowCursor()+1 < NUM_ROWS && currentBoard.getBoard()[currentBoard.getLastRowCursor()+1][currentBoard.getLastColCursor()] != RIVER) {
						Board down = new Board(deepCopy(currentBoard.getBoard()));
						down.addRiver(currentBoard.getLastRowCursor()+1, currentBoard.getLastColCursor());
						down.setLastColCursor(currentBoard.getLastColCursor());
						down.setLastRowCursor(currentBoard.getLastRowCursor()+1);
						boards.push(down);
					}
				}
				return new Solution(bestBoard, highestScoreFound);
			}
		});
	}

	public static int[][] deepCopy(int[][] matrix) {
		return Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
	}
}
