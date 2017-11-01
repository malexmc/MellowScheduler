/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mellowscheduler.Constraints.Constraint;
import static mellowscheduler.MellowScheduler.getNode;

/**
 *
 * @author Alex McClellan
 */
public class ConstraintGridBuilder {
    
    public enum constraintTypes 
    {
        WEEKLY_HOURS,
        EMPLOYEE_AVAILABILITY,
        FILL_ALL_SHIFTS
    }
    
    public Map<constraintTypes, String> constraintTypeStringMap;
    private ArrayList<Constraint> constraints;
    public final int PREF_LIST_HEIGHT = 5 * 26;
    
    public ConstraintGridBuilder()
    {
        constraintTypeStringMap  = new HashMap();
        constraintTypeStringMap.put(constraintTypes.WEEKLY_HOURS, "Weekly Hours");
        constraintTypeStringMap.put(constraintTypes.FILL_ALL_SHIFTS, "Shift Filling");
        constraintTypeStringMap.put(constraintTypes.EMPLOYEE_AVAILABILITY, "Employees Unavailability");
    }
    
    public ArrayList<Constraint> getConstraints()
    {
        if(constraints != null)
        {
            return constraints;
        }
        return null;
    }

    public void setConstraints(ArrayList<Constraint> constraints)
    {
        this.constraints = constraints;
    }
    
    // Sets the row constraints for the main grid to make sure each section gets
    // appropriate height to display its contents
    public void setRowConstraints(GridPane grid)
    {
        RowConstraints constraintListTitleRow = new RowConstraints();
        constraintListTitleRow.setPercentHeight(10);
        RowConstraints constraintListPaneRow = new RowConstraints();
        constraintListTitleRow.setPercentHeight(30);
        RowConstraints constraintBuilderTitleRow = new RowConstraints();
        constraintListTitleRow.setPercentHeight(10);
        RowConstraints constraintBuilderPaneRow = new RowConstraints();
        constraintListTitleRow.setPercentHeight(30);
        RowConstraints buttonRow = new RowConstraints();
        constraintListTitleRow.setPercentHeight(20);
        
        grid.getRowConstraints().addAll(constraintListTitleRow, constraintListPaneRow, constraintBuilderTitleRow, constraintBuilderPaneRow, buttonRow);
    }
    
    //Makes the box displaying types of constraints for the constraint builder section
    public ListView makeConstraintListView()
    {
        ListView constraintListView = new ListView();
        constraintListView.setPrefHeight(PREF_LIST_HEIGHT);
        
        ObservableList constraintListItems = FXCollections.observableArrayList();
        
        constraintListItems.add( constraintTypeStringMap.get(constraintTypes.WEEKLY_HOURS) );
        constraintListItems.add( constraintTypeStringMap.get(constraintTypes.FILL_ALL_SHIFTS) );
        constraintListItems.add( constraintTypeStringMap.get(constraintTypes.EMPLOYEE_AVAILABILITY) );
        
        constraintListView.setItems(constraintListItems);
        return constraintListView;
    }
    
    public void updateConstraintBuilder(GridPane builderGrid, constraintTypes enumValue)
    {
        
        //If we have any children at the spot we want to be at, remove them.
        builderGrid.getChildren().remove(MellowScheduler.getNode(builderGrid, 0, 3));
        HBox secondaryBoxes = new HBox();
        secondaryBoxes.setSpacing(5);
        secondaryBoxes.setAlignment(Pos.CENTER);
        
        switch (enumValue)
        {
            case FILL_ALL_SHIFTS: 
                Text fill = new Text("to fill every shift.");
                secondaryBoxes.getChildren().add(fill);
                break;
            
                
            case EMPLOYEE_AVAILABILITY:
                Text emp = new Text("to respect employee unavalability."); 
                secondaryBoxes.getChildren().add(emp);
                break;
                
            case WEEKLY_HOURS:
                Text week = new Text("to give");
                secondaryBoxes.getChildren().add(week);
                
                //Employee List + All Employees
                ScrollPane employeeScrollPane = new ScrollPane();
                
                FileManager manager = new FileManager();
                ArrayList<Employee> employees = manager.JSONReader(new ArrayList<Employee>(), "Employees");
                
                ListView employeeListView = MellowScheduler.makeEmployeeListView(employees);
                employeeListView.getItems().add(0, "All Employees");
                employeeListView.setPrefHeight(PREF_LIST_HEIGHT);
                
                
                employeeScrollPane.setContent(employeeListView);
                secondaryBoxes.getChildren().add(employeeScrollPane);
                
                //Less than or more than
                ListView comparisonListView = new ListView();
                comparisonListView.setPrefHeight(PREF_LIST_HEIGHT);
                
                
                //Just enough room to display these fixed values
                comparisonListView.setPrefWidth(100);
                ObservableList comparisonListItems = FXCollections.observableArrayList();
                comparisonListItems.addAll("At Least", "At Most");

                comparisonListView.setItems(comparisonListItems);
                secondaryBoxes.getChildren().add(comparisonListView);
                
                //Text box for hours
                TextField hoursField = new TextField();
                //Only want room for about three characters
                hoursField.setPrefWidth(50);
                hoursField.setPromptText("8");
                secondaryBoxes.getChildren().add(hoursField);
                
                Text hours = new Text("hours");
                secondaryBoxes.getChildren().add(hours);
                break;
                
            default:
                throw new AssertionError(enumValue.name());
            
        }
        builderGrid.add(secondaryBoxes, 3, 0);
    }
    
