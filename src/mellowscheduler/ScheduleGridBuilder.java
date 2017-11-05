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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mellowscheduler.Constraints.Constraint;

/**
 *
 * @author Alex McClellan
 */
public class ScheduleGridBuilder {
    
    public ArrayList<Employee> employees;
    public Schedule schedule;
    private ArrayList<ArrayList<Shift>> shifts;
    private ArrayList<Constraint> constraints;
    public String scheduleName;
    public int VISUALIZER_ROW_SIZE = 20;
    public int VISUALIZER_COLUMN_SIZE = 20;
    
    public void setConstraints(ArrayList<Constraint> constraints)
    {
        this.constraints = constraints;
    }
    public void setShifts(ArrayList<ArrayList<Shift>> shifts)
    {
        this.shifts = shifts;
    }
            
    public ArrayList<Constraint> getConstraints()
    {
        return constraints;
    }
    
    public ArrayList<ArrayList<Shift>> getShifts()
    {
        return shifts;
    }
    
    public void makeTimeLabelRow(GridPane visualizerGridPane)
    {
        RowConstraints rowConstraint = new RowConstraints(VISUALIZER_ROW_SIZE, VISUALIZER_ROW_SIZE, VISUALIZER_ROW_SIZE);
        rowConstraint.setVgrow(Priority.ALWAYS);
        visualizerGridPane.getRowConstraints().add(rowConstraint);
        
        ColumnConstraints columnConstraint = new ColumnConstraints(VISUALIZER_COLUMN_SIZE*5, VISUALIZER_COLUMN_SIZE*5, VISUALIZER_COLUMN_SIZE*5);
        columnConstraint.setHgrow(Priority.ALWAYS);
        visualizerGridPane.getColumnConstraints().add(columnConstraint);

        //For each 15 minute period in the day...
        for(int jj = 0; jj < 96; jj++)
        {
            int hour = jj/4;
            int minute = (jj % 4)*15;
            
            columnConstraint = new ColumnConstraints(VISUALIZER_COLUMN_SIZE, VISUALIZER_COLUMN_SIZE, VISUALIZER_COLUMN_SIZE);
            columnConstraint.setHgrow(Priority.ALWAYS);
            visualizerGridPane.getColumnConstraints().add(columnConstraint);

            //make a pane with borders
            StackPane pane = new StackPane();
            
            //If we're on the beginning of an hour...
            if(minute == 0)
            {
                Text timeText = new Text( Integer.toString(hour) + ":00" );
                pane.setPrefSize(20, 20);
                pane.setStyle(""
                                    + "-fx-border-color:black lightgray lightgray black; "
                                    + "-fx-background-color:lightgray; "
                                    + "-fx-border-width: 6 2 2 2; "
                                    + "-fx-border-style: solid solid solid solid;");
                pane.getChildren().add(timeText);
                visualizerGridPane.add(pane, jj+1, 0, 4, 1);
            }
//            pane.setPrefSize(20, 20);
//            pane.setStyle(""
//                                    + "-fx-border-color:black blue blue blue; "
//                                    + "-fx-background-color:lightgray; "
//                                    + "-fx-border-width: 6 2 2 2; "
//                                    + "-fx-border-style: solid solid solid solid;");
//
//
//            Rectangle rectangle = new Rectangle(19, 19);
//            rectangle.setFill(Color.BLUE);
//            rectangle.setStroke(Color.YELLOW);
//            pane.getChildren().add(rectangle);

            //Place pane into the grid for display
        }
    }
    
