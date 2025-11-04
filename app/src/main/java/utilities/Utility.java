package utilities;

public class Utility {
       //Utility for randomized number ---
    public static int randomNumberInterval(int from, int to) {
        return (int) (Math.random() * (to - from + 1)) + from;
    }
 
}
