//(c) A+ Computer Science
//www.apluscompsci.com
//Name - Cody Washington

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class MazeRunner
{
    public static void main(String[] args) throws IOException
    {
        Scanner file = new Scanner(new File("src\\main\\java\\maze.dat"));
        ArrayList<Maze> Mazes = new ArrayList<Maze>();
        while(file.hasNext())
        {
            int size = file.nextInt(); file.nextLine();
            Mazes.add(new Maze(size, file.nextLine()));
        }
        System.out.println("MAZE TEST RESULTS: ");
        for(Maze maze: Mazes) {System.out.println(maze);}
    }
}
