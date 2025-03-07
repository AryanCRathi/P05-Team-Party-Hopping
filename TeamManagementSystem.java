// TODO: add file header

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * TeamManagementSystem manages agents, teams, and party locations in the CS300 P05 Team Party
 * Hopping project. It provides interactive functionality to create, modify, and display teams
 * dynamically.
 */
public class TeamManagementSystem extends PApplet {

  // General data fields for this program
  private ArrayList<Clickable> objects; // Storage for interactive components
  private ArrayList<Team> teams;        // Storage for all Teams with at least one member
  private int bgColor;                  // The background color of the application window

  // Selection-related fields:
  private boolean isSelecting; // Indicates whether the user is currently creating a selection box
  private int selectionStartX; // The x-coordinate where the user began creating a selection box
  private int selectionStartY; // The y-coordinate where the user began creating a selection box

  public static void main(String[] args) {
    PApplet.main("TeamManagementSystem"); // Start Processing sketch
  }

  @Override
  public void settings() {
    size(800, 600); // Set application window size to 800x600
  }

  @Override
  public void setup() {
    Party.setProcessing(this);

    imageMode(CENTER);

    objects = new ArrayList<>();
    teams = new ArrayList<>();

    bgColor = color(81, 125, 168);

    // ✅ Corrected: Pass `this` as the `TeamManagementSystem` argument
    objects.add(new Party(200.0f, 150.0f, loadImage("cup.png"), this, this));
    objects.add(new Party(600.0f, 150.0f, loadImage("dice.png"), this, this));
    objects.add(new Party(400.0f, 450.0f, loadImage("ball.png"), this, this));

    // ✅ Correctly linking `Agent` to `TeamManagementSystem`
    objects.add(new Agent(width / 2.0f, height / 2.0f, color(255, 0, 0), this));
  }


  @Override
  public void draw() {
    background(bgColor);

    for (Clickable obj : objects) {
      obj.draw();
    }

    clearEmptyTeams();

    float y = 20;
    textSize(16);
    for (Team team : teams) {
      fill(team.isActive() ? color(0, 255, 0) : color(255));
      text("Team " + team.getTeamID(), 10, y);
      y += 20;
    }

    if (isSelecting) {
      drawSelectionBox();
    }
  }

  public void clearEmptyTeams() {
    teams.removeIf(team -> team.getTeamSize() == 0);
  }

  public void drawSelectionBox() {
    fill(135, 185, 201, 100);
    rectMode(CORNERS);
    rect(selectionStartX, selectionStartY, mouseX, mouseY);
  }

  @Override
  public void mousePressed() {
    for (Clickable obj : objects) {
      if (obj.isMouseOver()) {
        obj.mousePressed();
        return;
      }
    }
    isSelecting = true;
    selectionStartX = mouseX;
    selectionStartY = mouseY;
  }

  @Override
  public void mouseReleased() {
    if (isSelecting) {
      Team detected = detectTeam();
      if (detected == null) {
        createTeam(getAllSelectedAgents());
      }
      isSelecting = false;
    }

    clearEmptyTeams();

    for (Clickable obj : objects) {
      obj.mouseReleased();
    }
  }

  public Team detectTeam() {
    ArrayList<Agent> selectedAgents = getAllSelectedAgents();
    if (selectedAgents.isEmpty()) return null;

    Team firstTeam = selectedAgents.get(0).getTeam();
    for (Agent agent : selectedAgents) {
      if (agent.getTeam() != firstTeam) {
        return null;
      }
    }
    return firstTeam;
  }

  public ArrayList<Agent> getAllSelectedAgents() {
    ArrayList<Agent> agents = new ArrayList<>();

    float minX = Math.min(selectionStartX, mouseX);
    float maxX = Math.max(selectionStartX, mouseX);
    float minY = Math.min(selectionStartY, mouseY);
    float maxY = Math.max(selectionStartY, mouseY);

    for (Clickable obj : objects) {
      if (obj instanceof Agent) {
        Agent agent = (Agent) obj;
        if (agent.getX() >= minX && agent.getX() <= maxX &&
            agent.getY() >= minY && agent.getY() <= maxY) {
          agents.add(agent);
        }
      }
    }
    return agents;
  }

  public void createTeam(ArrayList<Agent> selected) {
    if (selected.isEmpty()) return;

    int teamColor = color((int) (Math.random() * 256),
        (int) (Math.random() * 256),
        (int) (Math.random() * 256));

    try {
      Team newTeam = new Team(teamColor, selected);
      teams.add(newTeam);
    } catch (IllegalStateException e) {
      // Do nothing if a team cannot be created
    }
  }

  // ✅ FIX: Implementing `getActiveTeam()` to prevent errors in `Party.java`
  public Team getActiveTeam() {
    for (Team team : teams) {
      if (team.isActive()) {
        return team;
      }
    }
    return null;
  }

  @Override
  public void keyPressed() {
    if (key == '.') {
      // ✅ FIX: Reintroducing `this` to correctly initialize Agent
      objects.add(new Agent(mouseX, mouseY, color(255, 0, 0), this));
    } else if (key == ',') {
      // ✅ FIX: Reintroducing `this` to correctly initialize Lead
      objects.add(new Lead(mouseX, mouseY, color(0, 0, 255), this));
    }
  }
}
