import java.util.*;

public class GameStats {
  /////////////////////////////// Object Memory ///////////////////////////////
  public int QUARTER;
    // Game Totals
  private int[] score;
  private int otherScore;
  private int timeOfPosession; //

  private int drives; //
  private Queue<Integer> startingPositions; //

  private int[] totalYards; //
  private int[] passYards; //
  private int[] runYards; //

  private int[] totalPlays; //
  private int[] bigPlays; //
  private int[] lossPlays; //
  private int[] passPlays; //
  private int[] runPlays; //

  private int[] firstDowns; //
  private int[] thirdDownSuccesses; //
  private int[] thirdDownAttempts; //
  private int[] fourthDownSuccesses; //
  private int[] fourthDownAttempts; //

  private int punts; //
  private Queue<Integer> puntLengths; //
  private int fieldGoalSuccesses; //
  private int fieldGoalAttempts; //
  private int touchdowns;

  private int turnoverLoss;
  private int interceptionLoss;
  private int fumbleLoss;
  private int safetyLoss;
  private int sackLoss;

  private int turnoverGain;
  private int interceptionGain;
  private int fumbleGain;
  private int safetyGain;
  private int sackGain;

    // Drive Totals
  private int driveTime; //

  private int driveYards; //
  private int fieldToGo; //

  private int drivePlays; //
  private int driveRuns; //
  private int drivePasses; //

  private int down; //
  private int yardsToGo; //
  private int playGain; //

  private boolean redzone; //
  private boolean scoring; //
  private boolean turnover; //
  private boolean drive; //

  //////////////////////////////// Constructor ////////////////////////////////
  public GameStats() {
    // Game
    this.QUARTER = 0;
    this.score = new int[5];
    this.otherScore = 0;
    this.timeOfPosession = 0;

    this.drives = 0;
    this.startingPositions = new LinkedList<>();

    this.totalYards = new int[4];
    this.passYards = new int[4];
    this.runYards = new int[4];

    this.totalPlays  = new int[4];
    this.bigPlays = new int[4];
    this.lossPlays = new int[4];
    this.passPlays = new int[4];
    this.runPlays = new int[4];

    this.firstDowns = new int[4];
    this.thirdDownSuccesses = new int[4];
    this.thirdDownAttempts = new int[4];
    this.fourthDownSuccesses = new int[4];
    this.fourthDownAttempts = new int[4];

    this.punts = 0;
    this.puntLengths = new LinkedList<>();
    this.fieldGoalSuccesses = 0;
    this.fieldGoalAttempts = 0;
    this.touchdowns = 0;

    this.turnoverLoss = 0;
    this.interceptionLoss = 0;
    this.fumbleLoss = 0;
    this.sackLoss = 0;
    this.safetyLoss = 0;

    this.turnoverGain = 0;
    this.interceptionGain = 0;
    this.fumbleGain = 0;
    this.sackGain = 0;
    this.safetyGain = 0;

    newDrive();
  }

  ////////////////////////////////// Updators /////////////////////////////////
  public void newDrive() {
    this.driveTime = 0;

    this.driveYards = 0;
    this.fieldToGo = 100;

    this.drivePlays = 0;
    this.driveRuns = 0;
    this.drivePlays = 0;

    this.down = 1;
    this.yardsToGo = 10;
    this.playGain = 0;

    this.redzone = false;
    this.scoring = false;
    this.turnover = false;
    this.drive = true;
  }

  public void newDrive(int position) {
    this.startingPositions.add(position);

    this.drives += 1;
    this.driveTime = 0;

    this.driveYards = 0;
    this.fieldToGo = 100 - position;

    this.drivePlays = 0;
    this.driveRuns = 0;
    this.drivePasses = 0;

    this.down = 1;
    this.yardsToGo = 10;
    this.playGain = 0;

    if (this.fieldToGo < 21 && this.fieldToGo > 0) { this.redzone = true; }
    else { this.redzone = false; }
    this.scoring = false;
    this.turnover = false;
    this.drive = true;
  }

  public void playUpdate(int type, int gain, int time, GameStats defense) {
    this.playGain = gain;
    // Clock
    this.driveTime += time;
    timeOPUpdate(time);

    if (fieldToGo < 21) { this.redzone = true; }
    if (this.turnover == true) { this.drive = false; }

    // Field Goals & Punts
    if (type > 4) {
      this.drive = false;
      if (type == 5) {
        this.fieldGoalAttempts += 1;
        if (playGain != 0) {
          this.fieldGoalSuccesses += 1;
          this.scoring = true; }
        else { this.turnover = true; }
      } else if (type == 6) {
        this.punts += 1;
        this.fieldToGo -= gain;
        this.puntLengths.add(gain);
        this.turnover = true; }
    } else {
      this.totalPlays[this.QUARTER] += 1;
      this.drivePlays += 1;

      this.fieldToGo -= gain;
      this.yardsToGo -= gain;
      this.driveYards += gain;
      this.totalYards[this.QUARTER] += gain;

      if (gain > 19) { this.bigPlays[this.QUARTER] += 1; }
      if (gain < 0) { this.lossPlays[this.QUARTER] += 1; }
      if (down == 3) {
        this.thirdDownAttempts[this.QUARTER] += 1;
        if (yardsToGo <= 0) { this.thirdDownSuccesses[this.QUARTER] += 1; } }
      if (down == 4) { this.fourthDownAttempts[this.QUARTER] += 1;
        if (yardsToGo <= 0) { this.fourthDownSuccesses[this.QUARTER] += 1; } }

      if (type == 1 || type == 2) {
        this.driveRuns += 1;
        this.runPlays[this.QUARTER] += 1;
        this.runYards[this.QUARTER] += gain;
      } else if (type == 3 || type == 4) {
        this.drivePasses += 1;
        this.passPlays[this.QUARTER] += 1;
        this.passYards[this.QUARTER] += gain;
      }
    }
    if (this.drive == true) { this.downUpdate(); }
  }

