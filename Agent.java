import processing.core.PApplet;

/**
 * Models an Agent in the P05 Team Party Hopping project.
 * Agents can be dragged, assigned to teams, and interact with other objects.
 */
public class Agent implements Clickable {
  protected float x;
  protected float y;
  protected int color;
  protected boolean isDragging;
  protected boolean isActive;
  protected Team team;
  protected static PApplet processing;

  public static final int DIAMETER = 40; // Standard diameter for agent rendering
  private float oldMouseX, oldMouseY;

  /**
   * Constructs an Agent with a given position, color, and Processing reference.
   *
   * @param x          Initial x position of the Agent
   * @param y          Initial y position of the Agent
   * @param color      Initial color of the Agent
   * @param processing The Processing application instance
   */
  public Agent(float x, float y, int color, PApplet processing) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.isDragging = false;
    this.isActive = false;
    Agent.processing = processing;
  }

  /**
   * Renders the agent as a circle with the appropriate color.
   */
  @Override
  public void draw() {
    processing.fill(getColor());
    processing.circle(x, y, DIAMETER);
    update();
  }

  /**
   * Determines whether the cursor is currently over this agent. Uses distance formula for accurate
   * detection.
   *
   * @return true if the mouse is within the agent's radius, false otherwise.
   */
  @Override
  public boolean isMouseOver() {
    float distance = PApplet.dist(processing.mouseX, processing.mouseY, x, y);
    return distance <= DIAMETER / 2;
  }

  /**
   * Handles mouse press events. If the agent is clicked, it becomes draggable.
   */
  @Override
  public void mousePressed() {
    if (isMouseOver()) {
      isDragging = true;
      oldMouseX = processing.mouseX;
      oldMouseY = processing.mouseY;
    }
  }

  /**
   * Handles mouse release events. Stops dragging when the mouse is released.
   */
  @Override
  public void mouseReleased() {
    isDragging = false;
  }

  /**
   * Updates the agent's position while dragging.
   */
  public void update() {
    if (isDragging) {
      float dx = processing.mouseX - oldMouseX;
      float dy = processing.mouseY - oldMouseY;
      x += dx;
      y += dy;
      oldMouseX = processing.mouseX;
      oldMouseY = processing.mouseY;
    }
  }

  /**
   * Determines the agent's color based on its status.
   * - If active: Green.
   * - If in a team: Uses team color.
   * - Otherwise: Yellow.
   *
   * @return The color the agent should be displayed with.
   */
  protected int getColor() {
    if (isActive()) {
      return processing.color(0, 255, 0); // Green when active
    } else if (team != null) {
      return team.getColor(); // Use team color if assigned
    }
    return processing.color(255, 255, 0); // Default Yellow
  }

  /**
   * Toggles the active state of the agent.
   */
  public void toggleActive() {
    isActive = !isActive;
  }

  /**
   * Returns whether the agent is currently selected.
   *
   * @return true if active, false otherwise.
   */
  public boolean isActive() {
    return isActive;
  }

  /**
   * Returns the current x-coordinate of the agent.
   *
   * @return x-coordinate of the agent.
   */
  public float getX() {
    return x;
  }

  /**
   * Returns the current y-coordinate of the agent.
   *
   * @return y-coordinate of the agent.
   */
  public float getY() {
    return y;
  }

  /**
   * Sets the agent's team.
   *
   * @param team The team to assign the agent to.
   */
  public void setTeam(Team team) {
    this.team = team;
  }

  /**
   * Returns the agent's current team.
   *
   * @return The team this agent belongs to, or null if not in a team.
   */
  public Team getTeam() {
    return team;
  }

  /**
   * Moves the agent toward a target destination if set. (Method stub for later implementation).
   */
  protected void move() {
    // To be implemented if movement logic is required
  }

  /**
   * Sets the destination coordinates of this agent to the provided values
   * and deactivates the agent.
   *
   * @param x The target x-coordinate.
   * @param y The target y-coordinate.
   */
  public void setDestination(float x, float y) {
    this.x = x;
    this.y = y;
    this.isActive = false; // Deactivate the agent upon reaching destination
  }
}


