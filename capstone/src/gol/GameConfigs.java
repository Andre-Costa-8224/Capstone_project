package gol;

import java.util.Random;

public class GameConfigs {

    private int[][] grid;
    private int width;
    private int height;
    private long time;
    private Random rd = new Random();
    private long actual_generation = 1;
    private long current_population = 0;
    private String pattern;
    private boolean aliveCells;

    /**
     *
     * @param w
     * @param h
     * @param speed
     * @param pattern
     */
    public GameConfigs(int w, int h, long speed, String pattern){
        this.width = w;
        this.height = h;
        this.time = speed;
        this.grid = new int[this.width][this.height];
        this.pattern = pattern;
        if (pattern.equals("rnd")){
            fillGridRandom();
        }else{
            fillGridPattern();
        }
    }

    /**
     *
     * @return returns a boolean value, that is the state of cells
     *
     */
    public boolean thereAreAliveCells(){
        return aliveCells;
    }

    /**
     *
     * @param cells
     * @setAlivecells
     * define the state of the cells
     */
    public void setAliveCells(boolean cells){
        this.aliveCells = cells;
    }

    /**
     * @nextGeneration
     * remake the grid changing to the next generation
     */
    public void nextGeneration(){
        int[][] nextGrid = new int[this.width][this.height];
        for (int row = 0; row < this.width; row++) {
            for (int col = 0; col < this.height; col++) {

                int neighbors = countNeighbors(row,col);

                if (this.grid[row][col]==1){
                    if (neighbors<2 || neighbors >3){
                        nextGrid[row][col] = 0;
                        setAliveCells(true);
                    }else {
                        nextGrid[row][col] = 1;
                        setAliveCells(true);
                    }
                }else {
                    if (neighbors==3){
                        nextGrid[row][col] = 1;
                        setAliveCells(true);
                    }else {
                        nextGrid[row][col] = 0;
                        setAliveCells(true);
                    }
                }

            }
        }
        this.grid = nextGrid;
    }

    /**
     * @fillGridPattern
     * fill the grid using the pattern
     */
    private void fillGridPattern(){

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                grid[i][j] = 0;
            }
        }
        String[] patternSplit = this.pattern.split("#");

        for (int i = 0; i < patternSplit.length; i++) {
            for (int j = 0; j < patternSplit[i].length(); j++) {
                grid[i][j]=Integer.parseInt(String.valueOf(patternSplit[i].charAt(j)));
            }
        }
    }

    /**
     * @fillGridRandom
     * fill the grid randomly
     */
    public void fillGridRandom(){
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                grid[i][j] = new Random().nextInt(0,2);
            }
        }
    }

    /**
     *
     * @param row
     * @param col
     * @return count the number of neighbors of a cell
     *
     */
    public int countNeighbors(int row, int col){
        int count = 0;
        for (int i = row-1; i <= row+1; i++) {
            if (i>=0 && i < this.width) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if ((j >= 0 && j < this.height)) {
                        if (i != row || j != col) {
                            if (this.grid[i][j] == 1) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * @setCurrentPopulation
     * define the size of the population on the grid
     */
    public void setCurrentPopulation(){
        this.current_population = 0;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.grid[i][j] == 1){
                    this.current_population++;
                }
            }
        }
    }

    /**
     *
     * @return gets the current population in a generation
     *
     */
    public long getCurrentPopulation(){
        return this.current_population;
    }

    /**
     *
     * @throws InterruptedException
     * @printGrid
     * prints the grid on the terminal, pausing with Thread.sleep() and setting the current population
     */
    public void printGrid() throws InterruptedException {
        setCurrentPopulation();
        System.out.println("  Actual generation: " + this.actual_generation++ + " | Current population:" + getCurrentPopulation());
        for (int row[]:grid) {
            System.out.print("  [ ");
            for (int col: row) {
                System.out.print(" "+col+" ");
            }
            System.out.println(" ]\n");
        }
        Thread.sleep(this.time);
    }

}
