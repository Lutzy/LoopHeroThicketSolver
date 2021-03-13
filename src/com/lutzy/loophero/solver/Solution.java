package com.lutzy.loophero.solver;

public class Solution implements Comparable<Solution>{
	private Board board;
	private int score;
	
	public Solution(Board board, int score) {
		super();
		this.board = board;
		this.score = score;
	}
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Solution sol) {
		if (score < sol.getScore()) {
			return -1;
		} else if(score > sol.getScore()) {
			return 1;
		}
		return 0;
	}
}