    public void makeEmployeeLabelRow(GridPane visualizerGridPane)
    {
        RowConstraints rowConstraint = new RowConstraints(VISUALIZER_ROW_SIZE, VISUALIZER_ROW_SIZE, VISUALIZER_ROW_SIZE);
        rowConstraint.setVgrow(Priority.ALWAYS);
        visualizerGridPane.getRowConstraints().add(rowConstraint);

        //For each 15 minute period in the day...
        int ii = 1;
        for(Employee employee : employees)
        {
            ColumnConstraints columnConstraint = new ColumnConstraints(VISUALIZER_COLUMN_SIZE*5, VISUALIZER_COLUMN_SIZE*5, VISUALIZER_COLUMN_SIZE*5);
            columnConstraint.setHgrow(Priority.ALWAYS);
            visualizerGridPane.getColumnConstraints().add(columnConstraint);

            //make a pane with borders
            StackPane pane = new StackPane();
            
            Text employeeNameText = new Text( employee.getLastName() + ", " + employee.getFirstName() );
            pane.setPrefSize(VISUALIZER_COLUMN_SIZE*5, VISUALIZER_ROW_SIZE);
            pane.setStyle(""
                                + "-fx-border-color:blalightgrayck lightgray lightgray lightgray; "
                                + "-fx-background-color:lightgray; "
                                + "-fx-border-width: 1 1 1 1; "
                                + "-fx-border-style: solid solid solid solid;");
            pane.getChildren().add(employeeNameText);
            visualizerGridPane.add(pane, 0, ii);
            ii++;
        }
//            pane.setPrefSize(20, 20);
//            pane.setStyle(""
//                                    + "-fx-border-color:black blue blue blue; "
//                                    + "-fx-background-color:lightgray; "
//                                    + "-fx-border-width: 6 2 2 2; "
//                                    + "-fx-border-style: solid solid solid solid;");
//
//
//            Rectangle rectangle = new Rectangle(19, 19);
//            rectangle.setFill(Color.BLUE);
//            rectangle.setStroke(Color.YELLOW);
//            pane.getChildren().add(rectangle);

            //Place pane into the grid for display
    }
    
    public Rectangle makeFillValue(Employee employee, int day, int fifteenMinutePeriod)
    {
        Rectangle fillRectangle = null;
        
        //First, determine if there is a shift going on right now
        String periodStart = String.format("%02d", fifteenMinutePeriod/4 ) + String.format("%02d", (fifteenMinutePeriod%4) *15 );
        String periodEnd = String.format("%02d", fifteenMinutePeriod/4 ) + String.format("%02d", ((fifteenMinutePeriod%4) *15) + 14 );
        Shift currentTimeShift = new Shift();
        currentTimeShift.setStartTime(periodStart);
        currentTimeShift.setEndTime(periodEnd);
        
        ArrayList<Shift> currentDayShifts = shifts.get(day);
        
        for(Shift shift : currentDayShifts)
        {
            //If a shift is going on
            if(shift.overlaps(currentTimeShift))
            {
                //Determine if this employee employee works it
                Employee workingEmployee = schedule.getSchedule().get(day).get(shift);

                //If the employee does...
                if (workingEmployee.equals(employee))
                {
                    //return a blue rectangle
                    fillRectangle = new Rectangle(19, 19);
                    fillRectangle.setFill(Color.BLUE);
                }
            }
        }
        
        return fillRectangle;
    }
    
