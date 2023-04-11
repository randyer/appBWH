package edu.wpi.teamc.navigation;

public enum Screen {
  ROOT("views/english/Root.fxml"),
  SERVICE_REQUEST("views/english/ServiceRequest.fxml"),
  SIGNAGE("views/english/SignagePage.fxml"),
  ELEVATOR_SIGNAGE("views/english/ElevatorSignage.fxml"),
  HOME("views/english/Home.fxml"),

  FLOOR_PLAN("views/english/FloorPlan.fxml"),

  LOGIN("views/english/LoginPage.fxml"),
  GUEST_HOME("views/english/GuestHome.fxml"),
  MEAL("views/english/Meal.fxml"),
  MEAL_HISTORY("views/english/MealHistory.fxml"),
  CONFERENCE("views/english/Conference.fxml"),
  CONFERENCE_HISTORY("views/english/ConferenceHistory.fxml"),

  FLOWER("views/english/Flower.fxml"),
  FURNITURE("views/english/Furniture.fxml"),
  OFFICE_SUPPLY("views/english/OfficeSupply.fxml"),
  CONGRATS_PAGE("views/english/congratsPage.fxml"),
  MAP_HISTORY_PAGE("views/english/MapChangeHistory.fxml"),
  PATHFINDING_PAGE("views/english/Pathfinding.fxml"),
  HELP("views/english/Help.fxml"),
  EDIT_MAP("views/english/MapEditing.fxml"),
  PATHFINDING("views/english/PathfindingIt1.fxml"),

  ADMIN_HOME("views/english/AdminHome.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
