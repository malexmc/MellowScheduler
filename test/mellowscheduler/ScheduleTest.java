/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import mellowscheduler.Constraints.Constraint;
import mellowscheduler.Constraints.EmployeeAvailabilityConstraint;
import mellowscheduler.Constraints.FillAllShiftsConstraint;
import mellowscheduler.Constraints.WeeklyHoursConstraint;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Alex McClellan
 */
public class ScheduleTest {
    
    public ScheduleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        TestDataMaker.makeAllTestEmployeesFile();
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void DISABLED_testShiftOverlaps() {
    }

    /**
     * Test of getScheduledEmployees method, of class Schedule.
     */
    @Test
    public void DISABLED_testGetScheduledEmployees() {
    }

    /**
     * Test of getEmployeeWeeklyMinutes method, of class Schedule.
     */
    @Test
    public void DISABLED_testGetEmployeeWeeklyMinutes() {
    }

    /**
     * Test of equals method, of class Schedule.
     */
    @Test
    public void DISABLED_testEquals() {
    }

    /**
     * Test of makeSchedule method, of class Schedule.
     */
    @Test
    public void testMakeSchedule() {
        //Make new Schedule
        Schedule testSchedule = new Schedule();
        FileManager manager = new FileManager();
        
        //Make employee array
        ArrayList<Employee> employees = new ArrayList<>();
        employees = manager.JSONReader(employees, "testFiles/TestEmployees");
        
        //Make constraints 
        ArrayList<Constraint> constraints = new ArrayList<>();
        FillAllShiftsConstraint fillAllShifts = new FillAllShiftsConstraint();
        WeeklyHoursConstraint weeklyHours = new WeeklyHoursConstraint();
        EmployeeAvailabilityConstraint available = new EmployeeAvailabilityConstraint();
        constraints.add(fillAllShifts);
        constraints.add(weeklyHours);
        constraints.add(available);
        
        //Make Shifts
        ArrayList<Shift> shifts = new ArrayList<>();
        Shift opening = TestDataMaker.makeOpeningShift();
        Shift closing = TestDataMaker.makeClosingShift();
        
        shifts.add(opening);
        shifts.add(closing);
        
        try
        {
            testSchedule.makeSchedule(constraints, shifts, employees);
            System.out.print(testSchedule.getPrintableString());
        }
        catch (Exception e)
            {
                e.printStackTrace();
            }
        
        assertTrue(testSchedule.getSchedule() != null);
        
    }
    
}