    //Makes the constraint display portion of the grid
    public void makeConstraintDisplay()
    {
    
    }
    
    //Makes the constraint builder section of the grid
    public GridPane makeConstraintBuilder()
    {
        GridPane builderGrid = new GridPane();
        builderGrid.setHgap(5);
        
        //Make Selection list of different constraint types
        Text openingText = new Text("I want to constrain the ");
        openingText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        builderGrid.add(openingText, 0, 0);
        
        ScrollPane constraintListScrollPane = new ScrollPane();
        
        ListView constraintListView = makeConstraintListView();
        
        //Set Handlers for constraint type selection to create correct dialog boxes afterwards.
        constraintListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                //If we have selected a valid constraint 
                if(constraintListView.getSelectionModel().getSelectedIndex() < constraintTypeStringMap.size()
                     && constraintListView.getSelectionModel().getSelectedIndex() >= 0      
                  )
                {
                    String constraintString = constraintListView.getSelectionModel().getSelectedItem().toString();
                    for (constraintTypes enumValue : constraintTypeStringMap.keySet())
                    {
                        //If we find the matching enum value for the selected box, show the UI pieces related to that box
                        if(constraintTypeStringMap.get(enumValue).equals(constraintString))
                        {
                            //TODO: Add new switch statement function to create remaining dialog boxes
                            updateConstraintBuilder(builderGrid, enumValue);
                        }
                    }
                }
                
            }
         });
        
        constraintListScrollPane.setContent(constraintListView);
        
        builderGrid.add(constraintListScrollPane, 1, 0);
                
        return builderGrid;
    }
    
    //Makes the save button to the grid
    //TODO: Make this correct for constraint Builder
    public Button makeSaveButton(TabPane shiftPane, ScheduleGridBuilder scheduleGridBuilder)
    {
        Button saveButton = new Button("Save Shifts");
        
//        //Handler for save button.
//        saveButton.setOnAction((ActionEvent e) -> {
//            
//            //Make ArrayList to hold our shifts
//            ArrayList<ArrayList<Shift>> shiftListList = new ArrayList<>();
//            
//            //For each day in the week...
//            for (int ii = 0; ii < 7; ii++)
//            {
//                //Make array to store day's shifts
//                ArrayList<Shift> currentDayShifts = new ArrayList<>();
//                
//                //Get the day's grid
//                ScrollPane temp = (ScrollPane) shiftPane.getTabs().get(ii).getContent();
//                GridPane dayGrid = (GridPane) temp.getContent();
//                int buttonRow = 0;
//                int buttonColumn = 0;
//                
//                //For each day's grid...
//                for(Node currentChild : dayGrid.getChildren())
//                {
//                    
//                    //More than likely, we are an HBox here, so if we are...
//                    if(currentChild instanceof HBox)
//                    {
//                        //Cast to HBox and get the children of it
//                        HBox currentChildHBox = (HBox) currentChild;
//                        
//                        
//                        
//                        //Check for text field children
//                        boolean textFieldChildren = false;
//                        for (Node hBoxChild : currentChildHBox.getChildren())
//                        {
//                                if(hBoxChild instanceof TextField)
//                                {
//                                    textFieldChildren = true;
//                                    break;
//                                }
//                        }
//                        
//                        //If any children are text fields...
//                        if(textFieldChildren)
//                        {
//                            //Put values into a shift
//                            Shift newShift = new Shift();
//                            newShift.setStartTime( ( (TextField) currentChildHBox.getChildren().get(0) ).getText() );
//                            newShift.setEndTime( ( (TextField) currentChildHBox.getChildren().get(2) ).getText() );
//                            
//                            //Put shift into array
//                            currentDayShifts.add(newShift);
//                        }
//                    }
//                }
//                shiftListList.add(currentDayShifts);
//            }
//            
//            scheduleGridBuilder.setShifts(shiftListList);
//            
//        });
        
        return saveButton;
    }
    
    //Makes the return button the the grid
    public Button makeReturnButton(MellowScheduler mellowScheduler, Stage primaryStage)
    {
        Button returnButton = new Button("Return to Schedule");
        //Handler for Return button.
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mellowScheduler.swapScene(MellowScheduler.sceneNames.SCHEDULE, primaryStage);
            }
        });
        return returnButton;
    }
    
    
    //Creates the screen grid.
    public GridPane makeConstraintGrid(GridPane grid, Stage primaryStage, MellowScheduler mellowScheduler, ScheduleGridBuilder scheduleGridBuilder)
    {
        
        //Set some grid stuff
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        setRowConstraints(grid);
        
        //Make label for current constraints
        Text currentConstraintText = new Text("Current Constraints:");
        currentConstraintText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(currentConstraintText, 0, 0);
        
        //Display current constraints
        makeConstraintDisplay();
        
        //Label for constraint builder
        Text constraintBuilderText = new Text("Make Constraints:");
        constraintBuilderText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(constraintBuilderText, 0, 2);
        
        //Constraint builder
        grid.add(makeConstraintBuilder(), 0, 3);
        
        //Save constraints
        
        //Return to schedule
        grid.add(makeReturnButton(mellowScheduler, primaryStage), 0, 4);
        
        return grid;
    }
    
}
