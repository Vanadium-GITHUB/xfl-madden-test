import java.util.*;
import java.io.*;

public class Season {
  /////////////////////////////// Object Memory ///////////////////////////////
  public int NUMBER_OF_TEAMS;
  public int WEEKS_IN_SEASON;
  public int week;

  private Team[] teams;
  private PrintStream output;

  public boolean playoffs;
  private Team[] west;
  private Team[] east;

  //////////////////////////////// Constructor ////////////////////////////////
  public Season(Random rand) {
    this.NUMBER_OF_TEAMS = 8;
    this.WEEKS_IN_SEASON = 10;
    this.week = 0;
    this.playoffs = false;
    this.teams = new Team[NUMBER_OF_TEAMS];
    for (int i = 0; i < NUMBER_OF_TEAMS; i++) {
      this.teams[i] = new Team(rand, i);
    }

    this.east = new Team[NUMBER_OF_TEAMS / 2];
    this.west = new Team[NUMBER_OF_TEAMS / 2];


    if(Madden.GAME_OUTPUT) {
      try { this.output = new PrintStream(new FileOutputStream("SeasonStandings.txt")); }
      catch (Exception error) { this.output = new PrintStream(System.out); }
    }

    week1(rand);
    week2(rand);
    week3(rand);
    week4(rand);
    week5(rand);
    week6(rand);
    week7(rand);
    week8(rand);
    week9(rand);
    week10(rand);

    regular(rand);
    playoffs(rand);
    championship(rand);
  }

  private void weekUpdate() {
    this.week += 1;
    System.out.println("Simulating Week " + week + "....");
    //try { Thread.sleep(1500); } catch (Exception e) { }
  }

  private void week1(Random rand) {
    // Game (seed, week, away, home)
    weekUpdate();
    new Game(rand, week, teams[4], teams[1]);
    new Game(rand, week, teams[7], teams[6]);
    new Game(rand, week, teams[3], teams[2]);
    new Game(rand, week, teams[0], teams[5]);
  }

  private void week2(Random rand) {
    weekUpdate();
    new Game(rand, week, teams[2], teams[1]);
    new Game(rand, week, teams[3], teams[4]);
    new Game(rand, week, teams[5], teams[7]);
    new Game(rand, week, teams[0], teams[6]);
  }

  private void week3(Random rand) {
    weekUpdate();
    new Game(rand, week, teams[6], teams[3]);
    new Game(rand, week, teams[5], teams[4]);
    new Game(rand, week, teams[2], teams[0]);
    new Game(rand, week, teams[1], teams[7]);
  }

  private void week4(Random rand) {
    weekUpdate();
    new Game(rand, week, teams[7], teams[2]);
    new Game(rand, week, teams[4], teams[0]);
    new Game(rand, week, teams[6], teams[5]);
    new Game(rand, week, teams[1], teams[3]);
  }

  private void week5(Random rand) {
    weekUpdate();
    new Game(rand, week, teams[4], teams[6]);
    new Game(rand, week, teams[2], teams[5]);
    new Game(rand, week, teams[0], teams[1]);
    new Game(rand, week, teams[3], teams[7]);
  }

  private void week6(Random rand) {
    weekUpdate();
    new Game(rand, week, teams[6], teams[2]);
    new Game(rand, week, teams[0], teams[3]);
    new Game(rand, week, teams[5], teams[1]);
    new Game(rand, week, teams[7], teams[4]);
  }

  private void week7(Random rand) {
     weekUpdate();
     new Game(rand, week, teams[5], teams[3]);
     new Game(rand, week, teams[7], teams[0]);
     new Game(rand, week, teams[2], teams[4]);
     new Game(rand, week, teams[1], teams[6]);
  }

  private void week8(Random rand) {
    weekUpdate();
    new Game(rand, week, teams[3], teams[1]);
    new Game(rand, week, teams[0], teams[2]);
    new Game(rand, week, teams[6], teams[7]);
    new Game(rand, week, teams[4], teams[5]);
  }

  private void week9(Random rand) {
    weekUpdate();
    new Game(rand, week, teams[5], teams[6]);
    new Game(rand, week, teams[1], teams[2]);
    new Game(rand, week, teams[3], teams[0]);
    new Game(rand, week, teams[4], teams[7]);
  }

  private void week10(Random rand) {
    weekUpdate();
    new Game(rand, week, teams[7], teams[5]);
    new Game(rand, week, teams[6], teams[4]);
    new Game(rand, week, teams[1], teams[0]);
    new Game(rand, week, teams[2], teams[3]);
  }

  private void playoffs(Random rand) {
    weekUpdate();
    this.playoffs = true;
    // Sort Divisions by
    new Game(rand, week, east[1], east[0]);
    new Game(rand, week, west[1], west[0]);
  }

