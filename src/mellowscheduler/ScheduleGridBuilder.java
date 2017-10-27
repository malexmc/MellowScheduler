/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alex McClellan
 */
public class ScheduleGridBuilder {
    
    public GridPane makeScheduleGrid(GridPane grid, Stage primaryStage)
    {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Make some column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(30);
        grid.getColumnConstraints().add(column1);
        
        Text scenetitle = new Text("EmployeeList");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        VBox employeeListVBox = new VBox();
        employeeListVBox.setSpacing(10);
        
        //Update the employee list from the employees file
        ArrayList<Employee> employees = MellowScheduler.makeEmployeeList();
        
        //Using our employee list, make a listView to display their names
        ListView employeeListView = MellowScheduler.makeEmployeeListView(employees);
        
        employeeListVBox.getChildren().add(scenetitle);
        employeeListVBox.getChildren().add(employeeListView);
        
        grid.add(employeeListVBox, 0, 3);
        
        return grid;
    }
    
}
