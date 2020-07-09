import java.util.*;
import java.io.*;

public class Plays {

  //////////////////////////////// Constructor ////////////////////////////////
  public Plays() {
  }

  //////////////////////////////////// AI //////////////////////////////////////
  public int selection(Random rand, int clockTime, int half, Team offense, GameStats offenseStats, GameStats defenseStats) {
    int preference = offense.offensePreference();
    int risk = offenseStats.fieldToGo() / 10;
    int yards = offenseStats.yardsToGo();
    int lead = offenseStats.teamScore() - defenseStats.teamScore();
    int down = offenseStats.down();

    if ((lead < -6 && half == 2 && clockTime < 400) || (lead < -3 && half == 2 && clockTime < 200)) {
      if (preference < 0) { preference += 2; }
      else { preference += 1; }
    }

    int diceRoll = rand.nextInt(100) + preference;

    // Closing
    if (clockTime < 60) {
      // If leading/tie
      if (lead >= 0) {
        if (risk > 5 && down == 4) { return 6; }
        if (risk > 5) { return 1; }
        if (risk > 4) { if (diceRoll >= 50) { return 3; } else { return 1; } }
        if (down == 4 && offenseStats.fieldToGo() < 41 && clockTime < 28) { return 5; }
        if (down == 4 && offenseStats.fieldToGo() > 40 && clockTime < 28) { return 3; }
        if (yards < 3) { return 2; }
        if (diceRoll >= 55) { return 3; }
        return 2;
      }

      // If losing
      if (offenseStats.fieldToGo() < 39 && lead >= -3) {
        return 5;
      } else if (offenseStats.fieldToGo() < 25) {
        return 3;
      } else {
        return 4;
      }
    }

    // Not Closing
    if (offenseStats.down() < 4) {
      int num = rand.nextInt(100);
      if (offenseStats.fieldToGo() < 20) { num -= 10; }
      if (num + preference > 46) { return 3; }
      return 1;
    } else if (offenseStats.yardsToGo() < 3 && risk < 7) {
      return 2;
    } else if (offenseStats.fieldToGo() < 40) {
      return 5;
    } else {
      return 6;
    }
  }

  public int skillDiff(Team offense, Team defense, GameStats offenseStats) {
    int skill = (2 * offense.offenseSkill() - defense.defenseSkill());
    if (offenseStats.fieldToGo() < 20) {
      skill -= 1;
    } else if (offenseStats.fieldToGo() > 80) {
      skill += 1;
    }
    return skill;
  }

  public int effortDiff(GameStats offenseStats, GameStats defenseStats) {
    return (int) (1.5 * defenseStats.teamScore() - offenseStats.teamScore());
  }

  private String direction(Random rand) {
    int diceRoll = rand.nextInt(3);
    if (diceRoll == 0) { return "to the left"; }
    else if (diceRoll == 1) { return "up the middle"; }
    else { return "to the right"; }
  }