    public GridPane makeScheduleVisualizer(GridPane grid)
    {
        Text visualizerText = new Text("Schedule");
        visualizerText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        grid.add(visualizerText, 3, 0);
        
        TabPane visualizerTabPane = new TabPane();
        visualizerTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        ArrayList<Tab> tabList = new ArrayList<>();
        
        for(int day = 0; day < 7; day++)
        {
            ScrollPane visualizerScrollPane = new ScrollPane();
            visualizerScrollPane.setPadding(new Insets(10, 0, 0, 0));
            GridPane visualizerGridPane = new GridPane();
            //visualizerGridPane.setGridLinesVisible(true);
            
            visualizerGridPane.setMinSize(200, 200);
            
            makeTimeLabelRow(visualizerGridPane);
            makeEmployeeLabelRow(visualizerGridPane);
        
            //For each employee
            for(int ii = 0; ii < employees.size(); ii++)
            {

                    RowConstraints rowConstraint = new RowConstraints(20, 20, 20);
                    rowConstraint.setVgrow(Priority.ALWAYS);
                    visualizerGridPane.getRowConstraints().add(rowConstraint);

                //For each 15 minute period in the day...
                for(int jj = 0; jj < (24*4); jj++)
                {
                    ColumnConstraints columnConstraint = new ColumnConstraints(20, 20, 20);
                    columnConstraint.setHgrow(Priority.ALWAYS);
                    visualizerGridPane.getColumnConstraints().add(columnConstraint);

                    //make a pane with borders
                    Pane pane = new Pane();
                    pane.setPrefSize(20, 20);
                    pane.setBorder(new Border( new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


//                    Rectangle rectangle = new Rectangle(19, 19);
//                    rectangle.setFill(Color.BLUE);
//                    rectangle.setStroke(Color.YELLOW);
                    
                    if(schedule != null)
                    {
                        Rectangle fillRectangle = makeFillValue(employees.get(ii), day, jj);
                        if (fillRectangle != null)
                        {
                            pane.getChildren().add( fillRectangle );
                        }
                    }

                    //Place pane into the grid for display
                    visualizerGridPane.add(pane, jj+1, ii+1);
                }
            }
            visualizerScrollPane.setContent(visualizerGridPane);
            visualizerScrollPane.setHvalue(.0815);
            
            Tab tab = new Tab();
            tab.setContent(visualizerScrollPane);
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
            visualizerTabPane.getTabs().add(currentTab);
        }
        grid.add(visualizerTabPane, 3, 2);

        return grid;
    }
    
    public GridPane makeScheduleGrid(GridPane grid, Stage primaryStage, MellowScheduler mellowScheduler)
    {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Make some column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(10);
        grid.getColumnConstraints().add(column1);

        //Make some row constraints
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(10);
        grid.getRowConstraints().add(row1);
        
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        grid.getRowConstraints().add(row2);
        
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(80);
        grid.getRowConstraints().add(row3);
        
        
//        //Make label for the employee list
//        Text scenetitle = new Text("Employee List");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        
//        VBox employeeListVBox = new VBox();
//        employeeListVBox.setSpacing(10);
//        
        //Update the employee list from the employees file
       employees = MellowScheduler.makeEmployeeList();
//        
//        //Using our employee list, make a listView to display their names
//        ListView employeeListView = MellowScheduler.makeEmployeeListView(employees);
//        
//        employeeListVBox.getChildren().add(scenetitle);
//        employeeListVBox.getChildren().add(employeeListView);
//        
//        grid.add(employeeListVBox, 0, 2);
        
        //Make Load Schedule Button
        Button loadScheduleButton = new Button("Load\nSchedule");
        loadScheduleButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid.add(loadScheduleButton, 0, 0);
        
        //For Debugging Only!
        //grid.setGridLinesVisible(true);
        
        
        //Make HBox to hold shift and constraint buttons
        HBox makerHBox = new HBox();
        makerHBox.setSpacing(5);
        
        //Make Employees Button
        TextField scheduleNameField = new TextField();
        if(scheduleName == null
                || scheduleName.isEmpty())
        {
            scheduleNameField.setPromptText("Schedule Name");
        }
        else
        {
            scheduleNameField.setText(scheduleName);
        }
        makerHBox.getChildren().add(scheduleNameField);
        
        scheduleNameField.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                scheduleName = scheduleNameField.getText() + event.getCharacter();
            }
        }) ;

        //Make Employees Button
        Button employeeSetButton = new Button("Set Employees");
        employeeSetButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        makerHBox.getChildren().add(employeeSetButton);
        
        //Handler for Employee button.
        employeeSetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mellowScheduler.swapScene(MellowScheduler.sceneNames.NEW_EMPLOYEE, primaryStage);
            }
        });
        
        //Make Shift Button
        Button shiftSetButton = new Button("Set Shifts");
        shiftSetButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        makerHBox.getChildren().add(shiftSetButton);
        
        //Handler for Shift Button
        shiftSetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mellowScheduler.swapScene(MellowScheduler.sceneNames.SHIFT, primaryStage);
            }
        });
        
        //Make Constraints Button
        Button constraintSetButton = new Button("Set Constraints");
        constraintSetButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        makerHBox.getChildren().add(constraintSetButton);
        
        constraintSetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mellowScheduler.swapScene(MellowScheduler.sceneNames.CONSTRAINT, primaryStage);
            }
        });
        
        //Make Generate Schedule Button
        Button generateScheduleButton = new Button("Generate Schedule");
        generateScheduleButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        
        if(     shifts != null
                && constraints != null
                && employees != null
                && scheduleName != null
                && !scheduleName.equals("")
        )
        {
            generateScheduleButton.setDisable(false);
        }
        else
        {
            generateScheduleButton.setDisable(true);
        }
            
        makerHBox.getChildren().add(generateScheduleButton);
        
        generateScheduleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                schedule = new Schedule();
                schedule.setName(scheduleNameField.getText());
                try{
                    schedule.makeSchedule(constraints, shifts, employees);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                
                mellowScheduler.swapScene(MellowScheduler.sceneNames.SCHEDULE, primaryStage);
            }
        });
        
        
        grid.add(makerHBox, 3, 1);

        
        //Make Schedule Visualizer
        grid = makeScheduleVisualizer(grid);
        
        
        //Make Save Schedule Button
        Button saveScheduleButton = new Button("Save Schedule");
        saveScheduleButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        
        return grid;
    }
    
}
