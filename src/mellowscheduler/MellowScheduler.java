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

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //Created button and gives it style
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        //Text to be displayed. Will do nothing for now.
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //Handler for button.
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                swapScene(sceneNames.NEW_EMPLOYEE, primaryStage);
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
        grid.add(scenetitle, 0, 0, 1, 1);

        //Employee first name
        Label firstName = new Label("Employee First Name:");
        grid.add(firstName, 0, 1);

        TextField firstNameTextField = new TextField();
        grid.add(firstNameTextField, 1, 1);

        //Employee last name
        Label lastName = new Label("Employee Last Name:");
        grid.add(lastName, 0, 2);

        TextField lastNameTextField = new TextField();
        grid.add(lastNameTextField, 1, 2);

        //Employee hourly wage
        Label hourlyWage = new Label("Employee Hourly Wage:");
        grid.add(hourlyWage, 0, 3);

        TextField hourlyWageTextField = new TextField();
        grid.add(hourlyWageTextField, 1, 3);

        //Employee quality
        Label quality = new Label("Employee Quality:");
        grid.add(quality, 0, 4);

        TextField qualityTextField = new TextField();
        grid.add(qualityTextField, 1, 4);

        //Create save employee button
        Button saveButton = new Button("Save Employee");
        HBox hbsaveButton = new HBox(10);
        hbsaveButton.setAlignment(Pos.BOTTOM_RIGHT);
        hbsaveButton.getChildren().add(saveButton);
        grid.add(hbsaveButton, 1, 5);

        //Create back button.
        Button backButton = new Button("Back");
        HBox hbbackButton = new HBox(10);
        hbbackButton.setAlignment(Pos.BOTTOM_LEFT);
        hbbackButton.getChildren().add(backButton);
        grid.add(hbbackButton, 0, 5);

        //Text to be displayed. Will do nothing for now.
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

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
            actiontarget.setText(newEmployee.getInformation(employeeString));
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
        Scene newScene = new Scene(grid, 300, 275);
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {

        //Stage is the big daddy for the whole application. Ours is primaryStage here.
        primaryStage.setTitle("JavaFX Welcome");
        swapScene(sceneNames.START, primaryStage);
    }

    /*   
    public void newEmployee(Stage primaryStage)
    {
        //Stage is the big daddy for the whole application. Ours is primaryStage here.
        primaryStage.setTitle("JavaFX Welcome");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        
        //Username field
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);


        //Created button and gives it style
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        //Text to be displayed. Will do nothing for now.
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        //Handler for button.
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Got 'eem!");
            }
        });
        
        //Scene code.
        //Give our scene the grid we have been building
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     */
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
