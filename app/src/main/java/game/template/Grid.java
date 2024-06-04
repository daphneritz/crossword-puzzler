package game.template; 

import java.io.InputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList; 
import java.util.Map; 
import java.util.HashMap;

public class Grid {
    
    private int rows; 
    private int cols;  
    private String[][] grid;
    private Map<Integer, String> acrossHints; 
    private Map<Integer, String> downHints;

    public Grid() {
        rows=5; 
        cols=5;
        grid = new String[rows][cols];
        acrossHints = new HashMap<Integer, String>();
        downHints = new HashMap<Integer, String>();
        initializeMaps();
    }

    public void setCell(int row, int col, String value) {
        grid[row][col] = value;
    }

    public void initializeMaps() {
        for (int i = 0; i < 5; i++) {
            acrossHints.put(i, "");
        }
        for (int i=6; i<10; i++){
            downHints.put(i, "");
        }
    }

    public String getCell(int row, int col) {
        return grid[row][col];
    }

    public static Grid loadCrossword(InputStream inputStream) throws Exception {
        Grid grid = new Grid();
        Scanner scanner = new Scanner(inputStream);
        outerLoop:
        for (int row = 0; row < 5; row++) {
            String line = scanner.nextLine();
            for (int col = 0; col < 5; col++) {
                String a=String.valueOf(line.charAt(col));
                // if a is not a letter, throw exception 
                if(!(Character.isLetter(a.charAt(0))||a.equals("+")||a.equals("}"))){
                    throw new Exception("Invalid character in grid: "+a); 
                }
                if(a.equals("}")){
                    break outerLoop; 
                }
                a.toUpperCase(); 
                grid.setCell(row, col, a);
            }
        }
        while(scanner.hasNextLine()){
           // get number (first character) and direction (next character) then hint (rest of line)
              String line = scanner.nextLine();
                int number = Integer.parseInt(line.substring(0,1));
                String direction = line.substring(1,2);
                String hint = line.substring(2);
                if(direction.equals("A")){
                    grid.acrossHints.put(number, hint);
                }else if(direction.equals("D")){
                    grid.downHints.put(number, hint);
                }
        }
        scanner.close(); 
        return grid; 

    }

    public Map<Integer, String> getAcrossHints() {
        return acrossHints;
    }

    public Map<Integer, String> getDownHints() {
        return downHints;
    }

    public void setAcrossHint(int clue, String hint) {
        acrossHints.put(clue, hint);
    }

    public void setDownHint(int clue, String hint) {
        downHints.put(clue, hint);
    }

    public void clearHints() {
        // set all hints to empty string
        /* ok this is cheating because the only way to clear all hints is if you clear the 
        // whole board which clears any black squares ergo there will only be 5 across clues and 5 down clues no matter what 
        but this wouldn't work otherwise sorry 
        */
        acrossHints.clear();
        downHints.clear();
        for (int i = 0; i < 5; i++) {
            acrossHints.put(i, "");
        }
        for (int i=6; i<10; i++){
            downHints.put(i, "");
        }
    }

    public void print() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(grid[row][col]);
            }
            System.out.println();
        }
    }

    public void clearBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = "";
            }
        }
    }

}
