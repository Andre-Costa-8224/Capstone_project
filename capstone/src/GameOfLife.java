import gol.GameConfigs;

/**
 * the class that runs the game
 */
public class GameOfLife {

    public static int h;
    public static int w;
    public static int g;
    public static int s;
    public static int mg;
    public static String pattern;

    /**
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {

        /*
         * parameters that will be passed by terminal
         */
        String width = null;
        String height = null;
        String speed = null;
        String maxGeneration = null;
        String pattern1 = null;

        /*
         * Exceptions that will cause a throw when the parameters are not writen correctly
         */
        Exception error_p = new Exception("You need to write the pattern correctly, example: p=”##10101#1#01”.\n Also your pattern must have the size equal or lower than the grid");
        Exception invalid_w = new Exception("The only allowed values, to width, are: (10, 20, 40 or 80)");
        Exception invalid_h = new Exception("The only allowed values, to height, are: (10, 20, 40)");
        Exception invalid_s = new Exception("The speed must be from 250 to 1000");
        Exception invalid_g = new Exception("The generation must be a number greater than or equal to 0");

        /*
         * a loop that has conditional verifications inside
         */
        for (String arg : args) {
            if (arg.startsWith("w=")) {
                if (Integer.parseInt(arg.substring(2))==10 || Integer.parseInt(arg.substring(2))==20 || Integer.parseInt(arg.substring(2))==40 || Integer.parseInt(arg.substring(2))==80) {
                    width = arg.substring(2);
                }else {
                    throw invalid_w;
                }
            } else if (arg.startsWith("h=")) {
                if (Integer.parseInt(arg.substring(2))==10 || Integer.parseInt(arg.substring(2))==20 || Integer.parseInt(arg.substring(2))==40) {
                    height = arg.substring(2);
                }else {
                    throw invalid_h;
                }
            } else if (arg.startsWith("s=")) {
                if ((Integer.parseInt(arg.substring(2))>=250) && Integer.parseInt(arg.substring(2))<=1000) {
                    speed = arg.substring(2);
                }else{
                    throw invalid_s;
                }
            } else if (arg.startsWith("g=")) {
                maxGeneration = arg.substring(2);
                try{
                    Integer.parseInt(maxGeneration);
                }catch (Exception error){
                    System.out.print(error.getMessage()+" -> ");
                    throw invalid_g;
                }
            } else if (arg.startsWith("p=")) {
                pattern1 = arg.substring(2);
                if (pattern1.length()==0){
                    pattern1="#";
                }
            }
        }

        //Verify the pattern, if is null or if is lower than 1 or if larger than width
        if (pattern1==null){
            throw error_p;
        } else if (pattern1.length()<1) {
            throw error_p;
        }else if((pattern1.split("#").length)>Integer.parseInt(width)) {
            throw error_p;
        }

        //Verify if the pattern isn't the "rnd", the especial option
        if (!pattern1.equals("rnd")) {
            for (int i = 0; i < pattern1.length(); i++) {
                if (!String.valueOf(pattern1.charAt(i)).equals("1") && !String.valueOf(pattern1.charAt(i)).equals("#") && !String.valueOf(pattern1.charAt(i)).equals("0")) {
                    throw error_p;
                }
            }
        }

        /*
         * variables that receives transformed params to integers
         */
        w = Integer.parseInt(width);
        h = Integer.parseInt(height);
        s = Integer.parseInt(speed);
        mg = Integer.parseInt(maxGeneration);
        pattern = pattern1;

        //verify the size of the pattern
        for (int i = 0; i < pattern.split("#").length; i++) {
            if (pattern.split("#")[i].length()>h){
                throw error_p;
            }
        }

        //start the game
        play();

    }

    public static void play() throws InterruptedException {
        //instance of game of life
        GameConfigs game = new GameConfigs(w, h, s, pattern);
        //if pattern is bigger than 0 does the for loop, else if it is equal to 0 do an infinite loop
        if (mg>0) {
            for (int i = 0; i < mg; i++) {
                game.printGrid();
                System.out.println("----------------------------------------------------");
                game.nextGeneration();
            }
        } else if (mg==0) {
            //the previous variable saves the current population for be used to be compared with the next population
            long previous = 0;
            //the repeated variable is a counter that saves how many times the same size of the population was repeated
            long repeated = 0;

            while (true){

                game.printGrid();

                //here I define that the cells will be false previously,
                //so if the nextGeneration not generates or kill any cell the game will stop.
                game.setAliveCells(false);

                System.out.println("---------------------------------------------------");
                game.nextGeneration();

                //verifies if there are alive cells
                if (game.thereAreAliveCells()) {
                    //verify if the size of the current population is equal to previous population
                    if (game.getCurrentPopulation() == previous) {
                        repeated++;//i left the incrementation here to verify previously until 3 times.
                        if (repeated > 3) {
                            break;
                        } else if (game.getCurrentPopulation()==0) {
                            break;
                        }
                    }
                    else {
                        repeated=0;
                    }

                }else{
                    break;
                }

                //here i save the currentPopulation in the variable.
                previous = game.getCurrentPopulation();
            }
        }
    }
}