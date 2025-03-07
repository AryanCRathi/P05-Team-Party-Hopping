// TODO: file header

import java.util.ArrayList;
import processing.core.PApplet;

/**
 * A short tester class for verifying some of the Agent and Team behaviors in P05.
 */
public class TeamTester {

  private static TeamManagementSystem papplet = new TeamManagementSystem();

  static {
    papplet.runSketch(new String[]{"TeamManagementSystem"}, papplet);
  }

  /**
   * Verifies that an Agent’s initial position is set correctly upon creation.
   *
   * @return true if both agents are created with correct coordinates; false otherwise
   */
  public static boolean testAgentInitialPosition() {
    Agent agent1 = new Agent(100, 200, 0xFF0000, papplet);
    Agent agent2 = new Agent(300, 400, 0x00FF00, papplet);

    return agent1.getX() == 100 && agent1.getY() == 200 &&
        agent2.getX() == 300 && agent2.getY() == 400;
  }

  /**
   * Verifies that an Agent moves correctly when given a destination.
   *
   * @return true if agent movement behavior is correct; false otherwise
   */
  public static boolean testAgentMovement() {
    Agent agent = new Agent(200, 200, 0xFF0000, papplet);

    agent.move();

    return agent.getX() > 100 && agent.getY() > 100 && agent.getX() < 200 && agent.getY() < 200;
  }

  /**
   * Verifies that an Agent without a destination remains stationary.
   *
   * @return true if agent remains stationary when no destination is set; false otherwise
   */
  public static boolean testAgentStationary() {
    Agent agent = new Agent(100, 100, 0xFF0000, papplet);
    float initialX = agent.getX();
    float initialY = agent.getY();

    agent.move();

    return agent.getX() == initialX && agent.getY() == initialY;
  }

  /**
   * Verifies that creating a Team with multiple Leads throws an IllegalStateException.
   *
   * @return true if the correct exception is thrown; false otherwise
   */
  public static boolean testMultipleLeadsException() {
    ArrayList<Agent> agents = new ArrayList<>();
    agents.add(new Lead(100, 100, 0xFF0000, papplet));
    agents.add(new Lead(200, 200, 0x00FF00, papplet));

    try {
      new Team(0x0000FF, agents);
    } catch (IllegalStateException e) {
      return true;
    }
    return false;
  }

  /**
   * Verifies behavior around empty teams.
   *
   * @return true if empty team behavior works correctly; false otherwise
   */
  public static boolean testEmptyTeam() {
    ArrayList<Agent> emptyList = new ArrayList<>();

    try {
      new Team(0xFF0000, emptyList);
      return false;
    } catch (IllegalArgumentException e) {
      // Expected exception
    }

    ArrayList<Agent> agents = new ArrayList<>();
    agents.add(new Agent(100, 100, 0xFF0000, papplet));

    Team team = new Team(0x00FF00, agents);
    team.removeMember(agents.get(0));

    return team.getTeamSize() == 0;
  }

  /**
   * Verifies that a Team can be created successfully with exactly one Lead.
   *
   * @return true if Team creation succeeds with correct composition; false otherwise
   */
  public static boolean testValidTeamCreation() {
    ArrayList<Agent> agents = new ArrayList<>();
    agents.add(new Lead(100, 100, 0xFF0000, papplet));
    agents.add(new Agent(150, 150, 0x00FF00, papplet));

    Team team = new Team(0x0000FF, agents);

    return team.getTeamSize() == 2 && team.hasLead();
  }

  /**
   * Verifies that a new Agent can be added to an existing Team.
   *
   * @return true if Agent is successfully added to Team; false otherwise
   */
  public static boolean testAddAgentToTeam() {
    ArrayList<Agent> agents = new ArrayList<>();
    agents.add(new Lead(100, 100, 0xFF0000, papplet));
    Team team = new Team(0x00FF00, agents);

    Agent newAgent = new Agent(200, 200, 0x0000FF, papplet);
    team.addMember(newAgent);

    return team.getTeamSize() == 2 && newAgent.getTeam() == team;
  }

  /**
   * Runs all tests and displays results
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("-----------------------------------------------------------");
    System.out.println("testAgentInitialPosition: " + (testAgentInitialPosition() ? "Pass" : "Failed!"));
    System.out.println("testAgentMovement: " + (testAgentMovement() ? "Pass" : "Failed!"));
    System.out.println("testAgentStationary: " + (testAgentStationary() ? "Pass" : "Failed!"));
    System.out.println("testMultipleLeadsException: " + (testMultipleLeadsException() ? "Pass" : "Failed!"));
    System.out.println("testEmptyTeam: " + (testEmptyTeam() ? "Pass" : "Failed!"));
    System.out.println("testValidTeamCreation: " + (testValidTeamCreation() ? "Pass" : "Failed!"));
    System.out.println("testAddAgentToTeam: " + (testAddAgentToTeam() ? "Pass" : "Failed!"));
  }
}
