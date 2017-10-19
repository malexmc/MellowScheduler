/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Alex McClellan
 */
public class FileManagerTest {
    
    public FileManagerTest() {
    }
    
    //Used to make an employee with all fields set
    public Employee makeAlBundy()
    {
         Employee alBundy = new Employee();
        
        alBundy.setFirstName("Al");
        alBundy.setLastName("Bundy");
        alBundy.setHourlyWage(2.50);
        alBundy.setQuality(5);
        
        return alBundy;
    }
    
      //Used to make an employee with all fields set
    public Employee makeJohnDoe()
    {
         Employee johnDoe = new Employee();
        
        johnDoe.setFirstName("Josh");
        johnDoe.setLastName("Doe");
        johnDoe.setHourlyWage(5.0);
        johnDoe.setQuality(97);
        
        return johnDoe;
    }
    
    //Makes Opening Shift with times filled out.
    public Shift makeOpeningShift()
    {
        Shift openingShift =  new Shift();
        
        openingShift.setStartTime("0800");
        openingShift.setEndTime("1400");
        
        return openingShift;
    }
    
        //Makes Closing Shift with times filled out.
    public Shift makeClosingShift()
    {
        Shift closingShift =  new Shift();
        
        closingShift.setStartTime("1400");
        closingShift.setEndTime("2000");
        
        return closingShift;
    }
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test Employees to JSON method, of class FileManager.
     * This will be in place of testing fileWriter core functionality because tests based on IO are not great. We're 
     * Mostly interested in making sure the right string is created to be written, which is what this function does.
     */
    @Test
    public void testEmployeesToJSON() {
        System.out.println("File Manager - testJSONWriter_Employee");

        //Clean up Bundy File
        File f = new File("./Bundy.JSON");
        if (f.exists())
        {
            f.delete();
        }
        
        
        String correctString = "{\"employees\":[{\"last name\":\"Bundy\",\"first name\":\"Al\",\"hourly wage"
                                         + "\":\"2.5\",\"quality\":\"5\"}]}";
        FileManager instance = new FileManager();
        
        
        assertEquals(correctString, instance.employeesToJSON( makeAlBundy(), "Bundy").toJSONString());
        
        //Clean up Bundy file after
        if (f.exists())
        {
            f.delete();    
        }
        
    }
    
    @Test
    public void testJSONReader_Employee() {
        ArrayList<Employee> employees = new ArrayList<>();
        String fileName = "testFiles/PermaBundy";
        
        FileManager manager = new FileManager();
        employees = manager.JSONReader(employees, fileName);
        
        Employee bundy = makeAlBundy();
        
        assertEquals(bundy.getFirstName(), employees.get(0).getFirstName());
        assertEquals(bundy.getLastName(), employees.get(0).getLastName());
        assertEquals(bundy.getHourlyWage(), employees.get(0).getHourlyWage());
        assertEquals(bundy.getQuality(), employees.get(0).getQuality());
        
    }    
    
    @Test
    public void testScheduleToJSON() {
        //Make synthetic schedule 
        
        Schedule testSchedule = new Schedule();
        
        ArrayList<Map<Shift, Employee>> weeklySchedule = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            Map<Shift, Employee> oneDaySchedule = new HashMap<>();
            oneDaySchedule.put(makeOpeningShift(), makeAlBundy());
            oneDaySchedule.put(makeClosingShift(), makeJohnDoe());
            weeklySchedule.add(oneDaySchedule);
        }
        testSchedule.setSchedule(weeklySchedule);
        
        FileManager manager = new FileManager();
        
        Schedule rereadSchedule = manager.parseScheduleJSON(manager.scheduleToJSON(testSchedule));
        
        assertTrue( rereadSchedule.equals(testSchedule) );
        
    }    

    @Test
    public void testJSONScheduleReader()
    {
     
        //Make synthetic schedule 
        
        Schedule testSchedule = new Schedule();
        
        ArrayList<Map<Shift, Employee>> weeklySchedule = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            Map<Shift, Employee> oneDaySchedule = new HashMap<>();
            oneDaySchedule.put(makeOpeningShift(), makeAlBundy());
            oneDaySchedule.put(makeClosingShift(), makeJohnDoe());
            weeklySchedule.add(oneDaySchedule);
        }
        
        testSchedule.setSchedule(weeklySchedule);
        
        //Compare to read in test schedule
        FileManager manager = new FileManager();
        
        Schedule readSchedule = manager.JSONScheduleReader("testFiles/PermaSchedule");
        
        
        assertTrue( readSchedule.equals(testSchedule));
        
        
    }
}
