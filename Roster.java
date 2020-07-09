import java.util.*;

public class Roster {
  /////////////////////////////// Object Memory ///////////////////////////////
  private String headCoach;
  private String offensiveCoordinator;
  private String defensiveCoordinator;

  private String[] quarterBacks;
  private String[] runningBacks;
  private String[] wideRecievers;
  private String[] tightEnds;

  private String[] defensiveLine;
  private String[] secondary;

  private String kicker;
  private String punter;
  private String[] returners;

  //////////////////////////////// Constructor ////////////////////////////////
  public Roster(int teamNumber) {
    this.headCoach = headCoaches[teamNumber];
    this.offensiveCoordinator = oCoordinators[teamNumber];
    this.defensiveCoordinator = dCoordinators[teamNumber];

    this.quarterBacks = QBInitialization(teamNumber);
    this.runningBacks = RBInitialization(teamNumber);
    this.wideRecievers = WRInitialization(teamNumber);
    this.tightEnds = TEInitialization(teamNumber);

    this.defensiveLine = DLInitialization(teamNumber);
    this.secondary = SECInitialization(teamNumber);

    this.kicker = kickers[teamNumber];
    this.punter = punters[teamNumber];
    this.returners = RInitialization(teamNumber);
  }
  ////////////////////////////////// Updators /////////////////////////////////
    // Single-Members
  private String[] headCoaches = new String[] {"Jonathan Hayes", "Pep Hamilton", "Kevin Gilbride", "Marc Trestman", "Jim Zorn", "Bob Stoops", "June Jones", "Winston Moss" };
  private String[] oCoordinators = new String[] {"Chuck Long", "Tanner Engstrand", "Kevin Gilbride", "Jaime Elizondo", "Mike Riley", "Hal Mumme", "Chris Miller", "Norm Chow"};
  private String[] dCoordinators = new String[] {"Jay Hayes", "Louie Cioffi", "Jim Herrmann", "Jerry Glanville", "Clayton Lopez", "Chris Woods", "Ted Cottrell", "Pepper Johnson"};
  private String[] kickers = new String[] {"(K) Taylor Russolino", "(K) Ty Rausa", "(K) Matthew McCrane", "(K) Andrew Franks", "(K) Ernesto Lacayo", "(K) Austin MacGinnis", "(K) Sergio Castitllo", "(K) Nick Novak"};
  private String[] punters = new String[] {"(P) Marquette King", "(P) Hunter Niswander", "(P) Justin Vogel", "(P) Jake Schum", "(P) Brock Miller", "(P) Drew Galitz", "(P) Ajene Rehkow", "(P) Shane Tripuika"};

    // Multi-Members
  private String[] QBInitialization(int teamNumber) {
    if (teamNumber == 0) { return new String[] {"(QB) Jordan Ta'amu"}; }
    if (teamNumber == 1) { return new String[] {"(QB) Cardale Jones"}; }
    if (teamNumber == 2) { return new String[] {"(QB) Luis Perez"}; }
    if (teamNumber == 3) { return new String[] {"(QB) Taylor Cornelius", "(QB) Quinton Flowers"}; }
    if (teamNumber == 4) { return new String[] {"(QB) Brandon Silvers"}; }
    if (teamNumber == 5) { return new String[] {"(QB) Landry Jones"}; }
    if (teamNumber == 6) { return new String[] {"(QB) PJ Walker"}; }
    return new String[] {"(QB) Josh Johnson"};
  }

  private String[] RBInitialization(int teamNumber) {
    if (teamNumber == 0) { return new String[] {"(RB) Matt Jones", "(RB) Cristine Michael", "(RB) Keith Ford", "(RB) Lenard Tillery"}; }
    if (teamNumber == 1) { return new String[] {"(RB) Donnel Pumphrey", "(RB) Jhurrel Pressley", "(RB) Nick Brossette", "(RB) Khalid Abdullah"}; }
    if (teamNumber == 2) { return new String[] {"(RB) Tim Cook", "(RB) Darius Victor", "(RB) Justin Stockton", "(RB) Matthew Colburn"}; }
    if (teamNumber == 3) { return new String[] {"(RB) De'Veon Smith", "(RB) Jacques Patrick"}; }
    if (teamNumber == 4) { return new String[] {"(RB) Ja'Quan Gardner", "(RB) Kenneth Farrow", "(RB) Trey Williams"}; }
    if (teamNumber == 5) { return new String[] {"(RB) Cameron Artis-Payne", "(RB) Lance Dunbar", "(RB) Marquis Young"}; }
    if (teamNumber == 6) { return new String[] {"(RB) James Butler", "(RB) Andrew Williams"}; }
    return new String[] {"(RB) Elijah Hood", "(RB) Larry Rose", "(RB) Martez Carter", "(RB) DuJuan Harris"};
  }

