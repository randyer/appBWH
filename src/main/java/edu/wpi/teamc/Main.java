package edu.wpi.teamc;

import static java.lang.System.exit;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
  public static final Logger logger =
      Logger.getLogger("main"); // need to copy this and then just use logger. commands

  public static void main(String[] args) {

    logger.info("Start application");
    CApp.launch(CApp.class, args);

    Scanner scan = new Scanner(System.in);
    while (true) {
      String command = scan.nextLine();
      if (command.equals("exit")) {
        exit(0);
        break;
      }
      if (command.equals("help")) {
        System.out.println("Commands: ");
        System.out.println("exit - exit the program");
        System.out.println("help - display this help message");
        System.out.println("test screensaver - test screensaver");
      }
      if (command.equals("test screensaver")) {
        CApp.logoutPopUp();
      }
    }

    //    ImportCSV importCSV = new ImportCSV();
    //    boolean test =
    //        importCSV.importMapCSV(
    //            "src/main/resources/edu/wpi/teamc/Node.csv",
    //            "src/main/resources/edu/wpi/teamc/Edge.csv",
    //            "src/main/resources/edu/wpi/teamc/Move.csv",
    //            "src/main/resources/edu/wpi/teamc/LocationName.csv");
    //
    //    System.out.println(test);
  }
}
