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
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mellowscheduler.Constraints.Constraint;

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
    
    public ListView makeConstraintListView()
    {
        ListView constraintListView = new ListView();
        ObservableList constraintListItems = FXCollections.observableArrayList();
        
        constraintListItems.add( constraintTypeStringMap.get(constraintTypes.WEEKLY_HOURS) );
        constraintListItems.add( constraintTypeStringMap.get(constraintTypes.FILL_ALL_SHIFTS) );
        constraintListItems.add( constraintTypeStringMap.get(constraintTypes.EMPLOYEE_AVAILABILITY) );
        
        constraintListView.setItems(constraintListItems);
        return constraintListView;
    }
    
    public void makeConstraintDisplay(){}
    
    public GridPane makeConstraintBuilder()
    {
        GridPane builderPane = new GridPane();
        
        //Make Selection list of different constraint types
        Text openingText = new Text("I want to constrain the ");
        openingText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        builderPane.add(openingText, 0, 0);
        //TODO Set list view inside ScrollPane
        ListView constraintListView = makeConstraintListView();
        builderPane.add(constraintListView, 1, 0);
                
        return builderPane;
    }
    
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
    
    public GridPane makeConstraintGrid(GridPane grid, Stage primaryStage, MellowScheduler mellowScheduler, ScheduleGridBuilder scheduleGridBuilder)
    {
        
        //Set some grid stuff
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Make label for current constraints
        Text currentConstraintText = new Text("Current Constraints:");
        currentConstraintText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(currentConstraintText, 0, 0);
        
        //Display current constraints
        makeConstraintDisplay();
        
        //Label for constraint builder
        Text constraintBuilderText = new Text("Current Constraints:");
        constraintBuilderText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(constraintBuilderText, 0, 2);
        
        //Constraint builder
        grid.add(makeConstraintBuilder(), 0, 3);
        
        //Save constraints
        
        //Return to schedule
        grid.add(makeReturnButton(mellowScheduler, primaryStage), 0, 3);
        
        return grid;
    }
    
}