  private String[] WRInitialization(int teamNumber) {
    if (teamNumber == 0) { return new String[] {"(WR) Keith Mumphrey", "(WR) L'Damian Washington", "(WR) Carlton Agudosi", "(WR) De'Mornay Pierson-El", "(WR) Alonzo Russell", "(WR) Brandon Reilly"}; }
    if (teamNumber == 1) { return new String[] {"(WR) DeAndrew Thompkins", "(WR) Simmie Cobbs", "(WR) Rashad Ross", "(WR) Malachi Dupre", "(WR) Eli Rogers", "(WR) Deion Holliman"}; }
    if (teamNumber == 2) { return new String[] {"(WR) Mekale McKay", "(WR) Austin Duke", "(WR) Teo Redding", "(WR) Joe Horn", "(WR) Colby Pearson"}; }
    if (teamNumber == 3) { return new String[] {"(WR) Jalen Tolliver", "(WR) Tanner McEvoy", "(WR) Reece Horn", "(WR) Ryan Davis", "(WR) Dan Williams", "(WR) SJ Green"}; }
    if (teamNumber == 4) { return new String[] {"(WR) Kasen Williams", "(WR) Austin Proehl", "(WR) Keenan Reynolds", "(WR) Dontez Byrd", "(WR) John Santiago", "(WR) Reuben Mwehla"}; }
    if (teamNumber == 5) { return new String[] {"(WR) Jeff Badet", "(WR) Freddie Martino", "(WR) Flynn Nagel", "(WR) Austin Walter", "(WR) Joshua Crockett", "(WR) Jazz Ferguson"}; }
    if (teamNumber == 6) { return new String[] {"(WR) Cam Phillips", "(WR) Blake Jackson", "(WR) Nick Holley", "(WR) Sammie Coates", "(WR) Sam Mobley"}; }
    return new String[] {"(WR) Jordan Smallwood", "(WR) Saeed Blacknall", "(WR) Adonis Jennings", "(WR) Tre McBride", "(WR) Jalen Greene", "(WR) Kermit Whitfield"};
  }

  private String[] TEInitialization(int teamNumber) {
    if (teamNumber == 0) { return new String[] {"(TE) Marcas Lucas", "(TE) Wes Saxton", "(TE) Connor Davis"}; }
    if (teamNumber == 1) { return new String[] {"(TE) Khari Lee", "(TE) Derrick Hayward", "(TE) Donnie Ernsberger"}; }
    if (teamNumber == 2) { return new String[] {"(TE) Jake Powell", "(TE) Jake Sutherland", "(TE) EJ Bibbs", "(TE) Keenen Brown"}; }
    if (teamNumber == 3) { return new String[] {"(TE) Nick Truesdell", "(TE) DeAndre Goolsby", "(TE) Colin Thompson", "(TE) Pharoah McKever"}; }
    if (teamNumber == 4) { return new String[] {"(TE) Colin Jeter", "(TE) Ben Johnson", "(TE) Evan Rodriguez", "(TE) Connor Hamlett"}; }
    if (teamNumber == 5) { return new String[] {"(TE) Sean Price", "(TE) Donald Parham", "(TE) Julian Allen"}; }
    if (teamNumber == 6) { return new String[] {"(WR) Cam Phillips", "(WR) Nick Holley", "(WR) Sammie Coates" };  }
    return new String[] {"(TE) Brandon Barnes", "(TE) De'Quan Hampton"};
    }

  private String[] DLInitialization(int teamNumber) {
    if (teamNumber == 0) { return new String[] {"(DL) Will Clarke", "(DL) Casey Sayles", "(DL) Channing Ward", "(DL) Andrew Ankrah", "(LB Terence Gavin)", "(DL) Dewayne Hendrix"}; }
    if (teamNumber == 1) { return new String[] {"(DE) Tracey Sprinkle", "(DT) Elijah Qualls", "(DE) Jay Bomley", "(LB) Jonathan Massaquoi", "(DT) Keshun Freeman", "(DT) Anthony Johnson"}; }
    if (teamNumber == 2) { return new String[] {"(DE) Benmi Rotimi", "(DT) Joey Mbu", "(DT) TJ Barnes", "(DE) Cavon Walker", "(LB) Ben Heeney", "(LB) D'Juan Hines"}; }
    if (teamNumber == 3) { return new String[] {"(DE) Obum Gwacham", "(DT) Josh Banks", "(DT) Nikita Whitlock", "(DE) Deiontrez Mount", "(LB) Lucas Wacha", "(DT) Shane Bowman"}; }
    if (teamNumber == 4) { return new String[] {"(DE) Stansly Maponga", "(DE) Marcell Frazier", "(DT) Anthony Moten", "(DT) Will Sutton", "(DE) Jacquies Smith", "(LB) Steve Johnson"}; }
    if (teamNumber == 5) { return new String[] {"(DE) Frank Alexander", "(DT) Tony Gueard", "(DE) Winston Craig", "(DT) Tomasi Laulile", "(LB) Ansantay Brown", "(LB) Greer Martini"}; }
    if (teamNumber == 6) { return new String[] {"(DE) Gabe Wright", "(DE) Cashaud Lyons", "(DE) Kony Ealy", "(DT) Corey Crawford", "(LB) Marquis Gates", "(LB) Beniquez Brown"}; }
    return new String[] {"(DE) Boogie Roberts", "(DT) Roderick Henderson", "(DE) Latarius Brady", "(LB) Tre' Williams", "(DT) Trevon Sanders", "(LB) Will Smith"};
  }