  /////////////////////////////////// Plays ////////////////////////////////////
  public int kickoff(Random rand, PrintStream output, Team rec, Team kick, GameStats recStats, GameStats kickStats) {
    String returner = rec.R(rand);
    int skill = skillDiff(rec, kick, recStats);
    int effort = effortDiff(recStats, kickStats);
    int diceRoll = rand.nextInt(100 + skill + effort);
    int dice2Roll = rand.nextInt(100);

    int bounce = rand.nextInt(100 - skill) / 4 - 2;
    int ballReturn;
    int fieldToGo;

    output.println("The " + kick.brand() + "' " + kick.K() + " will kick the ball.");
    output.println(rec.brand() + " have elected " + returner + " to receive.");
    output.println();

    if (bounce < 1) {
      if (dice2Roll > 75) {
        fieldToGo = 100 - 35;
        output.println("Ball sails into the endzone. Major touchback,");
        output.print(rec.brand() + " to start their drive on the ");
        printYardLine (output, rec.shor(), kick.shor(), fieldToGo);
      } else {
        fieldToGo = 100 - 15;
        output.println("Ball hits the turf at the " + (rand.nextInt(8) + 1) + " yard line");
        output.println("and bounces into the endzone. Minor touchback.");
        output.print(rec.brand() + " to start their drive on the ");
        printYardLine(output, rec.shor(), kick.shor(), fieldToGo);
      }
    } else {
      fieldToGo = 100 - bounce;
      if (dice2Roll > 83) {
        output.println("Ball hits the turf and rolls to a halt at the " + bounce + ".");
        output.print(rec.brand() + " will start their drive on the ");
        printYardLine(output, rec.shor(), kick.shor(), fieldToGo);
      } else if (dice2Roll > 70) {
        output.println("A fair catch is whistled.");
        output.print(rec.brand() + " to start their drive on the ");
        printYardLine(output, rec.shor(), kick.shor(), fieldToGo);
      } else { // Returnable Ball
        output.println(returner + " catches the ball from the " + bounce + ".");
        if (diceRoll < 16 && bounce > 4) {
          ballReturn = 0 - rand.nextInt(4);
          output.println(returner + " is tackled for a " + Math.abs(ballReturn) + " yard loss.");
        } else if (diceRoll < 30) {
          ballReturn = 0;
          output.println(returner + " is immediately tackled. No gain.");
        } else if (diceRoll < 60) {
          ballReturn = rand.nextInt(5) + 1;
          output.println(returner + " is tackled after a short " + ballReturn + " yard gain.");
        } else if (diceRoll < 80) {
          ballReturn = rand.nextInt(9) + 3;
          output.println(returner + " is tackled after a " + ballReturn + " yard gain.");
        } else if (diceRoll < 90) {
          ballReturn = rand.nextInt(13) + 7;
          output.println(returner + " is brought down after a " + ballReturn + " yard return.");
        } else if (diceRoll < 96) {
          ballReturn = rand.nextInt(17) + 15;
          output.println(returner + " is brought down after an amazing " + ballReturn + " yard return!");
        } else if (diceRoll < 98) {
          ballReturn = rand.nextInt(20) + 23;
          output.println(returner + " is knocked out of the field after a " + ballReturn + " yard return!");
        } else {
          ballReturn = rand.nextInt(40) + 35;
          if (ballReturn > fieldToGo && rand.nextInt(10) < 8) {
              ballReturn -= 15;
          }
          if (fieldToGo - ballReturn != 0) {
            output.println(returner + " returns the ball for an amazing " + ballReturn + " yards!");
            output.println(kick.SEC(rand) + " with the saving tackle.");
          } else {
            output.println("Touchdown!");
          }
        }
        fieldToGo -= ballReturn;
        if (fieldToGo != 0) {
          output.print(rec.brand() + " to start their drive on the ");
          printYardLine(output, rec.shor(), kick.shor(), fieldToGo);
        }
      }
    }
    output.println(".");
    return fieldToGo;
  }

