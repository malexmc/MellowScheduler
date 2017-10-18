/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import java.util.Map;

/**
 * Holds a weekly schedule consisting of a list of dictionaries of employees and the shifts they fill
 * @author Alex McClellan
 */
public class Schedule {
    private String name;
    private ArrayList<Map<Shift, Employee>> scheduleWeek; 
    
    //Default constructor
    public Schedule()
    {
        scheduleWeek = new ArrayList<>();
        name = "";
    }
    
    //Get the schedule
    public ArrayList<Map<Shift, Employee>> getSchedule()
    {
        return scheduleWeek;
    }
    
    public String getName()
    {
        return name;
    }
    
    //Set the schedule
    public void setSchedule(ArrayList<Map<Shift, Employee>> newSchedule)
    {
        scheduleWeek  = newSchedule;
    }
    
    public void setName(String newName)
    {
        name = newName;
    }
    
    public void makeSchedule() //
    {
        //Take in constraints
        //Take in employees
        //Take in shifts
        
        //For each day...
            //For each shift, save all employees that can work it based on availability constraints
            //Place all shift arrays into a larger array for the day
        
        //Move through day -> shift-employee arrays
            //If employee can be added to a shift, and doesn't have an overlapping shift, schedule him/her
        
        //Once all shifts are added, file through days to make sure other constraints are met
        
        //If shift is all good, return it.
        
    }
}
