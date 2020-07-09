import java.util.*;
import java.io.*;

public class Game {

  /////////////////////////////// Object Memory ///////////////////////////////
    // Game Setting
  private String location;
  private int temperature;
  private int attendance;

    // Output Stream
  private String gameName;
  private PrintStream output;

    // Game Control
  private Plays playBook;
  private boolean secondQuarter;
  private int clockTime;
  private int half;
  private int quarter;

  private int gameWeek;

  //////////////////////////////// Constructor ////////////////////////////////
  public Game(Random rand, int week, Team away, Team home)  {
    this.gameWeek = week;
    initialization(rand, week, home);

    if (Madden.GAME_OUTPUT) {
      if (week < 11) { this.gameName = "WK" + week + "-"; }
      else if (week == 11) { this.gameName = home.division() + "-"; }
      else if (week == 12) { this.gameName = "CHAMP-"; }
      this.gameName += away.shor() + "@" + home.shor();
      try { this.output = new PrintStream(new FileOutputStream(this.gameName + ".txt")); }
      catch (Exception error) { this.output = new PrintStream(System.out); }
    }
    output.print("Week " + gameWeek + " | " + away.record() + " " + away.name() + " @ ");
    output.println(home.record() + " " + home.name() + "\n");
    GameStats homeStats = home.gameStats(week);
    GameStats awayStats = away.gameStats(week);

    // Game Preview
    half(rand, away, home, awayStats, homeStats);
    output.println("End of first half.\n");
    // HalftimeUpdate
    half(rand, home, away, homeStats, awayStats);
    output.println("End of regulation.\n");
    if (homeStats.teamScore() == awayStats.teamScore()) {
      overTime(rand, home, away, homeStats, awayStats);
    }

    printGameSummary(home, away, homeStats, awayStats);
  }

  private void initialization(Random rand, int week, Team home) {
    this.location = home.stadium() + ", " + home.city();
    this.temperature = rand.nextInt(20) + 28 + 3 * week;

    int[] capacities = new int[] {66965, 20000, 82500, 65890, 72000, 49115, 40000, 27000};
    double percentage = (rand.nextInt(400) + 301) / 1000;
    this.attendance = (int) (percentage * capacities[home.num()]);

    this.playBook = new Plays();
    this.secondQuarter = false;
    this.clockTime = 0;
    this.half = 0;
    this.quarter = 0;
  }

  //////////////////////////////// Game Control ////////////////////////////////
    // Regulation
  private void half(Random rand, Team first, Team second, GameStats firstStats, GameStats secondStats) {
    this.half += 1;
    this.clockTime = 1800;
    this.secondQuarter = false;
    quarterUpdate(firstStats, secondStats);
    while (clockTime > 0) {
      drive(rand, first, second, firstStats, secondStats);
      if (clockTime > 0) {
        drive(rand, second, first, secondStats, firstStats);
      }
    }
  }