  public int run(Random rand, PrintStream output, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    String runner = offense.RB(rand);
    String direction = direction(rand);
    int learn = 0;
    if (offenseStats.fumbleLoss() > 2 || offenseStats.turnoverLoss() > 3) {
      learn += offenseStats.turnoverLoss() - 1; }
    int skill = skillDiff(offense, defense, offenseStats);
    int effort = effortDiff(offenseStats, defenseStats);
    int diceRoll = rand.nextInt(100 + skill + effort) + learn;
    int dice2Roll = rand.nextInt(100);

    int playGain;

    if (offenseStats.fieldToGo() >= 93) {
      diceRoll += rand.nextInt(5);
    } else if (offenseStats.fieldToGo() <= 13 && diceRoll > 20) {
      diceRoll -= rand.nextInt(5);
    }

    if (diceRoll < 8) {
      if (dice2Roll + (2 * learn) < 30) {
        if (dice2Roll < 12) {
          playGain = rand.nextInt(9) - 9;
          output.println(runner + " gets caught in the back field. Fumbles the ball.");
        } else if (dice2Roll < 19) {
          playGain = rand.nextInt(7) - 7;
          output.println("Miscommunication on the call. " + offense.QB(rand));
          output.println("and " + runner + " slip on the handoff. Fumble.");
        } else {
          playGain = rand.nextInt(14) + 1;
          if (playGain >= offenseStats.fieldToGo()) {
            playGain = offenseStats.fieldToGo() - 1;
          }
          output.println(runner + " carries the ball for a " + playGain + " gain but gets stripped!");
        }
        output.print("Ball recovery by " + defense.DL(rand) + " on the ");
        printYardLine(output, offense.shor(), defense.shor(), offenseStats.fieldToGo() - playGain);
        output.println(".\n");
        offenseStats.fumbleLossUpdate();
        defenseStats.fumbleGainUpdate();
      } else {
        if (dice2Roll < 62) {
          playGain = rand.nextInt(9) - 9;
          output.println(runner + " gets caught in the back field. Fumbles the ball.");
        } else if (dice2Roll < 69) {
          playGain = rand.nextInt(7) - 7;
          output.println("Miscommunication on the call. " + offense.QB(rand));
          output.println("and " + runner + " slip on the handoff. Fumble.");
        } else {
          playGain = rand.nextInt(5) + 1;
          if (playGain >= offenseStats.fieldToGo()) {
            playGain = offenseStats.fieldToGo() - 1;
          }
          output.println(runner + " carries the ball for a " + playGain + " gain but gets stripped!");
        }
        output.println(offense.brand() + " catch a break and recover.");
      }
    } else if (diceRoll < 19) {
      playGain = rand.nextInt(8) - 8;
    } else if (diceRoll < 74) {
      playGain = rand.nextInt(5) + 1;
    } else if (diceRoll < 89 + effort + skill) {
      playGain = rand.nextInt(10) + 5;
    } else if (diceRoll < 97 + effort + skill) {
      playGain = rand.nextInt(8) + 10;
    } else {
      if (dice2Roll < 40) {
        playGain = rand.nextInt(12) + 15;
      } else if (dice2Roll < 69) {
        playGain = rand.nextInt(17) + 23;
      } else if (dice2Roll < 92) {
        playGain = rand.nextInt(22) + 30;
      } else if (dice2Roll < 98) {
        playGain = rand.nextInt(20) + 40;
      } else {
        playGain = rand.nextInt(50) + 50;
      }
    }

    if (playGain > offenseStats.fieldToGo()) { playGain = offenseStats.fieldToGo(); }
    if (diceRoll > 7) {
      if (playGain > 0) {
        output.println(runner + " runs " + direction + " for a " + playGain + " yard gain!");
        if (playGain != offenseStats.fieldToGo()) {
          output.println("The tackle made by " + defense.DL(rand) + ".");
        }
      } else {
        output.println(runner + " is brought down for a " + Math.abs(playGain) + " yard loss.");
        output.println("The tackle made by " + defense.DL(rand) + ".");
      }
    }
    return playGain;
  }

  public int sneak(Random rand, PrintStream output, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    int skill = skillDiff(offense, defense, offenseStats);
    int effort = effortDiff(offenseStats, defenseStats);
    int diceRoll = rand.nextInt(100 + skill + effort);
    int dice2Roll = rand.nextInt(100);
    int playGain;

    if (diceRoll < 20) { playGain = rand.nextInt(2) - 2;
      output.println(offense.QB(rand) + " runs up the middle for a " + Math.abs(playGain) + " loss.");
      output.println("The stop made by " + defense.DL(rand) + "."); }
    else if (diceRoll < 35) { playGain = 0;
      output.println(offense.QB(rand) + " gets stuck at the line. No gain.");
      if (dice2Roll > 30) {
        output.println(defense.DL(rand) + " with the tackle."); } }
    else {
      if (diceRoll < 45) { playGain = 1; }
      else if (diceRoll < 80) { playGain = 2; }
      else if (diceRoll < 95) { playGain = 3; }
      else { playGain = rand.nextInt(3) + 3; }
      output.println(offense.QB(rand) + " runs up the middle for a " + playGain + " gain.");
      if (dice2Roll < 65) {
        output.println(defense.DL(rand) + " with the tackle."); }
      else {
        output.println(defense.SEC(rand) + " with the tackle."); }
    }
    return playGain;
  }

