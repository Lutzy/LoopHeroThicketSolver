package com.lutzy.loophero.solver;

import java.util.Arrays;

public class Board {
	public static final int RIVER = 1;
	public static final int THICKET = 2;
	public static final int NUM_ROWS = 12;
	public static final int NUM_COLS = 5;

	private int[][] board;

	private int lastRowCursor;
	private int lastColCursor;
	
	public Board () {
		board = new int[NUM_ROWS][NUM_COLS];
	}

	public Board(int[][] board) {
		this.board = board;
	}
	
	public Board(int lastRowCursor, int lastColCursor) {
		board = new int[NUM_ROWS][NUM_COLS];
		this.lastRowCursor = lastRowCursor;
		this.lastColCursor = lastColCursor;
	}

	public int getLastRowCursor() {
		return lastRowCursor;
	}

	public void setLastRowCursor(int lastRowCursor) {
		this.lastRowCursor = lastRowCursor;
	}

	public int getLastColCursor() {
		return lastColCursor;
	}

	public void setLastColCursor(int lastColCursor) {
		this.lastColCursor = lastColCursor;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int [][] board) {
		this.board = board;
	}

	public void printBoard() {
		for (int i = 0; i < NUM_ROWS ; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("Score: " + calculateScore());
	}

	public int calculateScore() {
		int score = 0;
		for (int i = 0; i < NUM_ROWS ; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				if (board[i][j] == THICKET) {
					int currentNodeScore;
					int sideCount = 0;

					// left
					if (j-1 >= 0 && board[i][j-1] == RIVER) {
						sideCount++;
					}
					// right
					if (j+1 < NUM_COLS && board[i][j+1] == RIVER) {
						sideCount++;
					}
					// up
					if (i-1 >= 0 && board[i-1][j] == RIVER) {
						sideCount++;
					}
					// down
					if (i+1 < NUM_ROWS && board[i+1][j] == RIVER) {
						sideCount++;
					}
					switch (sideCount) {
					case 1:
						currentNodeScore = 4;
						break;
					case 2:
						currentNodeScore = 8;
						break;
					case 3:
						currentNodeScore = 12;
						break;
					case 4:
						currentNodeScore = 16;
						break;
					default:
						currentNodeScore = 2;
					}
					score += currentNodeScore;
				}
			}
		}
		return score;
	}

	public void addThicket(int row, int column) {
		board[row][column] = THICKET;
	}

	public void addRiver(int row, int column) {
		board[row][column] = RIVER;
	}

	public void fillThickets() {
		for (int i = 0; i < NUM_ROWS ; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				if (board[i][j] == 0) {
					board[i][j] = THICKET;
				}
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}
}

