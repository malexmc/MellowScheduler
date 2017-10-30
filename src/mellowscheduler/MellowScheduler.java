/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import com.sun.javafx.scene.control.skin.CustomColorDialog;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class MellowScheduler extends Application {

    public ScheduleGridBuilder schdeuleGridBuilder;
    public ShiftGridBuilder shiftGridBuilder;
        
    public static Node getNode(GridPane grid, final int row, final int column)
    {
        Node result = null;
        ObservableList<Node> childrens = grid.getChildren();

        for (Node node : childrens) {
            if(grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
    
    //Makes and returns a view list of the employees
    public static ArrayList<Employee> makeEmployeeList()
    {
        FileManager manager = new FileManager();
        return manager.JSONReader(new ArrayList<Employee>(), "Employees");
        
    }
    
    public static ListView makeEmployeeListView(ArrayList<Employee> employees)
    {
        //Make and populate the list view to show the employee names in the box
        ListView<String> employeeListView = new ListView();
        ObservableList employeeListItems = FXCollections.observableArrayList();
        
        for(Employee employee : employees)
        {
            employeeListItems.add( (employee.getLastName() + ", " + employee.getFirstName() ) );
        }
        
        employeeListView.setItems(employeeListItems);
        return employeeListView;
    }
    
    public GridPane addEmployeeAttributeFields(GridPane grid, Stage primaryStage)
    {

       
        
        return grid;
    }
    
    public GridPane addEmployeeViewingFields(GridPane grid, Stage primaryStage)
    {
        
        return grid;
    }
    
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
        
        //Handler for schedule button.
        scheduleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            //TODO: Implement this when it's time for the scheduler page
            public void handle(ActionEvent e) {
                swapScene(sceneNames.SCHEDULE, primaryStage);
            }
        });

        return grid;
    }

    public GridPane employeeGridBuilder(GridPane grid, Stage primaryStage) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Make some column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(30);
        grid.getColumnConstraints().add(column1);
        
        Text scenetitle = new Text("Employee Data");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        VBox employeeListVBox = new VBox();
        employeeListVBox.setSpacing(10);
        
        //Update the employee list from the employees file
        ArrayList<Employee> employees = makeEmployeeList();
        
        //Using our employee list, make a listView to display their names
        ListView employeeListView = makeEmployeeListView(employees);
        
        //Create delete button
        Button deleteButton = new Button("Delete Employee");
        deleteButton.setDisable(true);
        
        //Add List to VBox so they stay together
        employeeListVBox.getChildren().add(employeeListView);
        employeeListVBox.getChildren().add(deleteButton);
        grid.add(employeeListVBox, 0, 2, 1, 5);
        
        //Make sure grid has enough rows to accomodate the fields for the employee
        //Place scene title in 4th column, 1st row with span of 1 cell right and down
        grid.add(scenetitle, 5, 0, 1, 1);

        grid = addEmployeeAttributeFields(grid, primaryStage);
        
        grid = addEmployeeViewingFields(grid, primaryStage);
        
         //Employee first name
        Label firstName = new Label("Employee First Name:");
        grid.add(firstName, 5, 2);
        
        TextField firstNameTextField = new TextField();
        firstNameTextField.setPromptText("John");
        grid.add(firstNameTextField, 6, 2);
        
        //Employee last name
        Label lastName = new Label("Employee Last Name:");
        grid.add(lastName, 5, 3);
        
        TextField lastNameTextField = new TextField();
        lastNameTextField.setPromptText("Doe");
        grid.add(lastNameTextField, 6, 3);

        //Employee hourly wage
        Label hourlyWage = new Label("Employee Hourly Wage:\n(dollars and cents)");
        grid.add(hourlyWage, 5, 4);
        
        TextField hourlyWageTextField = new TextField();
        hourlyWageTextField.setPromptText("7.25");
        grid.add(hourlyWageTextField, 6, 4);
        
        //Employee quality
        Label quality = new Label("Employee Quality:\n(1-100)");
        grid.add(quality, 5, 5);
        
        TextField qualityTextField = new TextField();
        qualityTextField.setPromptText("35");
        grid.add(qualityTextField, 6, 5);
        
        //Unavailable tab pane
        Label unavailable = new Label("Employee Unavailability:");
        grid.add(unavailable, 5, 6);
        GridPane.setValignment(unavailable, VPos.TOP);
        
        
        //Make the tab pane, list for tabs and list for buttons in tabs
        TabPane unavailablePane = new TabPane();
        
        //Make panes not be able to be closed out of... We need them :)
        unavailablePane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        ArrayList<Tab> tabList = new ArrayList<>();
        ArrayList<Button> tabButtonList = new ArrayList<>();
        
        //Generate tabs and respective buttons
        for(int ii = 0; ii < 7; ii++)
        {
            //Make a grid to hold the content of the tab
            ScrollPane tabScroll = new ScrollPane();
            tabScroll.setPadding(new Insets(8, 0, 0, 0));
            GridPane tabGrid = new GridPane();
            tabGrid.setHgap(2);
            tabGrid.setVgap(5);
            
            //Make HBox for buttons
            HBox tabButtonHBox = new HBox();
            tabButtonHBox.setSpacing(5);
            
            //Make the add shift button
            Button addShiftButton = new Button("Add Unavailability");
            addShiftButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            
            //Make Delete shift button
            Button deleteShiftButton = new Button("-");
            deleteShiftButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
            
            //Make it hidden to start with, since there is nothing to delete
            deleteShiftButton.setVisible(false);

            //Make the handler for the add shift button
            addShiftButton.setOnAction((ActionEvent e) -> 
            {
                //Get the tab's content
                Tab currentTab = unavailablePane.getSelectionModel().getSelectedItem();
                ScrollPane currentTabScroll = (ScrollPane) currentTab.getContent();
                GridPane currentTabGrid = (GridPane) currentTabScroll.getContent();

                
                int buttonRow   = GridPane.getRowIndex(tabButtonHBox);
                int buttonColumn = GridPane.getColumnIndex(tabButtonHBox);
                
                //Remove buttons from grid
                currentTabGrid.getChildren().remove(tabButtonHBox);
                addShiftButton.setText("+");
                addShiftButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
                
                //Since we definitely have at least one shift now, set the button to visible
                deleteShiftButton.setVisible(true);
                
                //Make HBox for shift adding
                HBox newShiftHBox = new HBox();
                newShiftHBox.setSpacing(5);
                int MAX_TEXT_HEIGHT = 20;
                int MAX_TEXT_WIDTH =  100;
                
                TextField startTime = new TextField();
                startTime.setPromptText("1200");
                startTime.setMaxSize(MAX_TEXT_WIDTH, MAX_TEXT_HEIGHT);
                
                TextField endTime = new TextField();
                endTime.setPromptText("1800");
                endTime.setMaxSize(MAX_TEXT_WIDTH, MAX_TEXT_HEIGHT);
                
                Text to = new Text();
                to.setText("-");
                
                newShiftHBox.getChildren().add(startTime);
                newShiftHBox.getChildren().add(to);
                newShiftHBox.getChildren().add(endTime);
                
                //Add HBox where button was and add button below it
                tabButtonHBox.getChildren().clear();
                tabButtonHBox.getChildren().add(addShiftButton);
                tabButtonHBox.getChildren().add(deleteShiftButton);
                
                currentTabGrid.add( newShiftHBox, buttonColumn, buttonRow);
                currentTabGrid.add( tabButtonHBox, buttonColumn, buttonRow+1);
                
                //Add grid to scroll tp tab
                currentTabScroll.setContent(currentTabGrid);
                currentTab.setContent(currentTabScroll);
                
            });
            
            //Make the handler for the delete shift button
            deleteShiftButton.setOnAction((ActionEvent e) -> 
            {
                //Get the tab's content
                Tab currentTab = unavailablePane.getSelectionModel().getSelectedItem();
                ScrollPane currentTabScroll = (ScrollPane) currentTab.getContent();
                GridPane currentTabGrid = (GridPane) currentTabScroll.getContent();

                
                int buttonRow   = GridPane.getRowIndex(tabButtonHBox);
                int buttonColumn = GridPane.getColumnIndex(tabButtonHBox);
                
                //Remove buttons from grid
                currentTabGrid.getChildren().remove(tabButtonHBox);
                
                //See if we're about to delete the last shift
                if(buttonRow < 2)
                {
                    //If we did, reset the + button to original state and hide - button
                    addShiftButton.setText("Add Unavailability");
                    addShiftButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
                    deleteShiftButton.setVisible(false);    
                }
                
                //Delete shift HBox
                currentTabGrid.getChildren().remove(getNode(currentTabGrid, buttonRow-1, buttonColumn));
                
                //Add HBox where button was and add buttons below it
                tabButtonHBox.getChildren().clear();
                tabButtonHBox.getChildren().add(addShiftButton);
                tabButtonHBox.getChildren().add(deleteShiftButton);
                
                currentTabGrid.add( tabButtonHBox, buttonColumn, buttonRow-1);
                
                //Add grid to scroll to tab
                currentTabScroll.setContent(currentTabGrid);
                currentTab.setContent(currentTabScroll);
                
            });
            
            tabButtonHBox.getChildren().add(addShiftButton);
            tabButtonHBox.getChildren().add(deleteShiftButton);
            
            tabGrid.add(tabButtonHBox, 0, 0);
            tabScroll.setContent(tabGrid);
            
            Tab tab = new Tab();
            tab.setContent(tabScroll);
            tabList.add(tab);

        }

        //Manually set tab text
        tabList.get(0).setText("Mon");
        tabList.get(1).setText("Tue");
        tabList.get(2).setText("Wed");
        tabList.get(3).setText("Thu");
        tabList.get(4).setText("Fri");
        tabList.get(5).setText("Sat");
        tabList.get(6).setText("Sun");

        //Add all tabs to tab pane
        for(Tab currentTab : tabList)
        {
            unavailablePane.getTabs().add(currentTab);
        }
        grid.add(unavailablePane, 6, 6);
        
        
        //Create save employee button
        Button saveButton = new Button("Save Employee");
        saveButton.setAlignment(Pos.BOTTOM_RIGHT);
        grid.add(saveButton, 6, 7);
        


        //Create back button.
        Button backButton = new Button("Back");
        saveButton.setAlignment(Pos.BOTTOM_LEFT);
        grid.add(backButton, 0, 10);
        
        
        //Handler for clicking the employee name list
        employeeListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                //Turn the delete button on.
                deleteButton.setDisable(false);

                // If the user clicked the box, but clicked where there is no employee, an error will throw. 
                // We should not take any action in that case to avoid the exception
                if(employeeListView.getSelectionModel().getSelectedIndex() < employees.size()
                     && employeeListView.getSelectionModel().getSelectedIndex() >= 0      
                  )
                {
                    //Get the employee this node refers to
                    Employee selectedEmployee = employees.get(employeeListView.getSelectionModel().getSelectedIndex());

                    //Set the field information to this employee's information
                    firstNameTextField.setText(selectedEmployee.getFirstName());
                    lastNameTextField.setText(selectedEmployee.getLastName());
                    hourlyWageTextField.setText(selectedEmployee.getHourlyWage().toString());
                    qualityTextField.setText(selectedEmployee.getQuality().toString());
                    
                    ArrayList<ArrayList<Shift>> unavailability = selectedEmployee.getUnavailable();
                    
                    //For each day in the week...
                    for (int ii = 0; ii < 7; ii++)
                    {
                        //Store day's shifts
                        ArrayList<Shift> currentDayShifts = unavailability.get(ii);

                        //Get the day's grid
                        ScrollPane dayScroll = (ScrollPane) unavailablePane.getTabs().get(ii).getContent();
                        GridPane dayGrid = (GridPane) dayScroll.getContent();
                        
                        //Clear everything out and start from scratch
                        dayGrid.getChildren().clear();
                        
                        int currentRow = 0;
                        //For each shift that day
                        for (Shift currentShift : currentDayShifts)
                        {
                            //Make textField HBox and fill fields with shift values
                            HBox newShiftHBox = new HBox();
                            newShiftHBox.setSpacing(5);
                            int MAX_TEXT_HEIGHT = 20;
                            int MAX_TEXT_WIDTH =  100;

                            TextField startTime = new TextField();
                            startTime.setText(currentShift.getStartTime());
                            startTime.setMaxSize(MAX_TEXT_WIDTH, MAX_TEXT_HEIGHT);

                            TextField endTime = new TextField();
                            endTime.setText(currentShift.getEndTime());
                            endTime.setMaxSize(MAX_TEXT_WIDTH, MAX_TEXT_HEIGHT);

                            Text to = new Text();
                            to.setText("-");

                            newShiftHBox.getChildren().add(startTime);
                            newShiftHBox.getChildren().add(to);
                            newShiftHBox.getChildren().add(endTime);

                            //Add HBox to grid
                            dayGrid.add(newShiftHBox, 0, currentRow);
                            currentRow++;
                        }
                            
                        //Add Buttons to Grid
                        HBox tabButtonHBox = new HBox();
                        tabButtonHBox.setSpacing(5);

                        Button addShiftButton = new Button("Add Unavailability");
                        Button deleteShiftButton = new Button("-");
                        
                        if(currentRow == 0)
                        {
                            addShiftButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
                            
                            deleteShiftButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));     
                            deleteShiftButton.setVisible(false);
                        }
                        else
                        {
                             addShiftButton.setText("+");
                            addShiftButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

                            deleteShiftButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
                        }

                        //Make the handler for the add shift button
                        addShiftButton.setOnAction((ActionEvent e) -> 
                        {
                            //Get the tab's content
                            Tab currentTab = unavailablePane.getSelectionModel().getSelectedItem();
                            ScrollPane currentTabScroll = (ScrollPane) currentTab.getContent();
                            GridPane currentTabGrid = (GridPane) currentTabScroll.getContent();


                            int buttonRow   = GridPane.getRowIndex(tabButtonHBox);
                            int buttonColumn = GridPane.getColumnIndex(tabButtonHBox);

                            //Remove buttons from grid
                            currentTabGrid.getChildren().remove(tabButtonHBox);
                            addShiftButton.setText("+");
                            addShiftButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

                            //Since we definitely have at least one shift now, set the button to visible
                            deleteShiftButton.setVisible(true);

                            //Make HBox for shift adding
                            HBox newShiftHBox = new HBox();
                            newShiftHBox.setSpacing(5);
                            int MAX_TEXT_HEIGHT = 20;
                            int MAX_TEXT_WIDTH =  100;

                            TextField startTime = new TextField();
                            startTime.setPromptText("1200");
                            startTime.setMaxSize(MAX_TEXT_WIDTH, MAX_TEXT_HEIGHT);

                            TextField endTime = new TextField();
                            endTime.setPromptText("1800");
                            endTime.setMaxSize(MAX_TEXT_WIDTH, MAX_TEXT_HEIGHT);

                            Text to = new Text();
                            to.setText("-");

                            newShiftHBox.getChildren().add(startTime);
                            newShiftHBox.getChildren().add(to);
                            newShiftHBox.getChildren().add(endTime);

                            //Add HBox where button was and add button below it
                            tabButtonHBox.getChildren().clear();
                            tabButtonHBox.getChildren().add(addShiftButton);
                            tabButtonHBox.getChildren().add(deleteShiftButton);

                            currentTabGrid.add( newShiftHBox, buttonColumn, buttonRow);
                            currentTabGrid.add( tabButtonHBox, buttonColumn, buttonRow+1);

                            //Add grid to scroll tp tab
                            currentTabScroll.setContent(currentTabGrid);
                            currentTab.setContent(currentTabScroll);

                        });

                        //Make the handler for the delete shift button
                        deleteShiftButton.setOnAction((ActionEvent e) -> 
                        {
                            //Get the tab's content
                            Tab currentTab = unavailablePane.getSelectionModel().getSelectedItem();
                            ScrollPane currentTabScroll = (ScrollPane) currentTab.getContent();
                            GridPane currentTabGrid = (GridPane) currentTabScroll.getContent();


                            int buttonRow   = GridPane.getRowIndex(tabButtonHBox);
                            int buttonColumn = GridPane.getColumnIndex(tabButtonHBox);

                            //Remove buttons from grid
                            currentTabGrid.getChildren().remove(tabButtonHBox);

                            //See if we're about to delete the last shift
                            if(buttonRow < 2)
                            {
                                //If we did, reset the + button to original state and hide - button
                                addShiftButton.setText("Add Unavailability");
                                addShiftButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
                                deleteShiftButton.setVisible(false);    
                            }

                            //Delete shift HBox
                            currentTabGrid.getChildren().remove(getNode(currentTabGrid, buttonRow-1, buttonColumn));

                            //Add HBox where button was and add buttons below it
                            tabButtonHBox.getChildren().clear();
                            tabButtonHBox.getChildren().add(addShiftButton);
                            tabButtonHBox.getChildren().add(deleteShiftButton);

                            currentTabGrid.add( tabButtonHBox, buttonColumn, buttonRow-1);

                            //Add grid to scroll to tab
                            currentTabScroll.setContent(currentTabGrid);
                            currentTab.setContent(currentTabScroll);

                        });                        
                        
                        tabButtonHBox.getChildren().add(addShiftButton);
                        tabButtonHBox.getChildren().add(deleteShiftButton);
                        
                        dayGrid.add(tabButtonHBox, 0, currentRow);
                        
                        //Add grid to scroll to tab
                        dayScroll.setContent(dayGrid);
                        unavailablePane.getTabs().get(ii).setContent(dayScroll);
                    }
                }
            }
         });        
        
        //Handler for save button.
        saveButton.setOnAction((ActionEvent e) -> {
            //Assign the members of the employee based on the text provided in the fields
            Employee newEmployee = new Employee();
            newEmployee.setFirstName( firstNameTextField.getText() );
            newEmployee.setLastName(lastNameTextField.getText());
            newEmployee.setHourlyWage(Double.parseDouble(hourlyWageTextField.getText()));
            newEmployee.setQuality(Integer.parseInt(qualityTextField.getText()));
            
            //Handle unavailability
            ArrayList<ArrayList<Shift>> unavailability = new ArrayList<>();
            
            //For each day in the week...
            for (int ii = 0; ii < 7; ii++)
            {
                //Make array to store day's shifts
                ArrayList<Shift> currentDayShifts = new ArrayList<>();
                
                //Get the day's grid
                ScrollPane temp = (ScrollPane) unavailablePane.getTabs().get(ii).getContent();
                GridPane dayGrid = (GridPane) temp.getContent();
                int buttonRow = 0;
                int buttonColumn = 0;
                
                //For each day's grid...
                for(Node currentChild : dayGrid.getChildren())
                {
                    
                    //More than likely, we are an HBox here, so if we are...
                    if(currentChild instanceof HBox)
                    {
                        //Cast to HBox and get the children of it
                        HBox currentChildHBox = (HBox) currentChild;
                        
                        
                        
                        //Check for text field children
                        boolean textFieldChildren = false;
                        for (Node hBoxChild : currentChildHBox.getChildren())
                        {
                                if(hBoxChild instanceof TextField)
                                {
                                    textFieldChildren = true;
                                    break;
                                }
                        }
                        
                        //If any children are text fields...
                        if(textFieldChildren)
                        {
                            //Put values into a shift
                            Shift newShift = new Shift();
                            newShift.setStartTime( ( (TextField) currentChildHBox.getChildren().get(0) ).getText() );
                            newShift.setEndTime( ( (TextField) currentChildHBox.getChildren().get(2) ).getText() );
                            
                            //Put shift into array
                            currentDayShifts.add(newShift);
                        }
                    }
                }
                unavailability.add(currentDayShifts);
            }
            
            newEmployee.setUnavailable(unavailability);
            
            
            FileManager fileManager = new FileManager();
            fileManager.JSONWriter(newEmployee);

            swapScene(sceneNames.NEW_EMPLOYEE, primaryStage);
            
        });
        
        
        //Handler for clicking the Delete Button
        deleteButton.setOnAction((ActionEvent e) -> 
        {
            //Get the employee this node refers to, and remove it from the list
            employees.remove( employees.get(employeeListView.getSelectionModel().getSelectedIndex()) );
            
            //Write new employee array to file and reset screen
            FileManager manager = new FileManager();
            manager.JSONWriter(employees);
            
            swapScene(sceneNames.NEW_EMPLOYEE, primaryStage);
        });

        //Handler for back button. Will return to start application for now.
        backButton.setOnAction((ActionEvent e) -> {
            swapScene(sceneNames.START, primaryStage);
        });

        return grid;
    }

    public void swapScene(sceneNames sceneName, Stage primaryStage) {

        GridPane grid = new GridPane();
        Scene newScene = null;
        
        switch (sceneName) {
            case START:
                startGridBuilder(grid, primaryStage);
                newScene = new Scene(grid, 640, 360);
                break;

            case NEW_EMPLOYEE:
                employeeGridBuilder(grid, primaryStage);
                newScene = new Scene(grid, 960, 540);
                break;
                
            case SHIFT:
                if(shiftGridBuilder == null)
                {
                    shiftGridBuilder = new ShiftGridBuilder();
                }
                shiftGridBuilder.makeShiftGrid(grid, primaryStage, this);
                //newScene = new Scene(grid, 1920, 1080);
                newScene = new Scene(grid, 960, 540);
                break;
                
            case SCHEDULE:
                
                if(schdeuleGridBuilder == null)
                {
                    schdeuleGridBuilder = new ScheduleGridBuilder();
                }
                schdeuleGridBuilder.makeScheduleGrid(grid, primaryStage, this);
                //newScene = new Scene(grid, 1920, 1080);
                newScene = new Scene(grid, 960, 540);
                break;
        }

        //Scene code.
        //Give our scene the grid we have been building
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {

        schdeuleGridBuilder = null;
        
        //Stage is the big daddy for the whole application. Ours is primaryStage here.
        primaryStage.setTitle("JavaFX Welcome");
        swapScene(sceneNames.START, primaryStage);
    }

    public enum sceneNames {
        START,
        NEW_EMPLOYEE,
        SCHEDULE,
        SHIFT
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
