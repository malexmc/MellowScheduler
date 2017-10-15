/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.io.File;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of JSONWriter method, of class FileManager.
     */
    @Test
    public void testJSONWriter_Employee() {
        System.out.println("File Manager - testJSONWriter_Employee");

        //Clean up Bundy File
        File f = new File("./Bundy.JSON");
        if (f.exists())
        {
            f.delete();
        }
        
        
        String correctString = "{\"employees\":[{\"last name\":\"Bundy\",\"first name\":\"Al\",\"hourly wage\":\"2.5\",\"quality\":\"5\"}]}";
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

    
}