  private void championship(Random rand) {
    weekUpdate();
    //
    Team westTeam = this.west[0];
    Team eastTeam = this.east[0];

    for (int i = 0; i < this.west.length; i++) {
      if (this.west[i].championship()) {
        westTeam = this.west[i];
      }
    }

    for (int i = 0; i < this.east.length; i++) {
      if (this.east[i].championship()) {
        eastTeam = this.east[i];
      }
    }

    if (westTeam.wins() > eastTeam.wins()) {
      new Game(rand, week, eastTeam, westTeam);
    } else {
      new Game(rand, week, westTeam, eastTeam);
    }
    System.out.println(" End of season.");
  }

  private void teamRanking(Random rand) {
    for (int i = 0; i < NUMBER_OF_TEAMS; i++) {
      if (i <= 3) {
        this.east[i] = this.teams[i];
      } else {
        this.west[i - 4] = this.teams[i];
      }
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < this.east.length - 1; j++) {
        if (this.east[j].wins() < this.east[j+1].wins()) {
          Team temp = this.east[j+1];
          this.east[j+1] = this.east[j];
          this.east[j] = temp;
        } else if (this.east[j].wins() == this.east[j+1].wins()) {
          Stats stats = this.east[j].fullStats();
          stats.seasonTotals(this.week);
          Stats stats2 = this.east[j+1].fullStats();
          stats2.seasonTotals(this.week);
          if (stats.pointsDiff() < stats2.pointsDiff()) {
            Team temp = this.east[j+1];
            this.east[j+1] = this.east[j];
            this.east[j] = temp;
          }
        }
      }
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < this.west.length - 1; j++) {
        if (this.west[j].wins() < this.west[j+1].wins()) {
          Team temp = this.west[j+1];
          this.west[j+1] = this.west[j];
          this.west[j] = temp;
        } else if (this.west[j].wins() == this.west[j+1].wins()) {
          Stats stats = this.west[j].fullStats();
          stats.seasonTotals(this.week);
          Stats stats2 = this.west[j+1].fullStats();
          stats2.seasonTotals(this.week);
          if (stats.pointsDiff() < stats2.pointsDiff()) {
            Team temp = this.west[j+1];
            this.west[j+1] = this.west[j];
            this.west[j] = temp;
          }
        }
      }
    }
  }

  ///////////////////////////// Print Statements ///////////////////////////////
  private void regular(Random rand) {
    teamRanking(rand);
    System.out.println("\n End of regular season.");
    seasonStats("Team Standings:");
  }

  private void seasonStats(String str) {
    output.println(str + "\n");

    output.println("\nXFL East");
    for (Team team : this.east) {
      output.println("  " + team.record() + " " + team.name());
      output.println();
    }

    output.println("\nXFL West");
    for (Team team : this.west) {
      output.println("  " + team.record() + " " + team.name());
      output.println();
    }

    output.println("\nFull Season Statistics");

    output.println("\nXFL East");
    for (Team team : this.east) {
      printStats(team);
    }

    output.println("\nXFL West");
    for (Team team : this.west) {
      printStats(team);
    }
  }

  private void printStats(Team team) {
    Stats stats = team.fullStats();
    stats.seasonTotals(this.week);
    output.println("  " + team.record() + " " + team.name());
    //output.println("          Offense Rating:     " + team.offenseSkill());
    //output.println("          Defense Rating:     " + team.defenseSkill());
    output.println("          Point Diff:         " + stats.pointsDiff());
    output.println("          Turnover Diff       " + stats.turnoverDiff());
    output.println("       Offense:");
    output.println("        - Points Scored:      " + stats.pointsScored());
    output.println("          Touchdowns:         " + stats.touchdowns());
    output.println("          Field Goals:        " + stats.fieldGoalTries());
    output.println("        - Total Offense:      " + stats.totalOffense() + " yds");
    output.println("          Passing Yards:      " + stats.totalPassing() + " yds");
    output.println("          Rushing Yards:      " + stats.totalRushing() + " yds");
    output.println("        - Total Plays:        " + stats.totalPlays());
    output.println("          20+ YDS Plays:      " + stats.totalBigPlays());
    output.println("          Pass Plays:         " + stats.totalPasses());
    output.println("          Run Plays:          " + stats.totalRuns());
    output.println("        - First Downs:        " + stats.firstDowns());
    output.println("          Third Down Pct:     " + stats.thirdDownPercent());
    output.println("          Fourth Down Pct:    " + stats.fourthDownPercent());
    output.println("       Defence:");
    output.println("        - Points Allowed:     " + stats.pointsAllowed());
    output.println("          Sacks:              " + stats.sackGain());
    output.println("        - Turnovers Forced:   " + stats.turnoverGain());
    output.println("          Interceptions:      " + stats.interceptionGain());
    output.println("          Fumbles:            " + stats.fumbleGain());
    int safeties = stats.turnoverGain() - stats.fumbleGain() - stats.interceptionGain();
    output.println("          Safeties:           " + safeties);
    output.println();
  }
}
