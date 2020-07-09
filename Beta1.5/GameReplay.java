import java.io.*;
import java.util.*;

public class GameReplay {
  public static void main(String[] args) throws FileNotFoundException{
    Scanner console = new Scanner(System.in);
    System.out.print("What Game would you like to replay? (FileName.txt) ");
    Scanner input = new Scanner(new File(console.next()));

    while (input.hasNextLine()) {
      String str = input.nextLine();
      if (str.equals("")) {
         try { Thread.sleep(5000); } catch (Exception e) {}
      }
      System.out.println(str);
    }
    System.out.println();
    System.out.println("Execution complete.");
  }
}
