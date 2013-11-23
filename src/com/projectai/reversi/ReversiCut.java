package com.projectai.reversi;

import java.util.ArrayList;
import java.util.List;

public class ReversiCut {
	
	public static int LEFT = 1;
    public static int RIGHT = 2;
    public static int UP = 3;
    public static int DOWN = 4;
    public static int LEFT_UP = 5;
    public static int LEFT_DOWN = 6;
    public static int RIGHT_UP = 7;
    public static int RIGHT_DOWN = 8;
    
    public static int[] directions = new int[] {
        LEFT, RIGHT, UP, DOWN,
        LEFT_UP, LEFT_DOWN,
        RIGHT_UP, RIGHT_DOWN
    };
    
    public static int getRemainingMoves(int[][] board)
    {
        int moves = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j] == 0)
                {
                    moves++;
                }
            }
        }
        return moves;
    }
    
    public static Position GetNextMove(GameState gameState, GameStage gameStage, int depth)
    {
        GameState[] child = getReachableGameStates(gameState, 2);
        Position nextMove = null;
        int max = Integer.MIN_VALUE;
        int h = 0;
        for (int i = 0; i < child.length; i++)
        {
            h = gameStage == GameStage.StartGameStage ? GameStateHeuristics.basicHeuristics(child[i].board, 2) :
                next(child[i],  depth - 1, 1);
            if (h > max)
            {
                max = h;
                nextMove = child[i].lastPosition;
            }                
        }
        return nextMove;
    }
    
    public static int next(GameState gameState, int depth, int player)
    {
        if (depth == 0)
        {                
            return GameStateHeuristics.endGameHeuristics(gameState.board, player, otherPlayer(player));                
        }
        GameState[] child = getReachableGameStates(gameState, player);
        int h = 0;
        for (int i = 0; i < child.length; i++)
        {
            h += next(child[i], depth - 1, otherPlayer(player));
        }
        return h;
    }
    
    public static int otherPlayer(int player)
    {
        return player == 2 ? 1 : 2;
    }
    
    public static GameState createGameBoard(String gameBoardStr)
    {
        GameState gameState = new GameState();
        List<Position> validPositions = new ArrayList<Position>(5);
        int[][] gameBoard = new int[8][];
        int xPos = 0;
        int yPos = 0;
        for (int i = 0; i < 8; i++)
        {
            gameBoard[i] = new int[8];
        }
        for (int i = 0; i < gameBoardStr.length(); i++)
        {
            if (xPos == 8)
            {
                break;
            }
            if (gameBoardStr.charAt(i) == '-')
            {
                gameBoard[xPos][yPos] = 1;
                yPos++;
                if (yPos == 8)
                {
                    xPos++;
                    yPos = 0;
                }
            }
            else
            {
                if (gameBoardStr.charAt(i) == '+')
                {
                    gameBoard[xPos][yPos] = 2;
                    yPos++;
                    if (yPos == 8)
                    {
                        xPos++;
                        yPos = 0;
                    }
                }
                else
                {
                    if (gameBoardStr.charAt(i) == '0' ||  gameBoardStr.charAt(i) == '*')
                    {
                        if (gameBoardStr.charAt(i) == '*')
                        {
                            Position pos = new Position(xPos, yPos);
                            validPositions.add(pos);
                        }
                        gameBoard[xPos][yPos] = 0;
                        yPos++;
                        if (yPos == 8)
                        {
                            xPos++;
                            yPos = 0;
                        }
                    }
                    if (gameBoardStr.charAt(i) == 'G')
                    {
                        gameBoard[xPos][yPos] = -1;
                        yPos++;
                        if (yPos == 8)
                        {
                            xPos++;
                            yPos = 0;
                        }
                    }

                }
            }

        }
        gameState.board = gameBoard;
        gameState.validPositions = validPositions;
        gameState.lastPosition = null;
        return gameState;
    }

    public static int[][] flipDiscs(int[][] gameBoard, Position pos, int flipColor)
    {
        boolean canFlip = false;
        int[][] newGameBoard = new int[8][];
        for (int i = 0; i < 8; i++)
        {
            newGameBoard[i] = new int[8];
        }
        for (int k = 0; k < 8; k++)
        {
            for (int j = 0; j < 8; j++)
            {
                newGameBoard[k][j] = gameBoard[k][j];
            }
        }
        for (int i = 0; i < 8; i++)
        {
            if (canFlipInDirection(gameBoard, directions[i], pos, flipColor))
            {
                if (!canFlip)
                {
                    newGameBoard[pos.getX()][pos.getY()] = flipColor;
                    canFlip = true;
                }                    
                flipInDirection(newGameBoard, directions[i], pos, flipColor);
            }
        }
        return newGameBoard;
    }
    
    private static void flipInDirection(int[][] gameBoard, int direction, Position pos, int flipColor)
    {
        if (direction == LEFT)
        {
            for (int j = pos.getY() - 1; j >= 0; j--)
            {
                if (gameBoard[pos.getX()][j] == flipColor)
                {
                    return;
                }
                gameBoard[pos.getX()][j] = flipColor;
            }
        }
        if (direction == RIGHT)
        {
            for (int j = pos.getY() + 1; j < 8; j++)
            {
                if (gameBoard[pos.getX()][j] == flipColor)
                {
                    return;
                }
                gameBoard[pos.getX()][j] = flipColor;
            }
        }
        if (direction == UP)
        {
            for (int i = pos.getX() - 1; i >= 0; i--)
            {
                if (gameBoard[i][pos.getY()] == flipColor)
                {
                    return;
                }
                gameBoard[i][pos.getY()] = flipColor;
            }
        }
        if (direction == DOWN)
        {
            for (int i = pos.getX() + 1; i < 8; i--)
            {
                if (gameBoard[i][pos.getY()] == flipColor)
                {
                    return;
                }
                gameBoard[i][pos.getY()] = flipColor;
            }
        }
        if (direction == LEFT_DOWN)
        {
            int x = pos.getX();
            int y = pos.getY();
            for (int i = 1; i < 8 && (x+i != 8 && y-i != -1); i++)
            {
                if (gameBoard[x + i][y - i] == flipColor)
                {
                    return;
                }
                gameBoard[x + i][y - i] = flipColor;
            }
        }
        if (direction == LEFT_UP)
        {
            int x = pos.getX();
            int y = pos.getY();
            for (int i = 1; i < 8 && (x-i != 0 && y-i != 0); i++)
            {
                if (gameBoard[x-i][y-i] == flipColor)
                {
                    return;
                }
                gameBoard[x - i][y - i] = flipColor;
            }
        }
        if (direction == RIGHT_UP)
        {
            int x = pos.getX();
            int y = pos.getY();
            for (int i = 1; i < 8 && (x-i != -1 && y+i != 8); i++)
            {
                if (gameBoard[x - i][y + i] == flipColor)
                {
                    return;
                }
                gameBoard[x - i][y + i] = flipColor;
            }
        }
        if (direction == RIGHT_DOWN)
        {
            int x = pos.getX();
            int y = pos.getY();
            for (int i = 1; i < 8 && (x+i != 8 && y+i != 8); i++)
            {
                if (gameBoard[x + i][y + i] == flipColor)
                {
                    return;
                }
                gameBoard[x + i][y + i] = flipColor;
            }
        }
    }
    
    public static boolean canFlipInDirection(int[][] gameBoard, int direction, Position pos, int flipColor)
    {
        if (direction == LEFT)
        {
            for (int j = pos.getY() - 1; j >= 0; j--)
            {
                if (gameBoard[pos.getX()][j] == 0)
                {
                    return false;
                }
                else if (gameBoard[pos.getX()][j] == flipColor)
                {
                    return (pos.getY() - j) != 1;
                }
            }
        }
        if (direction == RIGHT)
        {
            for (int j = pos.getY() + 1; j < 8; j++)
            {
                if (gameBoard[pos.getX()][j] == 0)
                {
                    return false;
                }
                else if (gameBoard[pos.getX()][j] == flipColor)
                {
                    return (j - pos.getY()) != 1;
                }
            }
        }
        if (direction == UP)
        {
            for (int i = pos.getX() - 1; i >= 0; i--)
            {
                if (gameBoard[i][pos.getY()] == 0)
                {
                    return false;
                }
                else if (gameBoard[i][pos.getY()] == flipColor)
                {
                    return (pos.getX() - i) != 1;
                }
            }
        }
        if (direction == DOWN)
        {
            for (int i = pos.getX() + 1; i < 8; i++)
            {
                if (gameBoard[i][pos.getY()] == 0)
                {
                    return false;
                }
                else if (gameBoard[i][pos.getY()] == flipColor)
                {
                    return (i - pos.getX()) != 1;
                }
            }
        }
        if (direction == RIGHT_DOWN)
        {
            int x = pos.getX();
            int y = pos.getY();
            for (int i = 1; i < 8 && (x+i != 8 && y+i != 8); i++)
            {
                if (gameBoard[x + i][y + i] == 0)
                {
                    return false;
                }
                else if (gameBoard[x + i][y + i] == flipColor)
                {
                    return true;
                }
            }
        }
        if (direction == LEFT_DOWN)
        {
            int x = pos.getX();
            int y = pos.getY();
            for (int i = 1; i < 8 && (x+i != 8 && y-i != -1); i++)
            {
                if (gameBoard[x + i][y - i] == 0)
                {
                    return false;
                }
                else if (gameBoard[x + i][y - i] == flipColor)
                {
                    return true;
                }
            }
        }
        if (direction == LEFT_UP)
        {
            int x = pos.getX();
            int y = pos.getY();
            for (int i = 1; i < 8 && (x-i != -1 && y-i != -1); i++)
            {
                if (gameBoard[x - i][y - i] == 0)
                {
                    return false;
                }
                else if (gameBoard[x - i][y - i] == flipColor)
                {
                    return true;
                }
            }
        }
        if (direction == RIGHT_UP)
        {
            int x = pos.getX();
            int y = pos.getY();
            for (int i = 1; i < 8 && (x-i != -1 && y+i != 8); i++)
            {
                if (gameBoard[x - i][y + i] == 0)
                {
                    return false;
                }
                else if (gameBoard[x - i][y + i] == flipColor)
                {
                    return true;
                }
            }
        }            
        return false;
    }
    
    public static GameState[] getReachableGameStates(GameState gameState, int player)
    {
        List<Position> validPositions = gameState.validPositions;
        GameState[] child = new GameState[validPositions.size()];
        GameState childGameState;
        int i = 0;
        for (Position pos : validPositions)
        {
            childGameState = new GameState();
            childGameState.board = flipDiscs(gameState.board, pos, player);
            childGameState.validPositions = getValidPositions(childGameState.board, player == 2 ? 1 : 2);
            childGameState.lastPosition = pos;
            child[i] = childGameState;
            i++;
        }
        return child; 
    }
    
    public static List<Position> getValidPositions(int[][] board, int player)
    {
        List<Position> validPos = new ArrayList<Position>(10);
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j] == 0)
                {
                    boolean isValid = false;
                    Position pos = new Position(i, j);
                    for (int k = 0; k < 8; k++)
                    {
                        if (canFlipInDirection(board, directions[k], pos, player))
                        {
                            isValid = true;
                            break;
                        }
                    }
                    if (isValid)
                    {
                        validPos.add(pos);
                    }
                }
            }
        }
        return validPos;
    }
    
    public static boolean allDiscsFlippedInDirection(int[][] board, int direction, int x, int y, int player)
    {
        if (direction == LEFT)
        {
            for (int i = y - 1; i >= 0; i--)
            {
                if (board[x][i] != player)
                {
                    return false;
                }
            }
            return true;
        }
        if (direction == RIGHT)
        {
            for (int i = y + 1; i < 8; i++)
            {
                if (board[x][i] != player)
                {
                    return false;
                }
            }
            return true;
        }
        if (direction == UP)
        {
            for (int i = x - 1; i >= 0; i--)
            {
                if (board[i][y] != player)
                {
                    return false;
                }
            }
            return true;
        }
        if (direction == DOWN)
        {
            for (int i = x + 1; i < 8; i++)
            {
                if (board[i][y] != player)
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
