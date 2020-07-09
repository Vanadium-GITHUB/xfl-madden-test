import java.util.*;

public class Stats {
  ///////////////////////////////// Constants /////////////////////////////////
  public final int WEEKS_IN_SEASON = 12; // temp variable
  /////////////////////////////// Object Memory ///////////////////////////////
  private GameStats[] gameStats;

  private int seasonPointsScored;
  private int seasonPointsAllowed;

  private int totalOffense;
  private int totalPassing;
  private int totalRushing;

  private int seasonPlays;
  private int seasonBigPlays;
  private int seasonRuns;
  private int seasonPasses;

  private int seasonFirstDowns;
  private int seasonThirdDownSuccs;
  private int seasonThirdDownAtts;
  private int seasonFourthDownSuccs;
  private int seasonFourthDownAtts;

  private int seasonFieldGoals;
  private int seasonFieldGoalAtts;
  private int seasonTouchdowns;

  private int seasonTurnoverLoss;
  private int seasonInterceptionLoss;
  private int seasonFumbleLoss;
  private int seasonSackLoss;
  private int seasonSafetyLoss;

  private int seasonTurnoverGain;
  private int seasonInterceptionGain;
  private int seasonFumbleGain;
  private int seasonSackGain;
  private int seasonSafetyGain;

  //////////////////////////////// Constructor ////////////////////////////////
  public Stats() {
    this.gameStats = new GameStats[WEEKS_IN_SEASON];
    for (int i = 0; i < WEEKS_IN_SEASON; i++) {
      gameStats[i] = new GameStats();
    }
    statsReset();
  }

  ////////////////////////////////// Updaters /////////////////////////////////
  public void statsReset() {
    this.seasonPointsScored = 0;
    this.seasonPointsAllowed = 0;

    this.totalOffense = 0;
    this.totalPassing = 0;
    this.totalRushing = 0;

    this.seasonPlays = 0;
    this.seasonBigPlays = 0;
    this.seasonRuns = 0;
    this.seasonPasses = 0;

    this.seasonFirstDowns = 0;
    this.seasonThirdDownSuccs = 0;
    this.seasonThirdDownAtts = 0;
    this.seasonFourthDownSuccs = 0;
    this.seasonFourthDownAtts = 0;

    this.seasonFieldGoals = 0;
    this.seasonFieldGoalAtts = 0;
    this.seasonTouchdowns = 0;

    this.seasonTurnoverLoss = 0;
    this.seasonInterceptionLoss = 0;
    this.seasonFumbleLoss = 0;
    this.seasonSackLoss = 0;

    this.seasonTurnoverGain = 0;
    this.seasonInterceptionGain = 0;
    this.seasonFumbleGain = 0;
    this.seasonSackGain = 0;
  }

  public void seasonTotals(int week) {
    statsReset();
    for (int i = 0; i < week - 1; i++) {
      GameStats gameStat = gameStats[i];
      this.seasonPointsScored += gameStat.teamScore();
      this.seasonPointsAllowed += gameStat.otherScore();

      this.totalOffense += gameStat.totalYards();
      this.totalPassing += gameStat.passYards();
      this.totalRushing += gameStat.runYards();

      this.seasonPlays += gameStat.totalPlays();
      this.seasonBigPlays += gameStat.bigPlays();
      this.seasonRuns += gameStat.runPlays();
      this.seasonPasses += gameStat.passPlays();

      this.seasonFirstDowns += gameStat.firstDowns();
      this.seasonThirdDownSuccs += gameStat.thirdDownSuccesses();
      this.seasonThirdDownAtts += gameStat.thirdDownAttempts();
      this.seasonFourthDownSuccs += gameStat.fourthDownSuccesses();
      this.seasonFourthDownAtts += gameStat.fourthDownAttempts();

      this.seasonFieldGoals += gameStat.fieldGoalSuccesses();
      this.seasonFieldGoalAtts += gameStat.fieldGoalAttempts();
      this.seasonTouchdowns += gameStat.touchdowns();

      this.seasonTurnoverLoss += gameStat.turnoverLoss();
      this.seasonInterceptionLoss += gameStat.interceptionLoss();
      this.seasonFumbleLoss += gameStat.fumbleLoss();
      this.seasonSackLoss += gameStat.sackLoss();
      this.seasonSafetyLoss += gameStat.safetyLoss();

      this.seasonTurnoverGain += gameStat.turnoverGain();
      this.seasonInterceptionGain += gameStat.interceptionGain();
      this.seasonFumbleGain += gameStat.fumbleGain();
      this.seasonSackGain += gameStat.sackGain();
      this.seasonSafetyGain += gameStat.safetyGain();
    }
  }
  //////////////////////////////// Return Cues ////////////////////////////////
  public GameStats gameStats(int week) { return this.gameStats[week - 1]; }

  public int pointsScored() { return this.seasonPointsScored; }
  public int pointsAllowed() { return this.seasonPointsAllowed; }
  public int pointsDiff() { return this.seasonPointsScored - this.seasonPointsAllowed; }

  public int totalOffense() { return this.totalOffense; }
  public int totalPassing() { return this.totalPassing; }
  public int totalRushing() { return this.totalRushing; }

  public int totalPlays() { return this.seasonPlays; }
  public int totalBigPlays() { return this.seasonBigPlays; }
  public int totalRuns() { return this.seasonRuns; }
  public int totalPasses() { return this.seasonPasses; }

  public int firstDowns() { return this.seasonFirstDowns; }
  public String thirdDownPercent() { int percent = this.seasonThirdDownSuccs * 100;
    percent = percent / this.seasonThirdDownAtts; return "" + percent + "%"; }
  public String fourthDownPercent() { int percent = this.seasonFourthDownSuccs * 100;
    percent = percent / this.seasonFourthDownAtts; return "" + percent + "%"; }

  public String fieldGoalTries() { return "" + this.seasonFieldGoals + "/" + this.seasonFieldGoalAtts; }
  public int touchdowns() { return this.seasonTouchdowns; }

  public int turnoverLoss() { return this.seasonTurnoverLoss; }
  public int interceptionLoss() { return this.seasonInterceptionLoss; }
  public int fumbleLoss() { return this.seasonFumbleLoss; }
  public int sackLoss() { return this.seasonSackLoss; }
  public int safetyLoss() { return this.seasonSafetyLoss; }

  public int turnoverGain() { return this.seasonTurnoverGain; }
  public int interceptionGain() { return this.seasonInterceptionGain; }
  public int fumbleGain() { return this.seasonFumbleGain; }
  public int sackGain() { return this.seasonSackGain; }
  public int safetyGain() { return this.seasonSafetyGain; }
  public int turnoverDiff() { return this.seasonTurnoverGain - this.seasonTurnoverLoss; }

}
