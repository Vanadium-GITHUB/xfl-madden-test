// Main Class for XFL-Madden 2.0

import java.util.*;
import java.io.*;

public class Madden {
  ////////////////////////////// Program Settings //////////////////////////////
  public static boolean GAME_RANDOMIZE;
  public static Random GAME_SEED;

  public static boolean GAME_OUTPUT;

  public static boolean GAME_DELAY;
  public static int GAME_DELAY_TIME;

  public static PrintStream output;
  /////////////////////////////// Program Main ////////////////////////////////
  public static void main(String[] args) {
      // System Initalization
    GAME_RANDOMIZE = true;
    GAME_SEED = new Random();
    GAME_DELAY = false;
    GAME_DELAY_TIME = 0;
    GAME_OUTPUT = true;

      // Start
    new Season(GAME_SEED);
  }
}
