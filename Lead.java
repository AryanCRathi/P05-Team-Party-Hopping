import processing.core.PApplet;

/**
 * Models a Team Lead agent for the CS300 P05 Team Party Hopping project.
 * Every Team can have at most one Lead, and clicking on a Lead selects ALL
 * members of the team at once.
 */
public class Lead extends Agent {

  /**
   * Constructs a new Lead agent at the given x, y coordinates.
   *
   * @param x          Initial x position of the Lead
   * @param y          Initial y position of the Lead
   * @param color      Initial color of the Lead
   * @param processing The Processing application instance
   */
  public Lead(float x, float y, int color, PApplet processing) {
    super(x, y, color, processing);
  }

  /**
   * Returns the standard diameter of an Agent.
   *
   * @return The diameter of the agent.
   */
  public static int getDiameter() {
    return DIAMETER;
  }


  /**
   * Draws the Lead agent.
   * - Renders the agent as a circle.
   * - Draws an inverted black triangle to indicate leadership.
   */
  @Override
  public void draw() {
    super.draw();
    drawTriangle();
  }

  /**
   * Draws an inverted black triangle on top of the agent.
   * Used to visually distinguish the Lead from regular agents.
   */
  private void drawTriangle() {
    processing.fill(00);
    float triHeight = DIAMETER / 3;
    float halfWidth = DIAMETER / 5;

    processing.triangle(
        x, y - triHeight,
        x - triHeight, y + halfWidth,
        x + triHeight, y + halfWidth
    );
  }

  /**
   * Handles the behavior when the Lead is clicked and released.
   * - If the Lead is clicked, all team members are selected.
   */
  @Override
  public void mouseReleased() {
    if (isMouseOver() && team != null) {
      team.selectAll();
    }
    super.mouseReleased();
  }

  /**
   * Overrides `getColor()` to ensure the Lead follows team selection rules.
   * - If the entire team is selected, it appears green.
   * - Otherwise, it uses the team's color.
   *
   * @return The color the Lead should be displayed with.
   */
  @Override
  protected int getColor() {
    if (team != null && team.isActive()) {
      return processing.color(0, 255, 0); // Green if the team is active
    }
    return super.getColor(); // Use normal team/agent color otherwise
  }

  /**
   * Checks whether this agent is the Lead of a team.
   *
   * @return true if the agent is a Lead, false otherwise.
   */
  public boolean isLead() {
    return true;
  }
}
