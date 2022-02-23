import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;

  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public  SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[4];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";

    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int [numRows] [numCols]; 
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    grid[row][col] = tool; 
  }

  //copies each element of grid into the display
  public void updateDisplay()  
  {
    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
      
      if (grid[r][c] == EMPTY) {
        display.setColor(r, c, Color.BLACK);
      }
      else if (grid[r][c] == METAL) {
        display.setColor(r, c, Color.GRAY);
      }
      else if (grid[r][c] == SAND) {
        display.setColor(r, c, Color.YELLOW);
      }
      else if (grid[r][c] == WATER){ //
          display.setColor(r, c, Color.BLUE);
      
      }
    }
  }
}


  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
      Random rand = new Random();
    int x = rand.nextInt(grid.length - 1);
    int y = rand.nextInt(grid[0].length);

    if (grid[x][y] == SAND) {
      if (grid[x+1][y] == EMPTY) {
        grid[x][y] = EMPTY;
        grid[x+1][y] = SAND;
      }
    }
    else if(grid[x][y] == WATER) {
      int num = rand.nextInt(3);
      System.out.println("" + num);
      if (grid[x][y] == WATER) {
        if (
            (
              ( (y+1) < grid[0].length) && (y > 1)) && ((grid[x+1][y] == EMPTY) || 
                (grid[x+1][y] == WATER)) && ((grid[x][y+1] == EMPTY)  || (grid[x][y+1] == WATER)) && 
                  ((grid[x][y-1] == EMPTY)  || (grid[x][y-1] == WATER)))
        {

          //out of bounds
          grid[x][y] = EMPTY;

          if ((num == 0) && (grid[x][y+1] == EMPTY)) {
            grid[x][y+1] = WATER; //particle right
          }
          else if ((num == 1) && (grid[x][y-1] == EMPTY)) {
            grid[x][y-1] = WATER; //particle left
          }
          else if ((num == 2) && (grid[x+1][y] == EMPTY)) {
            grid[x+1][y] = WATER; //particle down

        }
      }
    }
  } 
} 
 //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