  private void drive(Random rand, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
      // Drive Set-Up
    offenseStats.driveUpdate(true);
    int startingPosition;
    int playType;
    int playTime = 1;
    int playGain = 0;

      // Drive Start
    if (defenseStats.turnover()) {
      defenseStats.turnoverUpdate(false);
      startingPosition = defenseStats.fieldToGo(); }
    else {
      printGameClock();
      output.println(" | Kick-off!");
      startingPosition = 100 - playBook.kickoff(rand, output, offense, defense, offenseStats, defenseStats);
      playTime = (int) (0.3 * startingPosition) + 8;
      offenseStats.timeOPUpdate(playTime);
      this.clockTime -= playTime;
    }

    if (clockTime <= 0 || startingPosition > 99) {
      offenseStats.driveUpdate(false);
      if (startingPosition > 99) {
        touchdown(rand, offense, defense, offenseStats, defenseStats);
      }
    } else {
      offenseStats.newDrive(startingPosition);
    }

    while (offenseStats.drive()) {
      // Print new Play
      if (0 > 1) {
        try { Thread.sleep(1000 * playTime); } catch (InterruptedException e) { }
      }
      printNewPlay(offense, defense, offenseStats);
      // Compute new Play
      playType = playBook.selection(rand, clockTime, half, offense, offenseStats, defenseStats);
      if (playType == 1) {
        playGain = playBook.run(rand, output, offense, defense, offenseStats, defenseStats);
        playTime = rand.nextInt(12) + 23;
        if (clockTime > 120 && offenseStats.teamScore() - defenseStats.teamScore() > 0) {
          playTime += rand.nextInt(7) + 6; } } // Run
      else if (playType == 2) {
        playGain = playBook.sneak(rand, output, offense, defense, offenseStats, defenseStats);
        playTime = rand.nextInt(10) + 25; } // QB-Sneak
      else if (playType == 3) {
        playGain = playBook.pass(rand, output, offense, defense, offenseStats, defenseStats);
        playTime = rand.nextInt(7) + 7;
        if (playGain != 0) {
          playTime += rand.nextInt(10) + 5;
        }
        if (playGain > 20) {
          playTime += 10; } } // Pass
      else if (playType == 4) {
        playGain = playBook.hail(rand, output, offense, defense, offenseStats, defenseStats);
        playTime = rand.nextInt(11) + 8; } // Hail Mary
      else if (playType == 5) {
        playGain = playBook.fGoal(rand, output, offense, defense, offenseStats, defenseStats);
        playTime = rand.nextInt(6) + 8;
        if (clockTime > playTime && playGain == 0) {
          output.print(defense.brand() + " to start on the ");
          printYardLine(offense.shor(), defense.shor(), offenseStats.fieldToGo());
          output.println("."); } } // Field-Goal
      else {
        playGain = playBook.punt(rand, output, offense, defense, offenseStats, defenseStats);
        playTime = rand.nextInt(18) + 12; } // Punt

      // Checks
      if (playTime > this.clockTime) { playTime = this.clockTime; }

      // Update Game State
      this.clockTime -= playTime;
      offenseStats.playUpdate(playType, playGain, playTime, defenseStats);

      if (!this.secondQuarter && this.clockTime < 900) {
        this.secondQuarter = true;
        quarterUpdate(offenseStats, defenseStats);
      }
        //
      if (this.clockTime <= 0 || offenseStats.fieldToGo() >= 100 || offenseStats.down() >= 5) {
        output.println();
        offenseStats.driveUpdate(false);
        if (offenseStats.fieldToGo() >= 100) {
          if (this.clockTime <= 0) {
            output.println("Safety.");
          } else {
              output.println("Safety. " + defense.brand() + " will recieve.");
          }
          defenseStats.scoreUpdate(2);
          defenseStats.safetyGainUpdate();
          offenseStats.otherScoreUpdate(2);
          offenseStats.safetyLossUpdate();
        } else if (offenseStats.down() >= 5) {
          output.println("Turnover on downs.");
          output.println();
          offenseStats.turnoverUpdate(true);
        }
      }
        // EndZone?
      if (offenseStats.fieldToGo() <= 0) {
        if (!offenseStats.turnover()) { output.println("Touchdown, " + offense.brand() + "!");
          touchdown(rand, offense, defense, offenseStats, defenseStats); }
        else if (clockTime >= 1) { output.println("Turnover in endzone. Touchback"); offenseStats.setFieldToGo(20); }
      }

    }

    printDriveSummary(offense, defense, offenseStats, defenseStats);
  }

    // Overtime
  private void overTime(Random rand, Team home, Team away, GameStats homeStats, GameStats awayStats) {
    output.println("-=======- OVERTIME -=======-");
    quarterUpdate(homeStats, awayStats);
    int round = 0;
    int diff = 0;

    while (round < 5 && diff <= 5 - round) {
      round += 1;
      diff = overTimeRound(rand, round, home, away, homeStats, awayStats);
    }
    while (diff == 0) {
      round += 1;
      diff = overTimeRound(rand, round, home, away, homeStats, awayStats);
    }
    output.println();
    printScores(home, away, homeStats, awayStats);
  }

