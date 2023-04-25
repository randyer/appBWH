package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.displays.Alert;
import edu.wpi.teamc.dao.displays.AlertDao;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class AlertController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;
  @FXML AnchorPane assignEmployeeAnchor;

  @FXML private MenuItem choice1;
  @FXML private MenuItem choice2;
  @FXML private MenuItem choice3;
  @FXML private MenuItem choice4;
  @FXML private MenuItem choice5;
  @FXML private MenuItem choice6;

  @FXML private MenuItem severityLow;
  @FXML private MenuItem severityMed;
  @FXML private MenuItem severityHigh;

  @FXML private MenuButton alertType;

  @FXML private MenuButton severityType;
  @FXML private TextField alertTitle;
  @FXML private TextArea alertDescription;
  @FXML private DatePicker startTime;
  @FXML private DatePicker endTime;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getSubmit(ActionEvent event) {

    LocalDateTime start = LocalDateTime.from((startTime.getValue()));
    LocalDateTime end = LocalDateTime.from((endTime.getValue()));
    String title = alertTitle.getText();
    String description = alertDescription.getText();
    String type = alertType.getText();
    Alert alert =
        new Alert(title, description, type, Timestamp.valueOf(start), Timestamp.valueOf(end));

    IDao<Alert, Integer> dao = new AlertDao();
    dao.addRow(alert);

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void setChoice1(ActionEvent event) {
    alertType.setText(choice1.getText());
  }

  @FXML
  void setChoice2(ActionEvent event) {
    alertType.setText(choice2.getText());
  }

  @FXML
  void setChoice3(ActionEvent event) {
    alertType.setText(choice3.getText());
  }

  @FXML
  void setChoice4(ActionEvent event) {
    alertType.setText(choice4.getText());
  }

  @FXML
  void setChoice5(ActionEvent event) {
    alertType.setText(choice5.getText());
  }

  @FXML
  void setChoice6(ActionEvent event) {
    alertType.setText(choice6.getText());
  }

  @FXML
  void setSeverityLow(ActionEvent event) {
    severityType.setText(severityLow.getText());
  }

  @FXML
  void setSeverityMed(ActionEvent event) {
    severityType.setText(severityMed.getText());
  }

  @FXML
  void setSeverityHigh(ActionEvent event) {
    severityType.setText(severityHigh.getText());
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {
    if (!CApp.getAdminLoginCheck()) {
      assignEmployeeAnchor.setMouseTransparent(true);
      assignEmployeeAnchor.setOpacity(0);
    }
  }
}
