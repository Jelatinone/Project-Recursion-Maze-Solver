//(c) A+ Computer Science
//www.apluscompsci.com
//Name - Cody Washington

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.System.*;

public class MazeRunner
{
    public static void main( String args[] ) throws IOException
    {
        Scanner file = new Scanner(new File("src\\main\\java\\maze.dat"));
        while(file.hasNext())
        {
            int size = file.nextInt();
            file.nextLine();
            Maze test = new Maze(size, file.nextLine());
            System.out.println(test);
        }

    }
}
