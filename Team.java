import java.util.*;

public class Team {
  ///////////////////////////////// Constants /////////////////////////////////

  /////////////////////////////// Object Memory ///////////////////////////////
  private int teamNumber;

  private String teamShor;
  private String teamCity;
  private String teamBrand;
  private String teamName;
  private String teamDivision;
  private String teamStadium;

  private Stats teamStats;
  private Roster teamRoster;
  private int[] teamRecord;
  private boolean playoffWin;
  private boolean champion;

  private int offensePreference;
  private int offenseSkill;
  private int defenseSkill;

  //////////////////////////////// Constructor ////////////////////////////////
  public Team(Random rand, int num) {
    this.teamNumber = num;

    this.teamShor = shor[this.teamNumber];
    this.teamCity = cities[this.teamNumber];
    this.teamBrand = brands[this.teamNumber];
    this.teamName = "The " + this.teamCity + " " + this.teamBrand;
    this.teamDivision = divisions[this.teamNumber / 4];
    this.teamStadium = stadiums[this.teamNumber];

    this.teamStats = new Stats();
    this.teamRoster = new Roster(this.teamNumber);
    this.teamRecord = new int[] {0, 0};
    this.playoffWin = false;
    this.champion = false;

    this.offensePreference = rand.nextInt(10) - 5;
    this.offenseSkill = rand.nextInt(6) + rand.nextInt(6);
    this.defenseSkill = rand.nextInt(6) + rand.nextInt(6);
  }

  ////////////////////////////////// Updators /////////////////////////////////
    // Team
  public void winUpdate(int week, boolean win) {
    if (week > 10) {
      if (this.playoffWin && win) {
          this.champion = true;
          System.out.println(this.brand() + " are this year's champion.");
      }
      if (win) { this.teamRecord[0] += 1;
        this.playoffWin = true; }
      else { this.teamRecord[1] += 1; }
    } else {
      if (win) { this.teamRecord[0] += 1; }
      else { this.teamRecord[1] += 1; }
    }
  }

  public void playoffWinUpdate(boolean win) {
    if (this.playoffWin && win) {
        this.champion = true;
    }
    if (win) { this.teamRecord[0] += 1;
      this.playoffWin = true; }
    else { this.teamRecord[1] += 1; }
  }

  //////////////////////////////// Return Cues ////////////////////////////////
    // Team
      // Designation
  public int num() { return this.teamNumber; }
      // Info
  public String shor() { return this.teamShor; }
  public String city() { return this.teamCity; }
  public String brand() { return this.teamBrand; }
  public String name() { return this.teamName; }
  public String division() { return this.teamDivision; }
  public String stadium() { return this.teamStadium; }
      // Record
  public String record() { return "[" + this.teamRecord[0] + " - " + this.teamRecord[1] + "]"; }
  public int wins() { return this.teamRecord[0]; }
  public int losses() { return this.teamRecord[1]; }
  public boolean championship() { return this.playoffWin; }

    // Roster
      // Coaching
  public String HC() { return teamRoster.HeadCoach(); }
  public String OC() { return teamRoster.OffensiveCoordinator(); }
  public String DC() { return teamRoster.DefensiveCoordinator(); }
      // Offense
  public String QB(Random r) { return teamRoster.QuarterBack(r); }
  public String RB(Random r) { return teamRoster.RunningBack(r); }
  public String WR(Random r) { return teamRoster.WideReceiver(r); }
  public String TE(Random r) { return teamRoster.TightEnd(r); }
      // Defense
  public String DL(Random r) { return teamRoster.DefensiveLine(r); }
  public String SEC(Random r) { return teamRoster.Secondary(r); }
      // Specials
  public String K() { return teamRoster.Kicker(); }
  public String P() { return teamRoster.Punter(); }
  public String R(Random r) { return teamRoster.Returner(r); }

  // Stats
    // Skill Levels
  public int offensePreference() { return this.offensePreference; }
  public int offenseSkill() { return this.offenseSkill; }
  public int defenseSkill() { return this.defenseSkill; }
    // Statistics
  public Stats fullStats() { return this.teamStats; }
  public GameStats gameStats(int week) { return this.teamStats.gameStats(week); }

  ///////////////////////////// Team Information //////////////////////////////
  private String[] cities = new String[] {"St. Louis", "Washington", "New York", "Tampa Bay",
                                          "Seattle", "Dallas", "Houston", "Los Angeles"};
  private String[] brands = new String[] {"Battlehawks", "Defenders", "Guardians", "Vipers",
                                          "Dragons", "Renegades", "Roughnecks", "Wildcats"};
  private String[] shor = new String[] {"STL", "WAS", "NY", "TB", "SEA", "DAL", "HOU", "LA"};
  private String[] divisions = new String[] {"EAST", "WEST"};
  private String[] stadiums = new String[] {"The Dome at America's Center", "Audi Field", "MetLife Stadium", "Raymond James Stadium",
                                            "CenturyLink Field", "Globe Life Park", "TDECU Stadium", "Dignity Health Park"};
}
