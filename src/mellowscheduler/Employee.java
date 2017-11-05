/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import java.util.Map;

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
    private ArrayList<ArrayList<Shift>> unavailable;
    //private Shift commonShift
    
    public Employee(){
        firstName = "";
        lastName = "";
        hourlyWage = 0.0;
        quality = 0;
        unavailable = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            ArrayList<Shift> shiftList = new ArrayList<>();
            unavailable.add(shiftList);
        }
        
        
//        ArrayList<Employee> employees = new ArrayList<>();
//        FileManager fileManager = new FileManager();
//        fileManager.JSONReader(employees);
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
    
    public void setUnavailable(ArrayList<ArrayList<Shift>> value)
    {
        this.unavailable = value;
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
    
    public ArrayList<ArrayList<Shift>> getUnavailable()
    {
        return unavailable;
    }
    


    public String getInformation()
    {
        String returnString = "";
        returnString = "First Name: " + firstName + 
           "\nLast Name: " + lastName + 
           "\nHourly Wage: " + hourlyWage.toString() + 
           "\nQuality: " + quality.toString();
        
        return returnString;
    }
    
    
    //Overloads the equals operator to allow comparison of employees
    @Override
    public boolean equals(Object obj)
    {
        //If the passed in object is null, it can never be equal to our object
        if(obj == null)
        {
            return false;
        }
        
        Employee a = (Employee) obj;
        
        
        //For each day
        for(int ii = 0; ii < 7; ii++)
        {
            
            //Get the unavailable shifts for the both employees
            ArrayList<Shift> thisUnavailableShiftsToday = this.getUnavailable().get(ii);
            ArrayList<Shift> aUnavailableShiftsToday = a.getUnavailable().get(ii);
            
            if( !thisUnavailableShiftsToday.isEmpty() || !aUnavailableShiftsToday.isEmpty())
            {
                boolean matched = false;
                for(Shift thisShift: thisUnavailableShiftsToday)
                {
                    matched = false;
                    for(Shift aShift : aUnavailableShiftsToday)
                    {
                        if(aShift.equals(thisShift))
                        {
                            matched = true;
                        }
                    }
                    if(matched == false)
                    {
                        return false;
                    }
                }
                if(matched == false)
                {
                    return false;
                }
            }
            
            
            
        }
        
        return this.firstName.equals(a.getFirstName())
                && this.quality.equals(a.getQuality())
                && this.hourlyWage.equals(a.getHourlyWage())
                && this.lastName.equals(a.getLastName());
    }
}
