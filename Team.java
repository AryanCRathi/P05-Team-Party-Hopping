import java.util.ArrayList;

/**
 * Models a Team for the CS300 P05 Team Party Hopping project.
 * A team consists of multiple agents and is assigned a unique color.
 */
public class Team {
  private int color;
  private ArrayList<Agent> members;
  private final char TEAM_ID;
  private static char idGenerator = 'A';

  /**
   * Constructs a Team with a specific color and a list of agents.
   *
   * @param color  The color of the team.
   * @param agents The agents assigned to this team.
   * @throws IllegalArgumentException if the agents list is empty.
   * @throws IllegalStateException if more than one Lead is included.
   */
  public Team(int color, ArrayList<Agent> agents) {
    if (agents.isEmpty()){
      throw new IllegalArgumentException("A team must have at least one agent in it.");
    }

    int leadCount = 0;
    for (Agent agent : agents){
      if (agent instanceof Lead){
        leadCount++;
      }
    }

    if (leadCount > 1) {
      throw new IllegalStateException("A team can have only one Lead.");
    }

    this.color = color;
    this.members = new ArrayList<>(agents);
    this.TEAM_ID = idGenerator++;

    for (Agent agent : members){
      agent.setTeam(this);
    }
  }

  /**
   * Returns the color assigned to this team.
   *
   * @return The team's color.
   */
  public int getColor() {
    return color;
  }

  /**
   * Returns the unique team ID character.
   *
   * @return The unique identifier for this team.
   */
  public char getTeamID() {
    return TEAM_ID;
  }

  /**
   * Returns the number of agents in the team.
   *
   * @return The number of agents in the team.
   */
  public int getTeamSize() {
    return members.size();
  }

  /**
   * Checks if this team has a Lead.
   *
   * @return true if there is a Lead, false otherwise.
   */
  public boolean hasLead() {
    for (Agent agent : members) {
      if (agent instanceof Lead) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks whether ALL members of this team are currently selected (active).
   *
   * @return true if all members are active, false otherwise.
   */
  public boolean isActive() {
    for (Agent agent : members) {
      if (!agent.isActive()) {
        return false;
      }
    }
    return true; // All members are active
  }

  /**
   * Selects all members of this team, making them active.
   */
  public void selectAll() {
    for (Agent agent : members) {
      agent.toggleActive();
    }
  }

  /**
   * Adds an agent to this team.
   *
   * @param agent The agent to add.
   * @throws IllegalStateException if adding a second Lead.
   */
  public void addMember(Agent agent) {
    if (agent instanceof Lead && hasLead()) {
      throw new IllegalStateException("This team already has a Lead.");
    }

    agent.setTeam(this);
    members.add(agent);
  }

  /**
   * Removes an agent from this team.
   *
   * @param agent The agent to remove.
   * @return true if successfully removed, false otherwise.
   */
  public boolean removeMember(Agent agent) {
    if (members.remove(agent)) {
      agent.setTeam(null); // Remove the agent's reference to this team
      return true;
    }
    return false;
  }

  /**
   * Moves the entire team to the specified Party location.
   * Keeps the relative formation while moving.
   *
   * @param party The destination party.
   */
  public void sendToParty(Party party) {
    float centerX = party.getX();
    float centerY = party.getY();

    float offsetX = centerX - getCenterX();
    float offsetY = centerY - getCenterY();

    for (Agent agent : members) {
      agent.setDestination(agent.getX() + offsetX, agent.getY() + offsetY);
    }
  }

  /**
   * Returns the center X-coordinate of the team.
   * Defined as halfway between the leftmost and rightmost agents.
   *
   * @return The calculated X center of the team.
   */
  public float getCenterX() {
    if (members.isEmpty()) return 0;

    float minX = Float.MAX_VALUE;
    float maxX = Float.MIN_VALUE;

    for (Agent agent : members) {
      float x = agent.getX();
      if (x < minX) minX = x;
      if (x > maxX) maxX = x;
    }
    return (minX + maxX) / 2;
  }

  /**
   * Returns the center Y-coordinate of the team.
   * Defined as halfway between the topmost and bottommost agents.
   *
   * @return The calculated Y center of the team.
   */
  public float getCenterY() {
    if (members.isEmpty()) return 0;

    float minY = Float.MAX_VALUE;
    float maxY = Float.MIN_VALUE;

    for (Agent agent : members) {
      float y = agent.getY();
      if (y < minY) minY = y;
      if (y > maxY) maxY = y;
    }
    return (minY + maxY) / 2;
  }
}
