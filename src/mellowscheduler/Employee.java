/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Employee {
    private Integer employeeID;
    private String firstName;
    private String lastName;
    private Double hourlyWage;
    private Integer quality;
    //private Shift commonShift
    
    public Employee(){
        firstName = "";
        lastName = "";
        hourlyWage = 0.0;
        quality = 0;
        
        ArrayList<Employee> employees = new ArrayList<Employee>();
        FileManager fileManager = new FileManager();
        fileManager.JSONReader(employees);
    }
    
    //Member set functions
    public void setEmployeeID(Integer value)
    {
        employeeID = value;
    }
    
    public void setFirstName(String value)
    {
        firstName = value;
    }
    
    public void setLastName(String value)
    {
        lastName = value;
    }

    public void setHourlyWage(Double value)
    {
        hourlyWage = value;
    }
    
    public void setQuality(Integer value)
    {
        quality = value;
    }
    
    
    //Member get functions
    public Integer getEmployeeID()
    {
        return employeeID;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
   
    public String getLastName()
    {
        return lastName;
    }
    
    public Double getHourlyWage()
    {
        return hourlyWage;
    }
    
    public Integer getQuality()
    {
        return quality;
    }
    


    public String getInformation(String returnString)
    {
        returnString = "First Name: " + firstName + 
           "\nLast Name: " + lastName + 
           "\nHourly Wage: " + hourlyWage.toString() + 
           "\nQuality: " + quality.toString();
        
        return returnString;
    }
}
