package com.projectai.reversi;

import java.util.List;

public class GameState {
	
	public int[][] board;
	public List<Position> validPositions;
	public Position lastPosition;
	public int[][] getBoard() {
		return board;
	}
	public void setBoard(int[][] board) {
		this.board = board;
	}
	public List<Position> getValidPositions() {
		return validPositions;
	}
	public void setValidPositions(List<Position> validPositions) {
		this.validPositions = validPositions;
	}
	public Position getLastPosition() {
		return lastPosition;
	}
	public void setLastPosition(Position lastPosition) {
		this.lastPosition = lastPosition;
	}
	
	
	

}
