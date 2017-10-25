/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class MellowScheduler extends Application {

    public GridPane startGridBuilder(GridPane grid, Stage primaryStage) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Mellow Scheduler");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //Create Employee button
        Button employeeButton = new Button("Employees");
        
        //Create Schedule button
        Button scheduleButton = new Button("Schedules");
        
        //Make HBox to hold buttons and place them in horizontal row.
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(employeeButton);
        hbBtn.getChildren().add(scheduleButton);
        grid.add(hbBtn, 1, 4);

        //Text to be displayed. Will do nothing for now.
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //Handler for Employee button.
        employeeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                swapScene(sceneNames.NEW_EMPLOYEE, primaryStage);
            }
        });
        
        //Handler for button.
        scheduleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            //TODO: Implement this when it's time for the scheduler page
            public void handle(ActionEvent e) {
                //swapScene(sceneNames.NEW_EMPLOYEE, primaryStage);
            }
        });

        return grid;
    }

    public GridPane employeeGridBuilder(GridPane grid, Stage primaryStage) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("New Employee");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        //Make sure grid has enough rows to accomodate the fields for the employee
        //Place scene title in 4th column, 1st row with span of 1 cell right and down
        grid.add(scenetitle, 4, 0, 1, 1);
        
        //Make vertical boxes to help align text fields and descriptive test
        VBox fieldDescription = new VBox();
        fieldDescription.setAlignment(Pos.TOP_LEFT);
        fieldDescription.setPadding(new Insets(5, 0, 5, 0));
        
        VBox textFields = new VBox();
        textFields.setAlignment(Pos.TOP_RIGHT);
        textFields.setPadding(new Insets(5, 0, 5, 0));
        
        
        //Employee first name
        Label firstName = new Label("Employee First Name:");
        fieldDescription.getChildren().add(firstName);

        TextField firstNameTextField = new TextField();
        textFields.getChildren().add(firstNameTextField);

        //Employee last name
        Label lastName = new Label("Employee Last Name:");
        fieldDescription.getChildren().add(lastName);

        TextField lastNameTextField = new TextField();
        textFields.getChildren().add(lastNameTextField);

        //Employee hourly wage
        Label hourlyWage = new Label("Employee Hourly Wage:");
        fieldDescription.getChildren().add(hourlyWage);

        TextField hourlyWageTextField = new TextField();
        textFields.getChildren().add(hourlyWageTextField);

        //Employee quality
        Label quality = new Label("Employee Quality:");
        fieldDescription.getChildren().add(quality);

        TextField qualityTextField = new TextField();
        textFields.getChildren().add(qualityTextField);

        //Create save employee button
        Button saveButton = new Button("Save Employee");
        saveButton.setAlignment(Pos.BOTTOM_RIGHT);
        textFields.getChildren().add(saveButton);

        //Create back button.
        Button backButton = new Button("Back");
        saveButton.setAlignment(Pos.BOTTOM_LEFT);
        fieldDescription.getChildren().add(backButton);
        
        
        grid.add(fieldDescription,4,3,1,1);
        grid.add(textFields,5,3,1,2);

        //Text to be displayed. Will do nothing for now.
        final Text actiontarget = new Text();
        grid.add(actiontarget, 5, 6);

        //Handler for save button.
        saveButton.setOnAction((ActionEvent e) -> {
            //Assign the members of the employee based on the text provided in the fields
            Employee newEmployee = new Employee();
            newEmployee.setFirstName(firstNameTextField.getText());
            newEmployee.setLastName(lastNameTextField.getText());
            newEmployee.setHourlyWage(Double.parseDouble(hourlyWageTextField.getText()));
            newEmployee.setQuality(Integer.parseInt(qualityTextField.getText()));
            FileManager fileManager = new FileManager();
            fileManager.JSONWriter(newEmployee);

            actiontarget.setFill(Color.BLACK);
            String employeeString = new String();
            actiontarget.setText(newEmployee.getInformation());
        });

        //Handler for back button. Will return to start application for now.
        backButton.setOnAction((ActionEvent e) -> {
            swapScene(sceneNames.START, primaryStage);
        });

        return grid;

    }

    public void swapScene(sceneNames sceneName, Stage primaryStage) {

        GridPane grid = new GridPane();
        switch (sceneName) {
            case START:
                startGridBuilder(grid, primaryStage);
                break;

            case NEW_EMPLOYEE:
                employeeGridBuilder(grid, primaryStage);
                break;
        }

        //Scene code.
        //Give our scene the grid we have been building
        Scene newScene = new Scene(grid, 889, 500);
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {

        //Stage is the big daddy for the whole application. Ours is primaryStage here.
        primaryStage.setTitle("JavaFX Welcome");
        swapScene(sceneNames.START, primaryStage);
    }

    public enum sceneNames {
        START,
        NEW_EMPLOYEE
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