  private int overTimeRound(Random rand, int round, Team home, Team away, GameStats homeStats, GameStats awayStats) {
    output.println();
    output.println("Round " + round);
    if (round % 2 == 1) {
      overTimeHit(rand, away, home, awayStats, homeStats);
      overTimeHit(rand, home, away, homeStats, awayStats);
    } else {
      overTimeHit(rand, home, away, homeStats, awayStats);
      overTimeHit(rand, away, home, awayStats, homeStats);
    }
    return (homeStats.teamScore() - awayStats.teamScore());
  }

  private void overTimeHit(Random rand, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    output.println(offense.brand() + " will now attempt.");
    int offenseRoll = rand.nextInt(100) + offense.offenseSkill() - defense.defenseSkill();
    int offenseDecision = rand.nextInt(100) + offense.offensePreference();
    int outcomeRoll = rand.nextInt(100);

    if (offenseRoll > 65) {
      if (offenseDecision > 55) {
        if (outcomeRoll > 55) {
          output.println(offense.WR(rand) + " catches the ball in the endzone. Score.");
        } else {
          output.println(offense.TE(rand) + " catches the ball in the endzone. Score.");
        }
      } else {
        if (outcomeRoll > 25) {
          output.println(offense.RB(rand) + " runs into the endzone. Score.");
        } else if (outcomeRoll > 15) {
          output.println(offense.RB(rand) + " jumps into the endzone. Score.");
        } else {
          output.println(offense.QB(rand) + " lunges into the endzone. Score.");
        }
      }
      offenseStats.scoreUpdate(1);
    } else {
      if (offenseDecision > 55) {
        if (outcomeRoll > 50) {
          output.println(offense.QB(rand) + " throws the ball to high for " + offense.WR(rand) + ". Incomplete.");
        } else {
        output.println(defense.SEC(rand) + " knocks the ball away for an incompletion. Intended for " + offense.WR(rand) + ".");
        }
      } else {
        if (outcomeRoll > 50) {
          output.println(offense.RB(rand) + " is stuffed at the line.");
        } else {
          output.println(offense.RB(rand) + " is tackled for a loss by " + defense.DL(rand) + ".");
        }
      }
    }
  }

  /////////////////////////////// Print Commands ///////////////////////////////
  private void printNewPlay(Team offense, Team defense, GameStats offenseStats) {
    output.println();
    printGameClock();
    printDownCounter(offense.shor(), offenseStats.down(), offenseStats.yardsToGo(), offenseStats.fieldToGo());
    output.print(" @ ");
    printYardLine(offense.shor(), defense.shor(), offenseStats.fieldToGo());
    output.println();
  }

  private void printGameClock() {
    if (this.clockTime < 0) { this.clockTime = 0; }
    int minutes = clockTime / 60;
    int seconds = clockTime % 60;
    if (minutes >= 15) { minutes -= 15; }
    if (minutes < 10 && seconds < 10) {
			output.print("[Q" + this.quarter + " | 0" + minutes + ":0" + seconds + "] ");
		} else if (minutes < 10) {
			output.print("[Q" + this.quarter + " | 0" + minutes + ":" + seconds + "] ");
		} else if (seconds < 10) {
			output.print("[Q" + this.quarter + " | " + minutes + ":0" + seconds + "] ");
		} else {
			output.print("[Q" + this.quarter + " | " + minutes + ":" + seconds + "] ");
		}
  }

  private void printDownCounter(String shor, int down, int yardsToGo, int fieldToGo) {
    String downPrint;
    if (down == 1) { downPrint = " 1st & "; }
    else if (down == 2) { downPrint = " 2nd & "; }
    else if (down == 3) { downPrint = " 3rd & "; }
    else if (down == 4) { downPrint = " 4th & "; }
    else { downPrint = " Error. 5th Down & "; }

    if (fieldToGo == yardsToGo) { output.print(shor + downPrint + "Goal "); }
    else { output.print(shor + downPrint + yardsToGo + " "); }
  }

  private void printYardLine(String offense, String defense, int fieldToGo) {
    if (fieldToGo < 50) {
      output.print(defense + " " + fieldToGo);
    } else if (fieldToGo > 50) {
      int yards = 100 - fieldToGo;
      output.print(offense + " " + yards);
    } else {
      output.print(" the '50");
    }
  }

