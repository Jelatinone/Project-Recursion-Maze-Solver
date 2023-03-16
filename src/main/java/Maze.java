//(c) A+ Computer Science
//www.apluscompsci.com
//Name - Cody Washington

//Libraries

import java.util.Arrays;
import java.util.Objects;


/**
 * {@code Maze} class represents a two-dimensional square maze made up of tiles represented either by a one, or a zero;
 * where a one represents a path tile and a zero a non-path tile
 * @author Cody Washington
 */
public class Maze extends Thread
{
    private Integer[][] maze;
    public Boolean hasEntrance = false;
    public Boolean hasExit = false;
    public Boolean hasPath = false;
    public Boolean isValid = false;
    public Integer ShortestPath = -1;

    /** Default Constructor. */
    public Maze()
    {
        maze = new Integer[0][0];
    }

    /** Constructor. */
    public Maze(int size, String line)
    {
        for(int i = 0, j = 0; i < size; i++, j += size)
        {
            maze[i] = Arrays.stream(line.substring(j, (j + size)).split(" ")).map(Integer::parseInt).toList().toArray(Integer[]::new);
            if(Objects.equals(i,0)) {if(Objects.equals(maze[0][0],1) && getNumberValidDirections(0,0) != 0) {hasEntrance = true;}}
            if(Objects.equals(maze[i][size],1) && getNumberValidDirections(i,size) != 0) {hasExit = true;}
        }
        if(this.hasEntrance && this.hasExit) {isValid = true; this.start();}
    }

    public void run()
    {
        if(this.isAlive() && this.isValid)
        {
            Integer[] Results = checkForExitPath(0, 0);
            ShortestPath = Results[1];
            hasPath = convertToBoolean(Results[0]);
        }
    }

    /**
     * Check is the maze(which contains an exit and entrance) contains a valid path
     * between the exit and entrance without using diagonals.
     * @return If the recursive check has reached an exit in the last column
     */
    public Integer[] checkForExitPath(int r, int c)
    {
        //conditional to check that r and c are still within the maze
        //and that the current value of the maze is still 1.

        //if conditional to see if the current value of c is at the end
        //of the maze.  Change the value of exitFound.

        //else save the current position of the maze, mark the spot
        //as visited, and make recursive calls to check each possible path

        //change the current value of the maze back to the saved value
        try {if(convertToBoolean(maze[r][c])) {maze[r][c] = 2;} else {return new Integer[] {0,Integer.MAX_VALUE};}}
        catch(NullPointerException exception) {return new Integer[] {0,Integer.MAX_VALUE};}
	    if(Objects.equals(c,maze[0].length)) {maze[r][c] = 3; return new Integer[] {1,1};}
        Boolean[] validDirections = checkDirections(r,c);
        if(validDirections[0]) {Integer[] result = checkForExitPath(r,c-1);result[1] = result[1]++; if(convertToBoolean(result[0]) && result[1] < ShortestPath) {return result;}}
        if(validDirections[1]) {Integer[] result = checkForExitPath(r+1,c); result[1] = result[1]++; if(convertToBoolean(result[0]) && result[1] < ShortestPath) {return result;}}
        if(validDirections[2]) {Integer[] result = checkForExitPath(r,c+1); result[1] = result[1]++; if(convertToBoolean(result[0]) && result[1] < ShortestPath) {return result;}}
        if(validDirections[3]) {Integer[] result = checkForExitPath(r-1,c); result[1] = result[1]++; if(convertToBoolean(result[0]) && result[1] < ShortestPath) {return result;}}
        return new Integer[] {0,Integer.MAX_VALUE};
}

    /**
     * Check which directions contain a valid Integer
     * @return An array of valid directions, where each element in the array is the result
     * of an attempted array indices in a cardinal direction
     */
    public Boolean[] checkDirections(int r, int c)
    {
        Boolean[] ValidDirections = new Boolean[4];
        try {if(convertToBoolean(maze[r][c-1])) {ValidDirections[0] = true;}} catch(NullPointerException exception) {ValidDirections[0] = false;}
        try {if(convertToBoolean(maze[r+1][c])) {ValidDirections[1] = true;}} catch(NullPointerException exception) {ValidDirections[1] = false;}
        try {if(convertToBoolean(maze[r][c+1])) {ValidDirections[2] = true;}} catch(NullPointerException exception) {ValidDirections[2] = false;}
        try {if(convertToBoolean(maze[r-1][c])) {ValidDirections[3] = true;}} catch(NullPointerException exception) {ValidDirections[3] = false;}
        return ValidDirections;
    }
    /** @return The number of directions which contain a valid Integer */
    public Integer getNumberValidDirections(int r, int c)
    {
        int Count = 0;
        for(Boolean Result: checkDirections(r,c)) {if(Result) {Count++;}}return Count;
    }

    public Boolean convertToBoolean(Integer Input) {return Objects.equals(Input, 1);}

    public String toString()
    {
        StringBuilder output= new StringBuilder();
        for (Integer[] integers : maze) {
            for (Integer integer : integers)
                output.append((Objects.equals(integer, 3)) ? ("\u001B[32mP") : (((Objects.equals(integer, 2) || (Objects.equals(integer, 1)) ? ("\u001b[31m1") : ("\u001b[31m0")))));
            output.append("\n");
        }
        output.append((hasPath) ? ("exit found - " + ShortestPath + " STEPS") : ("exit not found"));
        return output.toString();
    }
}