  public void downUpdate() {
    if (yardsToGo < 1) {
      this.down = 1;
      this.yardsToGo = 10;
      this.firstDowns[this.QUARTER] += 1;
      if (this.fieldToGo < 10) { this.yardsToGo = this.fieldToGo; }
    } else { this.down += 1; }
    if (down > 4) { this.drive = false; this.turnover = true; }
  }

  public void gainUpdate(int gain) { this.playGain = gain; }

  public void quarterUpdate(int quarter) { this.QUARTER = quarter - 1; }
  public void timeOPUpdate(int time) { this.timeOfPosession += time; }
  public void setFieldToGo(int yards) { this.fieldToGo = yards; }

  public void scoreUpdate(int points) { this.score[this.QUARTER] += points; }
  public void otherScoreUpdate(int points) { this.otherScore += points; }
  public void touchdownUpdate() { this.touchdowns += 1; }
  public void driveUpdate(boolean boo) { this.drive = boo; }
  public void scoringUpdate(boolean boo) { this.scoring = boo; this.drive = boo; }

  public void turnoverUpdate(boolean boo) { this.turnover = boo; }
  public void interceptionLossUpdate() { this.turnoverLoss +=1; this.interceptionLoss += 1; this.turnover = true; this.drive = false; }
  public void fumbleLossUpdate() { this.turnoverLoss += 1; this.fumbleLoss += 1; this.turnover = true; this.drive = false; }
  public void safetyLossUpdate() { this.turnoverLoss += 1; this.safetyLoss += 1; this.drive = false; this.turnover = false; }
  public void sackLossUpdate() { this.sackLoss += 1; }

  public void interceptionGainUpdate() { this.turnoverGain += 1; this.interceptionGain += 1; }
  public void fumbleGainUpdate() { this.turnoverGain += 1; this.fumbleGain += 1; }
  public void safetyGainUpdate() { this.turnoverGain += 1; this.safetyGain += 1; }
  public void sackGainUpdate() { this.sackGain += 1; }

  //////////////////////////////// Return Cues ////////////////////////////////

  public int teamScore() { int points = 0;
    for (int i = 0; i <= QUARTER; i++) { points += this.score[i]; } return points; }
  public int otherScore() { return this.otherScore; }
  public String quarterScores() { String str = "[";
    for (int points : this.score) {
      if (points < 10) {
        str += "  " + points;
      } else {
        str += " " + points;
      }
    } return str + " ]"; }

  public String timeOfPosession() {
    int minutes = this.timeOfPosession / 60; int seconds = this.timeOfPosession % 60;
    if (seconds < 10) { return "" + minutes + ":0" + seconds; }
    return "" + minutes + ":" + seconds; }
  public double startAverage() { int total = 0;
    for (int i = 0; i < startingPositions.size(); i++) { int num = startingPositions.remove();
      total += num; startingPositions.add(num); }
    int average = total * 10 / startingPositions.size();
    return (double) (average / 10); }

  public String quarterTotalYards() { String str = "[";
    for (int yards : this.totalYards) {
      if (yards < 100) {
        str += "  " + yards;
      } else {
        str += " " + yards;
      }
    } return str + " ]"; }
  public int totalYards() { int yards = 0;
    for (int i = 0; i < QUARTER; i++) { yards += this.totalYards[i]; } return yards; }
  public int quarterPassYards() { return this.passYards[QUARTER]; }
  public int passYards() { int yards = 0;
    for (int i = 0; i < QUARTER; i++) { yards += this.passYards[i]; } return yards; }
  public int quarterRunYards() { return this.runYards[QUARTER]; }
  public int runYards() { int yards = 0;
    for (int i = 0; i < QUARTER; i++) { yards += this.runYards[i]; } return yards; }

