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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mellowscheduler.Constraints.Constraint;
import mellowscheduler.Constraints.EmployeeAvailabilityConstraint;
import mellowscheduler.Constraints.FillAllShiftsConstraint;
import mellowscheduler.Constraints.WeeklyHoursConstraint;
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
    public Constraint constraintInProgress;
    public MellowScheduler mellowScheduler;
    public Stage primaryStage;
    
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
                
                constraintInProgress = new FillAllShiftsConstraint();
                
                Text fill = new Text("to fill every shift.");
                secondaryBoxes.getChildren().add(fill);
                break;
            
                
            case EMPLOYEE_AVAILABILITY:
                
                constraintInProgress = new EmployeeAvailabilityConstraint();
                
                Text emp = new Text("to respect employee unavalability."); 
                secondaryBoxes.getChildren().add(emp);
                break;
                
            case WEEKLY_HOURS:
                
                WeeklyHoursConstraint weeklyHoursConstraint = new WeeklyHoursConstraint();
                constraintInProgress = weeklyHoursConstraint;
                
                Text week = new Text("to give");
                secondaryBoxes.getChildren().add(week);
                
                // Employee List + All Employees
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
    
    public void saveConstraintBuilder(GridPane builderGrid, constraintTypes enumValue, ScheduleGridBuilder scheduleGridBuilder)
    {
        switch (enumValue)
        {
            case FILL_ALL_SHIFTS: 
                constraints.add(new FillAllShiftsConstraint());
                break;
            
                
            case EMPLOYEE_AVAILABILITY:
                constraints.add(new EmployeeAvailabilityConstraint());
                break;
                
            case WEEKLY_HOURS:
                
                ArrayList<Constraint> weeklyHoursConstraints = new ArrayList<>();
                FileManager manager = new FileManager();
                ArrayList<Employee> employees = manager.JSONReader(new ArrayList<Employee>(), "Employees");
                
                //Get min or max value first
                Boolean maxValue = false;
                
                System.out.print(MellowScheduler.getNode(builderGrid, 0, 3).getClass().toString());
                HBox secondaryPanes = (HBox) MellowScheduler.getNode(builderGrid, 0, 3);
                ListView minMaxListView = (ListView) secondaryPanes.getChildren().get(2);
                
                int minMaxselectedIndex = minMaxListView.getSelectionModel().getSelectedIndex();
                
                //If we have selected a valid constraint 
                if(minMaxselectedIndex < 2
                     && minMaxselectedIndex >= 0      
                  )
                {
                    //If what we selected indicates we want a max
                    if(minMaxListView.getSelectionModel().getSelectedItem().toString().equals("At Most"))
                    {
                        maxValue = true;
                    }
                    
                }
                
                //Get hours threshold next by taking what's in the text box and making it a double
                int hours = Integer.parseInt(((TextField) secondaryPanes.getChildren().get(3)).getText());
                
                //Get employee(s) to add
                ScrollPane employeeScrollPane = (ScrollPane) secondaryPanes.getChildren().get(1);
                ListView employeeListView = (ListView) employeeScrollPane.getContent();
                
                int employeeSelectedIndex = employeeListView.getSelectionModel().getSelectedIndex();
                
                //If we have selected a valid constraint 
                if(employeeSelectedIndex < employees.size()+1
                     && employeeSelectedIndex >= 0      
                  )
                {
                // IF we want to constrain all employees...
                 if(employeeSelectedIndex == 0)
                 {
                     //Make a constraint for each of them
                     for(int ii = 0; ii < employees.size(); ii++)
                     {
                         WeeklyHoursConstraint newConstraint = new WeeklyHoursConstraint();
                         newConstraint.setEmployee(employees.get(ii));
                         newConstraint.setHours(hours);
                         newConstraint.setMax(maxValue);
                         constraints.add(newConstraint);
                     }
                 }
                 //Otherwise, just get the employee we want, and put him/her in
                 else
                 {
                     WeeklyHoursConstraint newConstraint = new WeeklyHoursConstraint();
                     newConstraint.setEmployee(employees.get(employeeSelectedIndex-1));
                     newConstraint.setHours(hours);
                     newConstraint.setMax(maxValue);
                     constraints.add(newConstraint);
                 }
                }
                
                
                break;
                
            default:
                break;
        }
    }
    
    //Makes the constraint display portion of the grid
    public ScrollPane makeConstraintDisplay()
    {

        
        ScrollPane constraintScrollPane = new ScrollPane();
        
        if(constraints != null 
            && constraints.isEmpty() == false
          )
        {
            VBox constraintsVBox = new VBox();
        
            for(Constraint constraint : constraints)
            {
                constraintsVBox.getChildren().add(new Text(constraint.printString()));
            }

            constraintScrollPane.setContent(constraintsVBox);
        }
        
        return constraintScrollPane;
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
                    constraintInProgress = null;
                    
                    String constraintString = constraintListView.getSelectionModel().getSelectedItem().toString();
                    for (constraintTypes enumValue : constraintTypeStringMap.keySet())
                    {
                        //If we find the matching enum value for the selected box, show the UI pieces related to that box
                        if(constraintTypeStringMap.get(enumValue).equals(constraintString))
                        {
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
    public Button makeSaveButton(GridPane gridPane, ScheduleGridBuilder scheduleGridBuilder)
    {
        
        //Make save button and set it disabled until we click some stuff in the dialog
        Button saveButton = new Button("Save Constraint");
        
            
        //saveButton.setDisable(true);
        
        //Handler for save button
        saveButton.setOnAction( (ActionEvent e) -> {
            
            //Make sure constraints is initiailized
            if(constraints == null)
            {
                constraints = new ArrayList<>();
            }
            
            //Go to the constraint builder
            GridPane constraintBuilderGrid = (GridPane) MellowScheduler.getNode(gridPane, 3, 0);
            
            
            //Get the selected constraint type.
            ScrollPane constraintTypeScrollPane = (ScrollPane) MellowScheduler.getNode(constraintBuilderGrid, 0, 1);
            ListView constraintTypeListView = (ListView) constraintTypeScrollPane.getContent();
            
            //If we have selected a valid constraint 
            if(constraintTypeListView.getSelectionModel().getSelectedIndex() < constraintTypeStringMap.size()
                 && constraintTypeListView.getSelectionModel().getSelectedIndex() >= 0      
              )
            {
                constraintInProgress = null;

                String constraintString = constraintTypeListView.getSelectionModel().getSelectedItem().toString();
                for (constraintTypes enumValue : constraintTypeStringMap.keySet())
                {
                    //If we find the matching enum value for the selected box, show the UI pieces related to that box
                    if(constraintTypeStringMap.get(enumValue).equals(constraintString))
                    {
                        saveConstraintBuilder(constraintBuilderGrid, enumValue, scheduleGridBuilder);
                        scheduleGridBuilder.setConstraints(constraints);
                        mellowScheduler.swapScene(MellowScheduler.sceneNames.CONSTRAINT, primaryStage);
                    }
                }
            }
            
            
            
            //Get the selected constraint type. That will govern what to do next...
        
//                employeeListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//                    @Override
//                    public void handle(MouseEvent event) {
//
//                        // If we have selected a valid constraint 
//                        if(employeeListView.getSelectionModel().getSelectedIndex() < employees.size() + 1
//                             && employeeListView.getSelectionModel().getSelectedIndex() >= 0      
//                          )
//                        {
//                            
//                        }
//
//                    }
//                 });
            
            
        });
        
        
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
        
        constraints = scheduleGridBuilder.getConstraints();
        this.primaryStage = primaryStage;
        this.mellowScheduler = mellowScheduler;
        
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
        grid.add(makeConstraintDisplay(), 0, 1);
        
        //Label for constraint builder
        Text constraintBuilderText = new Text("Make Constraints:");
        constraintBuilderText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(constraintBuilderText, 0, 2);
        
        //Constraint builder
        grid.add(makeConstraintBuilder(), 0, 3);
        
        //Save constraints
        grid.add(makeSaveButton(grid, scheduleGridBuilder), 0, 4);
        
        //Return to schedule
        grid.add(makeReturnButton(mellowScheduler, primaryStage), 0, 5);
        
        return grid;
    }
    
}
