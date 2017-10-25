/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex McClellan
 */
public class EmployeeTest {
    
    public EmployeeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        TestDataMaker.makeAllTestEmployeesFile();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }


    /**
     * Test of getInformation method, of class Employee.
     */
    @Test
    public void testGetInformation() {
        
        System.out.println("Employee - testGetInformation");
        
        String firstName = "Al";
        String lastName = "Bundy";
        Double hourlyWage = 2.50;
        Integer  quality = 1;
        
        String resultString = "First Name: " + firstName + 
           "\nLast Name: " + lastName + 
           "\nHourly Wage: " + hourlyWage.toString() + 
           "\nQuality: " + quality.toString();
        
        Employee newEmployee = new Employee();
        
        newEmployee.setFirstName(firstName);
        newEmployee.setLastName(lastName);
        newEmployee.setHourlyWage(hourlyWage);
        newEmployee.setQuality(quality);
        
        assertEquals(resultString, newEmployee.getInformation());
    }
    
}