  private String[] SECInitialization(int teamNumber) {
    if (teamNumber == 0) { return new String[] {"(LB) Dexter McCoil", "(CB) David Rivers", "(CB) Darius Hillary", "(CB) Robert Nelson", "(S) Kenny Robinson", "(S) Will Hill"}; }
    if (teamNumber == 1) { return new String[] {"(LB) Scooby Wright", "(LB) Jonathan Celestin", "(CB) Elijah Campbell", "CB Des Lawrence", "(S) Shamarko Thomas", "(S) Rahim Moore"}; }
    if (teamNumber == 2) { return new String[] {"(CB) Bryce Jones", "(CB) Dravon Askew-Henrey",  "(LB) Garret Dooley", "(S) Andrew Summers", "CB Jamar Summers", "(S) AJ Hendy"}; }
    if (teamNumber == 3) { return new String[] {"(LB) Terrance Plummer", "(CB) Tarvarus McFadden", "(CB) Anthoula Kelly", "(S) Micah Hannemann", "(S) Robert Priester", "(S) Marcelis Branch"}; }
    if (teamNumber == 4) { return new String[] {"(LB) Nick Temple", "(S) Godwin Igwebuike", "(CB) Jeremy Clark", "(CB) Channing Stribling", "(S) Jordan Martin", "(LB) Kyle Quiero"}; }
    if (teamNumber == 5) { return new String[] {"(LB) Gerald Rivers", "(LB) Toebenna Okeke", "(CB) Josh Hawkins", "(CB) Treston Decoud", "(S) Tenny Adewusi", "(S) Derron Smith"}; }
    if (teamNumber == 6) { return new String[] {"(LB) Edmond Robinson", "(LB) Kaelin Burnett", "(CB) Jeremiah Johnson", "(CB) Ajene Harris", "(S) Detrick Nichols", "(S) Cody Brown"}; }
    return new String[] {"(LB) Cedric Reed", "(LB) Shawn Oakman", "(CB) Jaylen Dunlap", "(CB) Arrion Springs", "(S) Jack Tocho", "(S) Ahmad Dixon"};
  }

  private String[] RInitialization(int teamNumber) {
    if (teamNumber == 0) { return new String[] {"(WR) Keith Mumphrey", "(WR) De'Monray Pierson-El"}; }
    if (teamNumber == 1) { return new String[] {"(WR) Eli Rogers", "(WR) Deion Holliman"}; }
    if (teamNumber == 2) { return new String[] {"(WR) Austin Duke", "(RB) Justin Stockton"}; }
    if (teamNumber == 3) { return new String[] {"(WR) Ryan Davis", "(WR) Reece Horn"}; }
    if (teamNumber == 4) { return new String[] {"(WR) Keenan Reynolds", "(WR) Austin Proehl", "(WR) John Santiago"}; }
    if (teamNumber == 5) { return new String[] {"(WR) Jeff Badet", "(WR) Fynn Nage", "(RB) Marquis Young", "(WR) Austin Wait"}; }
    if (teamNumber == 6) { return new String[] {"(CB) Ajene Harris", "(RB) James Butler", "(WR) Sam Mobley"}; }
    return new String[] {"(WR) Kermit Whitfield"};
  }

  //////////////////////////////// Return Cues ////////////////////////////////
  public String HeadCoach() { return this.headCoach; }
  public String OffensiveCoordinator() { return this.offensiveCoordinator; }
  public String DefensiveCoordinator() { return this.defensiveCoordinator; }

  public String QuarterBack(Random r) { return this.quarterBacks[r.nextInt(this.quarterBacks.length)]; }
  public String RunningBack(Random r) { return this.runningBacks[r.nextInt(this.runningBacks.length)]; }
  public String WideReceiver(Random r) { return this.wideRecievers[r.nextInt(this.wideRecievers.length)]; }
  public String TightEnd(Random r) { return this.tightEnds[r.nextInt(this.tightEnds.length)]; }

  public String DefensiveLine(Random r) { return this.defensiveLine[r.nextInt(this.defensiveLine.length)]; }
  public String Secondary(Random r) { return this.secondary[r.nextInt(this.secondary.length)]; }

  public String Kicker() { return this.kicker; }
  public String Punter() { return this.punter; }
  public String Returner(Random r) { return this.returners[r.nextInt(this.returners.length)]; }
}
