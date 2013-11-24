package com.projectai.reversi;

import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String gameStateStr = sc.nextLine();
		sc.close();
        GameState gameState = ReversiCut.createGameBoard(gameStateStr);
        int[][] board = gameState.board;
        int remainMoves = ReversiCut.getRemainingMoves(board);
        Position nextMove = remainMoves < 9 ? ReversiCut.getNextMove(gameState, GameStage.EndGameState, remainMoves) :
            ReversiCut.getNextMove(gameState, GameStage.StartGameStage, remainMoves);
        System.out.println((char)('A' + nextMove.getY()) + "" + (char)('1' + nextMove.getX()));    
	}

}
