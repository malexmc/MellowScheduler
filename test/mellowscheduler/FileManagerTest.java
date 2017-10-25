/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.io.File;
import java.util.ArrayList;
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
    
    
    
    @BeforeClass
    public static void setUpClass() {
        TestDataMaker.makeAllTestEmployeesFile();
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
        
        
        String correctString = "{\"employees\":[{\"last name\":\"Bundy\",\"first name\":\"Al\",\"hourly wage\":\"2.5\",\"unavailable\":[{\"shifts\":[{\"end time\":\"1400\",\"start time\":\"0800\"},{\"end time\":\"2000\",\"start time\":\"1400\"}]},{\"shifts\":[{\"end time\":\"1400\",\"start time\":\"0800\"},{\"end time\":\"2000\",\"start time\":\"1400\"}]},{\"shifts\":[{\"end time\":\"1400\",\"start time\":\"0800\"},{\"end time\":\"2000\",\"start time\":\"1400\"}]},{\"shifts\":[{\"end time\":\"1400\",\"start time\":\"0800\"},{\"end time\":\"2000\",\"start time\":\"1400\"}]},{\"shifts\":[{\"end time\":\"1400\",\"start time\":\"0800\"},{\"end time\":\"2000\",\"start time\":\"1400\"}]},{\"shifts\":[{\"end time\":\"1400\",\"start time\":\"0800\"},{\"end time\":\"2000\",\"start time\":\"1400\"}]},{\"shifts\":[{\"end time\":\"1400\",\"start time\":\"0800\"},{\"end time\":\"2000\",\"start time\":\"1400\"}]}],\"quality\":\"5\"}]}";
        FileManager manager = new FileManager();
        
        assertEquals(correctString, manager.employeesToJSON( TestDataMaker.makeAlBundy(), "Bundy").toJSONString());
        
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
        
        Employee bundy = TestDataMaker.makeAlBundy();
        
        assertEquals(bundy.getFirstName(), employees.get(0).getFirstName());
        assertEquals(bundy.getLastName(), employees.get(0).getLastName());
        assertEquals(bundy.getHourlyWage(), employees.get(0).getHourlyWage());
        assertEquals(bundy.getQuality(), employees.get(0).getQuality());
        
    }    
    
    @Test
    public void testScheduleToJSON() {

        Schedule testSchedule = TestDataMaker.makeGenericSchedule();
        
        FileManager manager = new FileManager();
        
        Schedule rereadSchedule = manager.parseScheduleJSON(manager.scheduleToJSON(testSchedule));
        
        assertTrue( rereadSchedule.equals(testSchedule) );
        
    }    

    
    @Test
    public void testJSONScheduleReader()
    {
     
        Schedule testSchedule = TestDataMaker.makeGenericSchedule();
        
        //Compare to read in test schedule
        FileManager manager = new FileManager();
        
        Schedule readSchedule = manager.JSONScheduleReader("testFiles/PermaSchedule");
        
        
        assertTrue( readSchedule.equals(testSchedule));
        
        
    }
    
}
