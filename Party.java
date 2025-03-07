import processing.core.PApplet;
import processing.core.PImage;

/**
 * Models a Party in the P05 Team Party Hopping project.
 * Parties serve as locations where Teams can be sent.
 */
public class Party implements Clickable {
  private final float x, y; // ✅ Fix: Make x and y final
  private final PImage image; // ✅ Fix: Make image final
  private static PApplet processing;
  private TeamManagementSystem system;

  /**
   * Constructs a Party at the given position with an assigned image.
   *
   * @param x          X-coordinate of the Party
   * @param y          Y-coordinate of the Party
   * @param image      Image representing the Party
   * @param processing The Processing application instance
   * @param system     The TeamManagementSystem instance
   */
  public Party(float x, float y, PImage image, PApplet processing, TeamManagementSystem system) {
    this.x = x;
    this.y = y;
    this.image = image;
    Party.processing = processing; // Assign Processing instance
    this.system = system; // ✅ Assign the TeamManagementSystem instance
  }

  /**
   * Sets the Processing instance for rendering.
   *
   * @param p The PApplet instance of the sketch.
   */
  public static void setProcessing(PApplet p) {
    processing = p;
  }

  /**
   * Renders the Party as an image on the screen.
   */
  @Override
  public void draw() {
    if (processing == null || image == null) return; // Ensure processing is set

    processing.image(image, x, y);
  }

  /**
   * Determines whether the cursor is currently over this Party.
   * Uses image width and height to check mouse position.
   *
   * @return true if the mouse is over the Party, false otherwise.
   */
  @Override
  public boolean isMouseOver() {
    if (processing == null || image == null) return false;

    return processing.mouseX >= x && processing.mouseX <= x + image.width &&
        processing.mouseY >= y && processing.mouseY <= y + image.height;
  }

  /**
   * Handles mouse press events. Does nothing for Parties.
   */
  @Override
  public void mousePressed() {
    // This method is intentionally left empty.
  }

  /**
   * Handles mouse release events.
   * If an active team exists, send it to this Party.
   */
  @Override
  public void mouseReleased() {
    if (processing == null) return; // Ensure processing is set
    // ✅ Use the instance `system` instead of `TeamManagementSystem.getActiveTeam()`
    if (isMouseOver() && system.getActiveTeam() != null) {
      system.getActiveTeam().sendToParty(this);
    }
  }

  /**
   * Returns the X-coordinate of the Party.
   *
   * @return X-coordinate of the Party.
   */
  public float getX() {
    return x;
  }

  /**
   * Returns the Y-coordinate of the Party.
   *
   * @return Y-coordinate of the Party.
   */
  public float getY() {
    return y;
  }
}
