package com.projectai.reversi;

public class SpecificPositionChecker {
	
	public static boolean isEdgePosition(int x, int y){
		if (x == 0 && (y == 1 || y == 6))
        {
            return true;
        }
        if (x == 1 && (y == 0 || y == 7))
        {
            return true;
        }
        if (x == 6 && (y == 0 || y == 7))
        {
            return true;
        }
        if (x == 7 && (y == 1 || y == 6))
        {
            return true;
        }
        return false;
	}
	
	public static boolean isNextToEdgePosition(int x, int y)
    {
        if (x == 2 && (y == 1 || y == 6))
        {
            return true;
        }
        if (x == 6 && (y == 0 || y == 7))
        {
            return true;
        }
        if (x == 6 && (y == 0 || y == 7))
        {
            return true;
        }
        if (x == 7 && (y == 1 || y == 6))
        {
            return true;
        }
        return false;

    }

    public static boolean isNeutralPosition(int x, int y)
    {
        return (x == 0 && x == y) || (x == 0 && y == 7)
               || (x == 7 && x == y) || (x == 7 && y == 0);
    }

    public static boolean isCentralContourPosition(int x, int y)
    {
        return ((x == 3 || x == 4) && (y == 0 || y == 7)) ||
               ((y == 3 || y == 4) && (x == 0 || x == 7));
    }

}
