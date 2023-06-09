package edu.wpi.teamc.navigation;

public enum Screen {
  // -------NON USING PAGES-------//
  ROOT("views/Root.fxml"),
  SIGN_UP("views/pages/SignUp.fxml"),

  // -------MAP & SIGNAGE-------//
  SIGNAGE("views/pages/displays/Signage.fxml"),
  GUEST_SIGNAGE("views/pages/guest/GuestElevatorSignage.fxml"),

  SCREENSAVER("views/pages/ScreenSaver.fxml"),

  MAP_HISTORY_PAGE("views/pages/map/MapHistory.fxml"),
  SIGNAGE_EDIT("views/pages/displays/EditSignage.fxml"),
  MOVE_TABLE("views/pages/map/MoveTable.fxml"),
  PATHFINDING_PAGE("views/pages/map/PathFinding.fxml"),
  GUEST_PATHFINDING_PAGE("views/pages/guest/GuestPathFinding.fxml"),
  EDIT_MAP("views/pages/map/EditMap.fxml"),
  FLOOR_PLAN("views/pages/map/FloorPlan.fxml"),

  // -------LOGIN & HOME-------//
  HOME("views/pages/Home.fxml"),
  ADMIN_HOME("views/pages/admin/AdminHome.fxml"),
  GUEST_HOME("views/pages/guest/GuestHome.fxml"),
  PATIENT_HOME("views/pages/patient/PatientHome.fxml"),
  MENU("views/components/Menu.fxml"),
  GUEST_MENU("views/pages/guest/GuestMenu.fxml"),
  ABOUT("views/pages/About.fxml"),

  // -------SERVICE REQUEST-------//
  MEAL("views/pages/requests/MealRequest.fxml"),
  CONFERENCE("views/pages/requests/Conference.fxml"),
  FLOWER("views/pages/requests/FlowerRequest.fxml"),
  FURNITURE("views/pages/requests/FurnitureRequest.fxml"),
  OFFICE_SUPPLY("views/pages/requests/OfficeSupplyRequest.fxml"),
  GIFT_BASKET("views/pages/requests/GiftBasketRequest.fxml"),

  REQUEST_HISTORY("views/pages/requests/RequestHistory.fxml"),

  // -------OTHER-------//
  CONGRATS_PAGE("views/pages/Congrats.fxml"),
  CONGRATS_SIGNUP_PAGE("views/pages/CongratsSignUp.fxml"),
  HELP("views/pages/Help.fxml"),

  GUEST_HELP("views/pages/guest/GuestHelp.fxml"),

  EXIT_PAGE("views/pages/ExitPage.fxml"),

  ALERT_REQUEST("views/pages/requests/Alert.fxml"),

  SIGNUP_PAGE("views/pages/SignUp.fxml"),

  LOGIN_TABLE("views/pages/settings/LoginTable.fxml"),
  EMPLOYEETABLE_PAGE("views/pages/settings/EmployeeTable.fxml"),
  IMPORT_EXPORT_PAGE("views/pages/settings/ImportExport.fxml"),

  PATIENTTABLE_PAGE("views/pages/settings/PatientTable.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