  public int pass(Random rand, PrintStream output, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    int learn = 0;
    if (offenseStats.interceptionLoss() > 2 || offenseStats.turnoverLoss() > 3) {
      learn += offenseStats.turnoverLoss() - 1; }
    int skill = skillDiff(offense, defense, offenseStats);
    int effort = effortDiff(offenseStats, defenseStats);
    int diceRoll = rand.nextInt(100 + skill + effort);
    int dice2Roll = rand.nextInt(100 - learn) + learn;
    int dice3Roll = rand.nextInt(100);

    String direction = direction(rand);
    String qb = offense.QB(rand);
    String rec = offense.WR(rand);
    if (rand.nextInt(100) < 40) { offense.TE(rand); }
    String def = defense.SEC(rand);

    int playGain = 0;
    int ballReturn = 0;

    if (offenseStats.fieldToGo() < 13 && diceRoll > 20) { diceRoll -= 8; }
    else if (offenseStats.fieldToGo() < 23 && diceRoll > 20) { diceRoll -= 4; }
    if (diceRoll < 6) {
      if (dice2Roll < 65) {
        if (dice2Roll < 30) { playGain = rand.nextInt(10) + 1; }
        else { playGain = rand.nextInt(15) + 10; }
        if (playGain > offenseStats.fieldToGo()) { playGain = offenseStats.fieldToGo(); }
        output.println(qb + " throws a ball " + direction + ".");
        output.print("Intercepted by " + def + " at the ");
        printYardLine(output, offense.shor(), defense.shor(), offenseStats.fieldToGo() - playGain);
        output.println();
        int behind = 100 + playGain - offenseStats.fieldToGo();
        if (dice3Roll < 2) {
          ballReturn = rand.nextInt(45) + 25;
        } else if (dice3Roll < 11) {
          ballReturn = rand.nextInt(15) + 8;
        } else if (diceRoll < 65) {
          ballReturn = rand.nextInt(8) + 2;
        }
        if (ballReturn > behind) { ballReturn = behind; }
        playGain -= ballReturn;
        if (ballReturn != 0) {
          output.println("and returns it ball for " + ballReturn + " yards!");
        }
        output.println("Intended for " + rec + ".");
        offenseStats.interceptionLossUpdate();
        defenseStats.interceptionGainUpdate();
      } else {
        playGain = 0;
        output.println(qb + " throws a ball " + direction + ".");
        output.println("Nearly intercepted by " + def + "!");
        output.println(rec + " with turnover-saving breakup!");
      } }
    else if (diceRoll < 9) {
      playGain = rand.nextInt(9) - 9;
      offenseStats.sackLossUpdate();
      defenseStats.sackGainUpdate();
      if (dice2Roll < 30) {
        offenseStats.fumbleLossUpdate();
        defenseStats.fumbleGainUpdate();
        output.println(qb + " gets sacked and fumbles the ball!");
        output.println(defense.brand() + " recovers the ball! Turnover.");
      } else {
        output.println(qb + " is sacked for a " + Math.abs(playGain) + " yard loss.");
      }
      output.println(def + " with the sack.");
    } else if (diceRoll < 41 ) {
      playGain = 0;
      output.println(qb + " throws the ball " + direction + ".");
      output.println("Incomplete. Intended for " + rec + ".");
    } else {
      if (diceRoll < 75) { dice2Roll -= rand.nextInt(10) + 5; }
      if (dice2Roll < 34) {
        playGain = rand.nextInt(6) + 1;
      } else if (dice2Roll < 65) {
        playGain = rand.nextInt(6) + 5;
      } else if (dice2Roll < 79) {
        playGain = rand.nextInt(11) + 11;
      } else if (dice2Roll < 88) {
        playGain = rand.nextInt(17) + 17;
      } else if (diceRoll < 94) {
        playGain = rand.nextInt(20) + 28;
      } else if (diceRoll < 97) {
        playGain = rand.nextInt(20) + 45;
      } else {
        playGain = rand.nextInt(48) + 55;
      }
    }
    if (playGain > offenseStats.fieldToGo()) { playGain = offenseStats.fieldToGo(); }
    if (diceRoll > 40) {
      output.println(qb + " throws the ball " + direction + ".");
      output.println(rec + " catches the ball for a " + playGain + " yard gain!");
      if (playGain != offenseStats.fieldToGo()) {
        output.println("The tackle made by " + defense.SEC(rand) + ".");
      }
    }
    return playGain;
  }

