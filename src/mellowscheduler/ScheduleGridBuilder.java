/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import com.sun.org.apache.bcel.internal.classfile.Code;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private ArrayList<Shift> shifts;
    private ArrayList<Constraint> constraints;
    
    public void setConstraints(ArrayList<Constraint> constraints)
    {
        this.constraints = constraints;
    }
    public void setShifts(ArrayList<Shift> shifts)
    {
        this.shifts = shifts;
    }
            
    public ArrayList<Constraint> getConstraints()
    {
        return constraints;
    }
    
    public ArrayList<Shift> getShifts()
    {
        return shifts;
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
            visualizerGridPane.setMinSize(200, 200);
        
            //For each employee
            for(int ii = 0; ii < employees.size(); ii++)
            {

                    RowConstraints rowConstraint = new RowConstraints(20, 20, 20);
                    rowConstraint.setVgrow(Priority.ALWAYS);
                    visualizerGridPane.getRowConstraints().add(rowConstraint);

                //For each 15 minute period in the day...
                for(int jj = 0; jj < 96; jj++)
                {
                    ColumnConstraints columnConstraint = new ColumnConstraints(20, 20, 20);
                    columnConstraint.setHgrow(Priority.ALWAYS);
                    visualizerGridPane.getColumnConstraints().add(columnConstraint);

                    //make a pane with borders
                    Pane pane = new Pane();
                    pane.setPrefSize(20, 20);
                    pane.setBorder(new Border( new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


                    Rectangle rectangle = new Rectangle(19, 19);
                    rectangle.setFill(Color.BLUE);
                    rectangle.setStroke(Color.YELLOW);
                    pane.getChildren().add(rectangle);

                    //Place pane into the grid for display
                    visualizerGridPane.add(pane, jj, ii);
                }
            }
            visualizerScrollPane.setContent(visualizerGridPane);
            
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
    
    public GridPane makeScheduleGrid(GridPane grid, Stage primaryStage)
    {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Make some column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        grid.getColumnConstraints().add(column1);
        
        
        //Make label for the employee list
        Text scenetitle = new Text("Employee List");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        VBox employeeListVBox = new VBox();
        employeeListVBox.setSpacing(10);
        
        //Update the employee list from the employees file
        employees = MellowScheduler.makeEmployeeList();
        
        //Using our employee list, make a listView to display their names
        ListView employeeListView = MellowScheduler.makeEmployeeListView(employees);
        
        employeeListVBox.getChildren().add(scenetitle);
        employeeListVBox.getChildren().add(employeeListView);
        
        grid.add(employeeListVBox, 0, 2);
        
        //Make Load Schedule Button
        Button loadScheduleButton = new Button("Load Schedule");
        loadScheduleButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid.add(loadScheduleButton, 0, 0);
        
        //For Debugging Only!
        grid.setGridLinesVisible(true);
        
        
        //Make HBox to hold shift and constraint buttons
        HBox shiftAndConstraintHBox = new HBox();
        shiftAndConstraintHBox.setSpacing(5);

        //Make Shift Button
        Button shiftSetButton = new Button("Set Shifts");
        shiftSetButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        shiftAndConstraintHBox.getChildren().add(shiftSetButton);
        
        //Make Constraints Button
        Button constraintSetButton = new Button("Set Constraints");
        constraintSetButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        shiftAndConstraintHBox.getChildren().add(constraintSetButton);
        
        grid.add(shiftAndConstraintHBox, 3, 1);

        
        //Make Schedule Visualizer
        grid = makeScheduleVisualizer(grid);
        
        
        //Make Save Schedule Button
        Button saveScheduleButton = new Button("Save Schedule");
        saveScheduleButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        
        //Make Generate Schedule Button
        Button generateScheduleButton = new Button("Generate Schedule");
        generateScheduleButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        
        return grid;
    }
    
}
