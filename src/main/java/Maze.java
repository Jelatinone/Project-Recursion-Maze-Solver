//(c) A+ Computer Science
//www.apluscompsci.com
//Name - Cody Washington

//Libraries

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * {@code Maze} class represents a two-dimensional square maze made up of tiles represented either by a one, or a zero;
 * where a one represents a path tile and a zero a non-path tile. Each maze is initialized with a size and a line of integers
 * on a new thread and is checked for a valid path between the entrance and exit.  If a valid path is found, the shortest path
 * is recorded along with its length.
 * @author Cody Washington
 */
public class Maze extends Thread
{
    private final int[][] maze;
    private ArrayList<Integer[][]> paths;
    public Boolean hasEntrance = false, hasExit = false, hasPath = false, isValid;
    public Integer ShortestPath = -1;
    /** Default Constructor. */
    public Maze() {maze = new int[0][0];}

    /** Constructor. 
     * @param size The size of the maze
     * @param line A line of integers representing the maze
     */
    public Maze(int size, String line)
    {
        if(line.length() >= (Math.pow(size,2) * 2) - 1)
        {
            maze = new int[size][size];
            for(int i = 0, j = 0; i < size; i++, j += size * 2)
            {maze[i] = Arrays.stream((line.substring(j , j + (size * 2)-1).trim().split("\\s+"))).mapToInt(Integer::parseInt).toArray();}
            for(int i = 0; i < size; i++)
            {if(maze[i][maze.length-1] == 1 && getNumberValidDirections(i,maze.length-1) != 0) {hasExit = true;}}
            if(Objects.equals(maze[0][0],1) && getNumberValidDirections(0,0) != 0)
            {hasEntrance = true;}
            if(this.hasEntrance && this.hasExit)
            {this.isValid = true; this.start();}
        }
        else {maze = new int[size][size];}
    }

    public void run()
    {
        if(this.isAlive() && this.isValid) {int[] Results = checkForExitPath(0, 0); ShortestPath = Results[1]; hasPath = convertToBoolean(Results[0]);}
    }

    /**
     * Check is the maze(which contains an exit and entrance) contains a valid path
     * between the exit and entrance without using diagonals.
     * @return An int array where the 0th element represents if a path has been found, and the 1st element represents the length of the shortest path
     */
    public int[] checkForExitPath(int r, int c) {
        //conditional to check that r and c are still within the maze and that the current value of the maze is still 1.
        //if conditional to see if the current value of c is at the end of the maze.  Change the value of exitFound.
        //else save the current position of the maze, mark the spot as visited, and make recursive calls to check each possible path
        //change the current value of the maze back to the saved value.
        try {
            if (convertToBoolean(maze[r][c]))
            {
                maze[r][c] = 2;
            }
            else
            {
                return new int[]{0, Integer.MAX_VALUE};
            }
        }
        catch(NullPointerException exception)
        {
            return new int[]{0,Integer.MAX_VALUE};
        }
        if(Objects.equals(c,maze.length))
        {
            maze[r][c] = 3;
            return new int[] {1,1};
        }
        Boolean[] validDirections = checkDirections(r,c);
        if(validDirections[0]) {int[] result = checkForExitPath(r,c-  1); result[1] = result[1]++; if(convertToBoolean(result[0]) && result[1] < ShortestPath) {return result;}}
        if(validDirections[1]) {int[] result = checkForExitPath(r + 1,c); result[1] = result[1]++; if(convertToBoolean(result[0]) && result[1] < ShortestPath) {return result;}}
        if(validDirections[2]) {int[] result = checkForExitPath(r,c + 1); result[1] = result[1]++; if(convertToBoolean(result[0]) && result[1] < ShortestPath) {return result;}}
        if(validDirections[3]) {int[] result = checkForExitPath(r - 1,c); result[1] = result[1]++; if(convertToBoolean(result[0]) && result[1] < ShortestPath) {return result;}}
        return new int[] {0,Integer.MAX_VALUE};
    }

    /**
     * Check which directions contain a valid Integer
     * @return An array of valid directions, where each element in the array is the result
     * of an attempted array indices in a cardinal direction
     */
    public Boolean[] checkDirections(int r, int c)
    {
        Boolean[] ValidDirections = new Boolean[4];
        try {ValidDirections[0] = convertToBoolean(maze[r][c - 1]);} catch(ArrayIndexOutOfBoundsException exception) {ValidDirections[0] = false;}
        try {ValidDirections[1] = convertToBoolean(maze[r + 1][c]);} catch(ArrayIndexOutOfBoundsException exception) {ValidDirections[1] = false;}
        try {ValidDirections[2] = convertToBoolean(maze[r][c + 1]);} catch(ArrayIndexOutOfBoundsException exception) {ValidDirections[2] = false;}
        try {ValidDirections[3] = convertToBoolean(maze[r - 1][c]);} catch(ArrayIndexOutOfBoundsException exception) {ValidDirections[3] = false;}
        return ValidDirections;
    }
    /** @return The number of directions which contain a valid Integer */
    public Integer getNumberValidDirections(int r, int c)
    {
        int Count = 0; for(Boolean Result: checkDirections(r,c))
    {if(Result) {Count++;}} return Count;
    }

    /**  @return An Integer(1 or 0) converted to a Boolean */
    public Boolean convertToBoolean(Integer Input) {return Objects.equals(Input, 1);}

    public String toString()
    {
        StringBuilder output= new StringBuilder();
        for (int[] row : maze)
        {
            for (int point : row)
                output.append((Objects.equals(point, 3)) ? ("\u001B[32mP") : (((Objects.equals(point, 2) || (Objects.equals(point, 1)) ? ("\u001b[31m1") : ("\u001b[31m0")))));
            output.append("\n");
        }
        output.append((hasPath) ? ("exit found - " + ShortestPath + " STEPS") : ("exit not found"));
        return output.toString();
    }
}
