package com.projectai.reversi;

public class GameStateHeuristics {

	public static int basicHeuristics(int[][] board, int player){
		int h = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (SpecificPositionChecker.isEdgePosition(i, j))
                {
                    h = board[i][j] == player ? h += 35 : h;
                }
                else
                {
                    if ((i == 2 || i == 6) && (j == 2 || j == 6))
                    {
                        h = board[i][j] == player ? h -= 25 : h;
                    }
                    if ((i == 0 || i == 1 || i == 5 || i == 6) && (j == 0 || j == 1 || j == 6 || j == 5))
                    {
                        boolean better = false;
                        if ((i == 0 && j == 5) && (board[0][4] == 1))
                        {
                            better = true;
                            h += 12;
                        }
                        if ((i == 5 && j == 0) && (board[4][0] == 1))
                        {
                            better = true;
                            h += 12;
                        }
                        if ((i == 7 && j == 5) && (board[7][4] == 1))
                        {
                            better = true;
                            h += 12;
                        }
                        if ((i == 5 && j == 7) && (board[4][7] == 1))
                        {
                            better = true;
                            h += 12;
                        }
                        if (i == 1 && j == 1)
                        {
                            if (board[1][0] == player)
                            {
                                better = true;
                                h += 15;
                            }
                            if (board[0][1] == player)
                            {
                                better = true;
                                h += 15;
                            }
                            if (board[1][0] == (player == 2 ? 1 : 2))
                            {
                                better = true;
                            }
                            if (board[0][1] == (player == 2 ? 1 : 2))
                            {
                                better = true;
                            }
                        }
                        if (i == 1 && j == 6)
                        {
                            if (board[0][6] == player)
                            {
                                better = true;
                                h += 15;
                            }
                            if (board[1][7] == player)
                            {
                                better = true;
                                h += 15;
                            }
                            if (board[0][6] == (player == 2 ? 1 : 2))
                            {
                                better = true;
                            }
                            if (board[1][7] == (player == 2 ? 1 : 2))
                            {
                                better = true;
                            }
                        }
                        if (i == 6 && j == 1)
                        {
                            if (board[6][0] == player)
                            {
                                better = true;
                                h += 15;
                            }
                            if (board[7][1] == player)
                            {
                                better = true;
                                h += 15;
                            }
                            if (board[6][0] == (player == 2 ? 1 : 2))
                            {
                                better = true;
                            }
                            if (board[7][1] == (player == 2 ? 1 : 2))
                            {
                                better = true;
                            }
                        }
                        if (i == 6 && j == 6)
                        {
                            if (board[6][7] == player)
                            {
                                better = true;
                                h += 15;
                            }
                            if (board[7][6] == player)
                            {
                                better = true;
                                h += 15;
                            }
                            if (board[6][7] == (player == 2 ? 1 : 2))
                            {
                                better = true;
                            }
                            if (board[7][6] == (player == 2 ? 1 : 2))
                            {
                                better = true;
                            }
                        }
                        h = board[i][j] == player && !better ? h -= 20 : h;
                    }
                    else
                    {
                        if (SpecificPositionChecker.isCentralContourPosition(i, j))
                        {
                            if (ReversiCut.canFlipInDirection(board, ReversiCut.LEFT, new Position(i, j), 2))
                            {
                                h = board[i][j] == player ? h += 3 : h;
                            }
                            if (ReversiCut.canFlipInDirection(board, ReversiCut.RIGHT, new Position(i, j), 2))
                            {
                                h = board[i][j] == player ? h += 3 : h;
                            }
                            if (ReversiCut.canFlipInDirection(board, ReversiCut.UP, new Position(i, j), 2))
                            {
                                h = board[i][j] == player ? h += 3 : h;
                            }
                            if (ReversiCut.canFlipInDirection(board, ReversiCut.DOWN, new Position(i, j), 2))
                            {
                                h = board[i][j] == player ? h += 3 : h;
                            }
                            h = board[i][j] == player ? h += 8 : h;
                        }
                        else
                        {
                            if (ReversiCut.allDiscsFlippedInDirection(board, ReversiCut.LEFT, i, j, player))
                            {
                                h = board[i][j] == player ? h += 1 : h;
                            }
                            if (ReversiCut.allDiscsFlippedInDirection(board, ReversiCut.RIGHT, i, j, player))
                            {
                                h = board[i][j] == player ? h += 1 : h;
                            }
                            if (ReversiCut.allDiscsFlippedInDirection(board, ReversiCut.UP, i, j, player))
                            {
                                h = board[i][j] == player ? h += 1 : h;
                            }
                            if (ReversiCut.allDiscsFlippedInDirection(board, ReversiCut.DOWN, i, j, player))
                            {
                                h = board[i][j] == player ? h += 1 : h;
                            }
                            h = board[i][j] == player ? h += 2 : h;
                        }
                    }
                }

            }
        }
        return h;
	}
	
	public static int endGameHeuristics(int[][] board, int player, int other)
    {
        int h = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j] == player)
                {
                    h += 1;
                }
                if (board[i][j] == other)
                {
                    h -= 1;
                }
            }
        }
        return h > 0 ? 1 : 0;
    }
	
}