  private void printScores(Team home, Team away, GameStats homeStats, GameStats awayStats) {
    output.print(" -= " + away.brand() + " - " + awayStats.teamScore() + " ; ");
    output.println(home.brand() + " - " + homeStats.teamScore() + " =- ");
  }

  private void printDriveSummary(Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    output.println("\n=================================");
    output.println("          Drive Summary: ");
    output.println("   Time: " + offenseStats.driveTime());
    output.println("   Plays: " + offenseStats.driveRuns() + " Run ; " + offenseStats.drivePasses() + " Pass");
    output.println("   Yards: " + offenseStats.driveYards());
    output.println();
    printScores(offense, defense, offenseStats, defenseStats);
    output.println("=================================\n");
  }

  private void printGameSummary(Team home, Team away, GameStats homeStats, GameStats awayStats) {
    output.println();
    if (this.half == 2 && this.clockTime <= 0) {
      if (homeStats.teamScore() > awayStats.teamScore()) {
        home.winUpdate(gameWeek, true); away.winUpdate(gameWeek, false); }
      else { away.winUpdate(gameWeek, true); home.winUpdate(gameWeek,false); }
    }

    // Visual Adjustment
     String awayTeam = away.shor();
     if (awayTeam.length() == 2) { awayTeam = awayTeam + " "; }
     String awayScore = "" + awayStats.teamScore();
     if (awayStats.teamScore() < 10) { awayScore = awayStats.teamScore() + " "; }
     String awayPass = "" + awayStats.passYards();
     if (awayStats.passYards() < 100) { awayPass = awayStats.passYards() + " "; }
     String awayRun = "" + awayStats.runYards();
     if (awayStats.runYards() < 100) { awayRun = awayStats.runYards() + " "; }

     // Print
     output.println(away.name() + " at " + home.name() + " Game Stats:");
     output.println("======================= " + awayTeam + " ======== " + home.shor());
     output.println("Score:                  " + awayScore + "           " + homeStats.teamScore());
     output.print("Time of Possession:     " + awayStats.timeOfPosession());
     output.println("        " + homeStats.timeOfPosession());
     output.println("Total Yards:            " + awayStats.totalYards() + "          " + homeStats.totalYards());
     output.println("Pass Yards:             " + awayPass + "          " + homeStats.passYards());
     output.println("Run Yards:              " + awayRun + "          " + homeStats.runYards());
     output.println("Turnovers:              " + awayStats.turnoverLoss() + "            " + homeStats.turnoverLoss());
     output.println();
     output.println("Turnovers:              " + awayStats.turnoverGain() + "            " + homeStats.turnoverGain());
     output.println("Interceptions:          " + awayStats.interceptionGain() + "            " + homeStats.interceptionGain());
     output.println("Fumbles:                " + awayStats.fumbleGain() + "            " + homeStats.fumbleGain());
     output.println("Safeties:               " + awayStats.safetyGain() + "            " + homeStats.safetyGain());
     output.println();
     output.println(home.shor() + " " + homeStats.quarterScores() + " " + homeStats.quarterTotalYards());
     output.println(away.shor() + " " + awayStats.quarterScores() + " " + awayStats.quarterTotalYards());
  }
  ////////////////////////////////// Updaters //////////////////////////////////
  private void quarterUpdate(GameStats stats1, GameStats stats2) {
    this.quarter += 1;
    stats1.quarterUpdate(quarter);
    stats2.quarterUpdate(quarter);
  }

  ////////////////////////////////// Scoring //////////////////////////////////
  private void touchdown(Random rand, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    offenseStats.driveUpdate(false);
    int points = 6;
    int diceRoll = rand.nextInt(100);
    if (diceRoll > 94) { points += 3; }
    else if (diceRoll > 80) { points += 2; }
    else if (diceRoll > 56) { points += 1; }

    offenseStats.touchdownUpdate();
    offenseStats.scoreUpdate(points);
    defenseStats.otherScoreUpdate(points);
  }
}
