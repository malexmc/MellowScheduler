/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class ShiftGridBuilder {
    private ArrayList<Shift> shifts;
    
    public void setShifts(ArrayList<Shift> shifts)
    {
        this.shifts = shifts;
    }
    
    public ArrayList<Shift> getShifts()
    {
        return shifts;
    }
    
    public Button makeSaveButton(TabPane shiftPane, ScheduleGridBuilder scheduleGridBuilder)
    {
        Button saveButton = new Button("Save Shifts");
        
        //Handler for save button.
        saveButton.setOnAction((ActionEvent e) -> {
            
            //Make ArrayList to hold our shifts
            ArrayList<ArrayList<Shift>> shiftListList = new ArrayList<>();
            
            //For each day in the week...
            for (int ii = 0; ii < 7; ii++)
            {
                //Make array to store day's shifts
                ArrayList<Shift> currentDayShifts = new ArrayList<>();
                
                //Get the day's grid
                ScrollPane temp = (ScrollPane) shiftPane.getTabs().get(ii).getContent();
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
                shiftListList.add(currentDayShifts);
            }
            
            scheduleGridBuilder.setShifts(shiftListList);
            
        });
        
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
    
    public TabPane makeShiftTabPane()
    {
        //Make the tab pane, list for tabs and list for buttons in tabs
        TabPane unavailablePane = new TabPane();
        
        //Make panes not be able to be closed out of... We need them :)
        unavailablePane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
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
                currentTabGrid.getChildren().remove(MellowScheduler.getNode(currentTabGrid, buttonRow-1, buttonColumn));
                
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
        return unavailablePane;
    }
    
        public GridPane makeShiftGrid(GridPane grid, Stage primaryStage, MellowScheduler mellowScheduler, ScheduleGridBuilder scheduleGridBuilder)
    {
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Set row constraints for the grid
        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(20);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(60);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(10);
        
        grid.getRowConstraints().addAll(row0, row1, row2, row3);
        
        //Unavailable tab pane header
        Text unavailable = new Text("Employee Unavailability");
        unavailable.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(unavailable, 0, 0);
        GridPane.setValignment(unavailable, VPos.TOP);
        
//        RowConstraints row0 = new RowConstraints();
//        row0.setPercentHeight(20);
//        grid.getRowConstraints().add(row0);
        
        TabPane shiftPane = makeShiftTabPane();
        grid.add(shiftPane, 0, 1);
        
        grid.add(makeSaveButton(shiftPane, scheduleGridBuilder), 0, 2);
        grid.add(makeReturnButton(mellowScheduler, primaryStage), 0, 3);

        
        
        return grid;
    }
    
}