  public int hail(Random rand, PrintStream output, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    int skill = skillDiff(offense, defense, offenseStats);
    int effort = effortDiff(offenseStats, defenseStats);
    int diceRoll = rand.nextInt(100 + skill + effort);
    int dice2Roll = rand.nextInt(80) + 50;
    double percent = dice2Roll / 100.0;
    int playGain = 0;
    String direction = direction(rand);

    if (offenseStats.fieldToGo() > 50) {
      if (diceRoll > 90 && dice2Roll > 100) {
        playGain = (int) (offenseStats.fieldToGo() * percent);
      } else {
        playGain = 0;
      }
    } else {
      if (diceRoll > 88 && dice2Roll > 100) {
        playGain = (int) (offenseStats.fieldToGo() * percent);
      } else {
        playGain = 0;
      }
    }
    if (playGain == 0) {
      output.println(offense.QB(rand) + " throws a long pass " + direction + ".");
      output.println(defense.SEC(rand) + " deflects the ball.");
      output.println("Closest to the ball was " + offense.WR(rand));
    } else {
      output.println(offense.QB(rand) + " throws a long pass " + direction);
      if (playGain > offenseStats.fieldToGo()) {
        playGain = offenseStats.fieldToGo();
        output.println("and connects with " + offense.WR(rand) + " in the endzone.");
      } else {
        output.print("and connects with " + offense.WR(rand) + " on the ");
        printYardLine(output, offense.shor(), defense.shor(), offenseStats.fieldToGo() - playGain);
        output.println(".");
      }
    }
    return playGain;
  }

  public int fGoal(Random rand, PrintStream output, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    int effort = effortDiff(offenseStats, defenseStats);
    int diceRoll = rand.nextInt(100 + offense.offenseSkill() + effort);
    int distance = offenseStats.fieldToGo() + 9 + rand.nextInt(4);
    int playGain = 0;

    output.println(offense.K() + " will now attempt a " + distance + " yard field goal.");
    if (diceRoll > 17 + 0.8 * (distance - 15)) {
      offenseStats.scoreUpdate(3);
      defenseStats.otherScoreUpdate(3);
      playGain = 1;
      output.println("Field Goal!");
    } else {
      output.print("Miss! ");
    }
    return playGain;
  }

  public int punt(Random rand, PrintStream output, Team offense, Team defense, GameStats offenseStats, GameStats defenseStats) {
    int basePunt = 37;
    int distance = rand.nextInt(23) + basePunt;
    while (distance >= offenseStats.fieldToGo()) {
      distance = rand.nextInt(23) + basePunt;
      basePunt -= 6;
    }

    if (distance > 47) { output.println(offense.P() + " punts the ball a good " + distance + " yards!"); }
    else { output.println(offense.P() + " punts the ball " + distance + " yards."); }
    output.print(defense.brand() + " will start their drive on the ");
    printYardLine(output, offense.shor(), defense.shor(), offenseStats.fieldToGo() - distance);
    output.println(".\n");
    return distance;
  }

  ///////////////////////////// Print Statements ///////////////////////////////

  private void printYardLine(PrintStream output, String offense, String defense, int fieldToGo) {
    if (fieldToGo < 50) {
      output.print(defense + " " + fieldToGo);
    } else if (fieldToGo > 50) {
      int yards = 100 - fieldToGo;
      output.print(offense + " " + yards);
    } else {
      output.print(" the '50");
    }
  }
}