  public int quarterTotalPlays() { return this.totalPlays[QUARTER]; }
  public int totalPlays() { int plays = 0;
    for (int i = 0; i < QUARTER; i++) { plays += this.totalPlays[i]; } return plays; }
  public int quarterBigPlays() { return this.bigPlays[QUARTER]; }
  public int bigPlays() { int plays = 0;
    for (int i = 0; i < QUARTER; i++) { plays += this.bigPlays[i]; } return plays; }
  public int quarterLossPlays() { return this.lossPlays[QUARTER]; }
  public int lossPlays() { int plays = 0;
    for (int i = 0; i < QUARTER; i++) { plays += this.lossPlays[i]; } return plays; }
  public int quarterPassPlays() { return this.passPlays[QUARTER]; }
  public int passPlays() { int plays = 0;
    for (int i = 0; i < QUARTER; i++) { plays += this.passPlays[i]; } return plays; }
  public int quarterRunPlays() { return this.runPlays[QUARTER]; }
  public int runPlays() { int plays = 0;
    for (int i = 0; i < QUARTER; i++) { plays += this.runPlays[i]; } return plays; }

  public int quarterFirstDowns() { return this.firstDowns[QUARTER]; }
  public int firstDowns() { int downs = 0;
    for (int i = 0; i < QUARTER; i++) { downs += this.firstDowns[i]; } return downs; }
  public int thirdDownSuccesses() { int downs = 0;
    for (int i = 0; i < QUARTER; i++) { downs += this.thirdDownSuccesses[i]; } return downs; }
  public int thirdDownAttempts() { int downs = 0;
    for (int i = 0; i < QUARTER; i++) { downs += this.thirdDownAttempts[i]; } return downs; }
  public String quarterThirdDownPercent() {
    if (this.thirdDownAttempts[QUARTER] == 0) { return "-"; }
    int percent = this.thirdDownSuccesses[QUARTER] * 100 / this.thirdDownAttempts[QUARTER];
    return "" + percent + "%"; }
  public String thirdDownPercent() { int succs = 0; int atts = 0;
    for (int i = 0; i < QUARTER; i++) { succs += this.thirdDownSuccesses[i]; }
    for (int i = 0; i < QUARTER; i++) { atts += this.thirdDownAttempts[i]; }
    int num = succs * 100 / atts;
    return "" + num + "%"; }
    public int fourthDownSuccesses() { int downs = 0;
      for (int i = 0; i < QUARTER; i++) { downs += this.fourthDownSuccesses[i]; } return downs; }
    public int fourthDownAttempts() { int downs = 0;
      for (int i = 0; i < QUARTER; i++) { downs += this.fourthDownAttempts[i]; } return downs; }
  public String quarterFourthDownPercent() {
    if (this.fourthDownAttempts[QUARTER] == 0) { return "-"; }
    int percent = this.fourthDownSuccesses[QUARTER] * 100 / this.fourthDownAttempts[QUARTER];
    return "" + percent + "%"; }
  public String fourthDownPercent() { int succs = 0; int atts = 0;
    for (int i = 0; i < QUARTER; i++) { succs += this.fourthDownSuccesses[i]; }
    for (int i = 0; i < QUARTER; i++) { atts += this.fourthDownAttempts[i]; }
    int num = succs * 100 / atts;
    return "" + num + "%"; }

  public int punts() { return this.punts; }
  public double puntAverages() { int total = 0;
    for (int i = 0; i < this.puntLengths.size(); i++) { int num = this.puntLengths.remove();
    total += num; this.puntLengths.add(num); }
    int average = total * 10 / puntLengths.size();
    return (double) (average / 10); }
  public int fieldGoalSuccesses() { return this.fieldGoalSuccesses; }
  public int fieldGoalAttempts() { return this.fieldGoalAttempts; }
  public String fieldGoalPercent() {
    int num = this.fieldGoalSuccesses * 100 / this.fieldGoalAttempts;
    return "" + num +"%"; }
  public int touchdowns() { return this.touchdowns; }

  public int turnoverLoss() { return this.turnoverLoss; }
  public int interceptionLoss() { return this.interceptionLoss; }
  public int fumbleLoss() { return this.fumbleLoss; }
  public int safetyLoss() { return this.safetyLoss; }
  public int sackLoss() { return this.sackLoss; }

  public int turnoverGain() { return this.turnoverGain; }
  public int interceptionGain() { return this.interceptionGain; }
  public int fumbleGain() { return this.fumbleGain; }
  public int safetyGain() { return this.safetyGain; }
  public int sackGain() { return this.sackGain; }

  // Drive Returns
  public String driveTime() {
    int minutes = this.driveTime / 60; int seconds = this.driveTime % 60;
    if (seconds > 9) { return "" + minutes + ":" + seconds; }
    else { return "" + minutes + ":0" + seconds; } }
  public int driveYards() { return this.driveYards; }
  public int fieldToGo() { return this.fieldToGo; }

  public int drivePlays() { return this.drivePlays; }
  public int driveRuns() { return this.driveRuns; }
  public int drivePasses() { return this.drivePasses; }

  public int down() { return this.down; }
  public int yardsToGo() { return this.yardsToGo; }
  public int playGain() { return this.playGain; }

  public boolean redzone() { return this.redzone; }
  public boolean scoring() { return this.scoring; }
  public boolean turnover() { return this.turnover; }
  public boolean drive() { return this.drive; }
}
