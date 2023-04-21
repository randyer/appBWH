package edu.wpi.teamc.controllers.pages.guest;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GuestMenuController {

  @FXML private Pane menuPane;
  @FXML private ImageView homeButton;
  @FXML private ImageView navigationButton;
  @FXML private ImageView settingsButton;
  @FXML private ImageView helpButton;
  @FXML private ImageView exitButton;
  @FXML private ImageView logoutButton;

  @FXML private Pane homeTrigger;
  @FXML private Pane navigationTrigger;
  @FXML private Pane settingsTrigger;
  @FXML private Pane helpTrigger;

  @FXML private Pane exitTrigger;
  @FXML private Pane logoutTrigger;
  @FXML private Pane navigationPopOut;
  @FXML private Pane settingsPopOut;
  @FXML private Pane helpPopOut;

  @FXML private Pane exitPopOut;
  @FXML private Pane logoutPopOut;
  @FXML private Pane homePopOut;
  @FXML private AnchorPane basePane;

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_SIGNAGE);
  }

  @FXML
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_PATHFINDING_PAGE);
  }

  @FXML
  void getExit(ActionEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  /** Method run when controller is initialized */
  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_HELP);
  }

  @FXML
  public void initialize() {
    homeTrigger.setVisible(false);
    navigationTrigger.setVisible(false);
    settingsTrigger.setVisible(false);
    helpTrigger.setVisible(false);

    exitTrigger.setVisible(false);
    logoutTrigger.setVisible(false);
    navigationPopOut.setVisible(false);
    settingsPopOut.setVisible(false);
    helpPopOut.setVisible(false);

    exitPopOut.setVisible(false);
    logoutPopOut.setVisible(false);
    homePopOut.setVisible(false);
    // basePane.setVisible(false);
    menuPane.setVisible(true);
    TranslateTransition navigationPopOutTransition = new TranslateTransition();
    PauseTransition pause = new PauseTransition(Duration.millis(200));
    TranslateTransition serviceRequestPopOutTransition = new TranslateTransition();
    TranslateTransition settingsPopOutTransition = new TranslateTransition();
    TranslateTransition helpPopOutTransition = new TranslateTransition();
    TranslateTransition exitPopOutTransition = new TranslateTransition();
    TranslateTransition logoutPopOutTransition = new TranslateTransition();
    TranslateTransition homePopOutTransition = new TranslateTransition();

    AtomicBoolean isHovering = new AtomicBoolean(false);

    // Define a PauseTransition with a delay of 200 milliseconds
    PauseTransition delay = new PauseTransition(Duration.millis(200));

    helpTrigger.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          Navigation.navigate(Screen.GUEST_HELP);
        });
    homeTrigger.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          Navigation.navigate(Screen.GUEST_HOME);
        });
    //    settingsTrigger.addEventFilter(
    //        MouseEvent.MOUSE_CLICKED,
    //        event -> {
    //          Navigation.navigate(Screen.HELP);
    //        });
    logoutTrigger.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          Navigation.navigate(Screen.HOME);
        });
    exitTrigger.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        event -> {
          Navigation.navigate(Screen.EXIT_PAGE);
        });

    homeButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      homePopOutTransition.setDuration(Duration.millis(200));
                      homePopOutTransition.setNode(homePopOut);
                      homePopOutTransition.setToX(180);
                      homePopOutTransition.play();
                      homePopOut.setVisible(true);
                      homeTrigger.setVisible(true);
                      homeButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                homePopOutTransition.setDuration(Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOutTransition.setToX(0);
                homePopOutTransition.setOnFinished(
                    e -> {
                      if (!homePopOut.isHover() && !homeTrigger.isHover()) {
                        homePopOut.setVisible(false);
                        homeTrigger.setVisible(false);
                        homeButton.setVisible(true);
                      }
                    });
                homePopOutTransition.play();
              }
            });

    homePopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !homeButton.isHover() && !homeTrigger.isHover()) {
                homePopOutTransition.setDuration(Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOutTransition.setToX(0);
                homePopOutTransition.setOnFinished(
                    e -> {
                      if (!homePopOut.isHover() && !homeTrigger.isHover()) {
                        homePopOut.setVisible(false);
                        homeTrigger.setVisible(false);
                        homeButton.setVisible(true);
                      }
                    });
                homePopOutTransition.play();
              } else if (newValue) {
                homePopOutTransition.stop();
                homePopOut.setTranslateX(180);
              }
            });

    homeTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !homeButton.isHover() && !homePopOut.isHover()) {
                homePopOutTransition.setDuration(Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOutTransition.setToX(0);
                homePopOutTransition.setOnFinished(
                    e -> {
                      if (!homePopOut.isHover() && !homeTrigger.isHover()) {
                        homePopOut.setVisible(false);
                        homeTrigger.setVisible(false);
                        homeButton.setVisible(true);
                      }
                    });
                homePopOutTransition.play();
              } else if (newValue) {
                homePopOutTransition.setDuration(Duration.millis(200));
                homePopOutTransition.setNode(homePopOut);
                homePopOut.setTranslateX(180);
                homePopOutTransition.play();
              }
            });

    navigationButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      navigationPopOutTransition.setDuration(Duration.millis(200));
                      navigationPopOutTransition.setNode(navigationPopOut);
                      navigationPopOutTransition.setToX(180);
                      navigationPopOutTransition.play();
                      navigationPopOut.setVisible(true);
                      navigationTrigger.setVisible(true);
                      navigationButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                navigationPopOutTransition.setDuration(Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOutTransition.setToX(0);
                navigationPopOutTransition.setOnFinished(
                    e -> {
                      if (!navigationPopOut.isHover() && !navigationTrigger.isHover()) {
                        navigationPopOut.setVisible(false);
                        navigationTrigger.setVisible(false);
                        navigationButton.setVisible(true);
                      }
                    });
                navigationPopOutTransition.play();
              }
            });
    navigationPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !navigationButton.isHover() && !navigationTrigger.isHover()) {
                navigationPopOutTransition.setDuration(Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOutTransition.setToX(0);
                navigationPopOutTransition.setOnFinished(
                    e -> {
                      if (!navigationPopOut.isHover() && !navigationTrigger.isHover()) {
                        navigationPopOut.setVisible(false);
                        navigationTrigger.setVisible(false);
                        navigationButton.setVisible(true);
                      }
                    });
                navigationPopOutTransition.play();
              } else if (newValue) {
                navigationPopOutTransition.stop();
                navigationPopOut.setTranslateX(180);
              }
            });
    navigationTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !navigationButton.isHover() && !navigationPopOut.isHover()) {
                navigationPopOutTransition.setDuration(Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOutTransition.setToX(0);
                navigationPopOutTransition.setOnFinished(
                    e -> {
                      if (!navigationPopOut.isHover() && !navigationTrigger.isHover()) {
                        navigationPopOut.setVisible(false);
                        navigationTrigger.setVisible(false);
                        navigationButton.setVisible(true);
                      }
                    });
                navigationPopOutTransition.play();
              } else if (newValue) {
                navigationPopOutTransition.setDuration(Duration.millis(200));
                navigationPopOutTransition.setNode(navigationPopOut);
                navigationPopOut.setTranslateX(180);
                navigationPopOutTransition.play();
              }
            });
    settingsButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      settingsPopOutTransition.setDuration(Duration.millis(200));
                      settingsPopOutTransition.setNode(settingsPopOut);
                      settingsPopOutTransition.setToX(180);
                      settingsPopOutTransition.play();
                      settingsPopOut.setVisible(true);
                      settingsTrigger.setVisible(true);
                      settingsButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                settingsPopOutTransition.setDuration(Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOutTransition.setToX(0);
                settingsPopOutTransition.setOnFinished(
                    e -> {
                      if (!settingsPopOut.isHover() && !settingsTrigger.isHover()) {
                        settingsPopOut.setVisible(false);
                        settingsTrigger.setVisible(false);
                        settingsButton.setVisible(true);
                      }
                    });
                settingsPopOutTransition.play();
              }
            });
    settingsPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !settingsButton.isHover() && !settingsTrigger.isHover()) {
                settingsPopOutTransition.setDuration(Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOutTransition.setToX(0);
                settingsPopOutTransition.setOnFinished(
                    e -> {
                      if (!settingsPopOut.isHover() && !settingsTrigger.isHover()) {
                        settingsPopOut.setVisible(false);
                        settingsTrigger.setVisible(false);
                        settingsButton.setVisible(true);
                      }
                    });
                settingsPopOutTransition.play();
              } else if (newValue) {
                settingsPopOutTransition.stop();
                settingsPopOut.setTranslateX(180);
              }
            });
    settingsTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !settingsButton.isHover() && !settingsPopOut.isHover()) {
                settingsPopOutTransition.setDuration(Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOutTransition.setToX(0);
                settingsPopOutTransition.setOnFinished(
                    e -> {
                      if (!settingsPopOut.isHover() && !settingsTrigger.isHover()) {
                        settingsPopOut.setVisible(false);
                        settingsTrigger.setVisible(false);
                        settingsButton.setVisible(true);
                      }
                    });
                settingsPopOutTransition.play();
              } else if (newValue) {
                settingsPopOutTransition.setDuration(Duration.millis(200));
                settingsPopOutTransition.setNode(settingsPopOut);
                settingsPopOut.setTranslateX(180);
                settingsPopOutTransition.play();
              }
            });
    helpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      helpPopOutTransition.setDuration(Duration.millis(200));
                      helpPopOutTransition.setNode(helpPopOut);
                      helpPopOutTransition.setToX(180);
                      helpPopOutTransition.play();
                      helpPopOut.setVisible(true);
                      helpTrigger.setVisible(true);
                      helpButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                helpPopOutTransition.setDuration(Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOutTransition.setToX(0);
                helpPopOutTransition.setOnFinished(
                    e -> {
                      if (!helpPopOut.isHover() && !helpTrigger.isHover()) {
                        helpPopOut.setVisible(false);
                        helpTrigger.setVisible(false);
                        helpButton.setVisible(true);
                      }
                    });
                helpPopOutTransition.play();
              }
            });
    helpPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !helpButton.isHover() && !helpTrigger.isHover()) {
                helpPopOutTransition.setDuration(Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOutTransition.setToX(0);
                helpPopOutTransition.setOnFinished(
                    e -> {
                      if (!helpPopOut.isHover() && !helpTrigger.isHover()) {
                        helpPopOut.setVisible(false);
                        helpTrigger.setVisible(false);
                        helpButton.setVisible(true);
                      }
                    });
                helpPopOutTransition.play();
              } else if (newValue) {
                helpPopOutTransition.stop();
                helpPopOut.setTranslateX(180);
              }
            });
    helpTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !helpButton.isHover() && !helpPopOut.isHover()) {
                helpPopOutTransition.setDuration(Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOutTransition.setToX(0);
                helpPopOutTransition.setOnFinished(
                    e -> {
                      if (!helpPopOut.isHover() && !helpTrigger.isHover()) {
                        helpPopOut.setVisible(false);
                        helpTrigger.setVisible(false);
                        helpButton.setVisible(true);
                      }
                    });
                helpPopOutTransition.play();
              } else if (newValue) {
                helpPopOutTransition.setDuration(Duration.millis(200));
                helpPopOutTransition.setNode(helpPopOut);
                helpPopOut.setTranslateX(180);
                helpPopOutTransition.play();
              }
            });
    logoutButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      logoutPopOutTransition.setDuration(Duration.millis(200));
                      logoutPopOutTransition.setNode(logoutPopOut);
                      logoutPopOutTransition.setToX(180);
                      logoutPopOutTransition.play();
                      logoutPopOut.setVisible(true);
                      logoutTrigger.setVisible(true);
                      logoutButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                logoutPopOutTransition.setDuration(Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOutTransition.setToX(0);
                logoutPopOutTransition.setOnFinished(
                    e -> {
                      if (!logoutPopOut.isHover() && !logoutTrigger.isHover()) {
                        logoutPopOut.setVisible(false);
                        logoutTrigger.setVisible(false);
                        logoutButton.setVisible(true);
                      }
                    });
                logoutPopOutTransition.play();
              }
            });
    logoutPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !logoutButton.isHover() && !logoutTrigger.isHover()) {
                logoutPopOutTransition.setDuration(Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOutTransition.setToX(0);
                logoutPopOutTransition.setOnFinished(
                    e -> {
                      if (!logoutPopOut.isHover() && !logoutTrigger.isHover()) {
                        logoutPopOut.setVisible(false);
                        logoutTrigger.setVisible(false);
                        logoutButton.setVisible(true);
                      }
                    });
                logoutPopOutTransition.play();
              } else if (newValue) {
                logoutPopOutTransition.stop();
                logoutPopOut.setTranslateX(180);
              }
            });
    logoutTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !logoutButton.isHover() && !logoutPopOut.isHover()) {
                logoutPopOutTransition.setDuration(Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOutTransition.setToX(0);
                logoutPopOutTransition.setOnFinished(
                    e -> {
                      if (!logoutPopOut.isHover() && !logoutTrigger.isHover()) {
                        logoutPopOut.setVisible(false);
                        logoutTrigger.setVisible(false);
                        logoutButton.setVisible(true);
                      }
                    });
                logoutPopOutTransition.play();
              } else if (newValue) {
                logoutPopOutTransition.setDuration(Duration.millis(200));
                logoutPopOutTransition.setNode(logoutPopOut);
                logoutPopOut.setTranslateX(180);
                logoutPopOutTransition.play();
              }
            });
    exitButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                delay.setOnFinished(
                    event -> {
                      exitPopOutTransition.setDuration(Duration.millis(200));
                      exitPopOutTransition.setNode(exitPopOut);
                      exitPopOutTransition.setToX(180);
                      exitPopOutTransition.play();
                      exitPopOut.setVisible(true);
                      exitTrigger.setVisible(true);
                      exitButton.setVisible(false);
                    });
                delay.play();
              } else {
                delay.setOnFinished(null); // Clear the delay's onFinished event
                exitPopOutTransition.setDuration(Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOutTransition.setToX(0);
                exitPopOutTransition.setOnFinished(
                    e -> {
                      if (!exitPopOut.isHover() && !exitTrigger.isHover()) {
                        exitPopOut.setVisible(false);
                        exitTrigger.setVisible(false);
                        exitButton.setVisible(true);
                      }
                    });
                exitPopOutTransition.play();
              }
            });
    exitPopOut
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !exitButton.isHover() && !exitTrigger.isHover()) {
                exitPopOutTransition.setDuration(Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOutTransition.setToX(0);
                exitPopOutTransition.setOnFinished(
                    e -> {
                      if (!exitPopOut.isHover() && !exitTrigger.isHover()) {
                        exitPopOut.setVisible(false);
                        exitTrigger.setVisible(false);
                        exitButton.setVisible(true);
                      }
                    });
                exitPopOutTransition.play();
              } else if (newValue) {
                exitPopOutTransition.stop();
                exitPopOut.setTranslateX(180);
              }
            });
    exitTrigger
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              isHovering.set(newValue);
              if (!newValue && !exitButton.isHover() && !exitPopOut.isHover()) {
                exitPopOutTransition.setDuration(Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOutTransition.setToX(0);
                exitPopOutTransition.setOnFinished(
                    e -> {
                      if (!exitPopOut.isHover() && !exitTrigger.isHover()) {
                        exitPopOut.setVisible(false);
                        exitTrigger.setVisible(false);
                        exitButton.setVisible(true);
                      }
                    });
                exitPopOutTransition.play();
              } else if (newValue) {
                exitPopOutTransition.setDuration(Duration.millis(200));
                exitPopOutTransition.setNode(exitPopOut);
                exitPopOut.setTranslateX(180);
                exitPopOutTransition.play();
              }
            });
  }
}