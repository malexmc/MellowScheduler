/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
    
    public ArrayList<Employee> getScheduledEmployees()
    {
        ArrayList<Employee> uniqueEmployees = new ArrayList<>();
        
        ArrayList<Map<Shift, Employee>> innerSchedule = this.getSchedule();
        
        //For each day in the schedules...
        for (int ii = 0; ii < 7; ii++)
        {
            //Get the map for the current day
            Map<Shift, Employee> currentDay = innerSchedule.get(ii);
            
            //Get all the keys and their values and store in separate arrays.
            Iterator<Shift> shiftIterator = currentDay.keySet().iterator();
            
            while( shiftIterator.hasNext() )
            {
                Shift currentShift = shiftIterator.next();
                Employee currentEmployee = currentDay.get(currentShift);
                
                
                boolean unique = true;
                
                //For all employees in the unique list
                for(Employee listEmployee : uniqueEmployees)
                {
                    //If the current employee matches one in the list, it's not unique.
                    if( listEmployee.equals( currentEmployee ) )
                    {
                        unique = false;
                        break;
                    }
                }
                
                //If the employee is unique, add him/her to list!
                if (unique == true)
                {
                    uniqueEmployees.add(currentEmployee);
                }
            }
        }
        
        
        return uniqueEmployees;
    }
    
    public int getEmployeeWeeklyMinutes(Employee employee)
    {
        int minutes = 0;
        
        ArrayList<Map<Shift, Employee>> innerSchedule = this.getSchedule();
        
        //For each day in the schedules...
        for (int ii = 0; ii < 7; ii++)
        {
            //Get the map for the current day
            Map<Shift, Employee> currentDay = innerSchedule.get(ii);
            
            //Get all the keys and their values and store in separate arrays.
            Iterator<Shift> shiftIterator = currentDay.keySet().iterator();
            
            while( shiftIterator.hasNext() )
            {
                Shift currentShift = shiftIterator.next();
                
                //If this shift is mapped to the employee we're looking for, then add its duration to the schedule
                if (employee.equals(currentDay.get(currentShift)))
                {
                    minutes += ( (currentShift.timeToMinutes(currentShift.getEndTime()) - currentShift.timeToMinutes(currentShift.getStartTime()) ) );
                }
            }
        }
            
        return minutes;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        Schedule a = (Schedule) obj;
        ArrayList<Map<Shift, Employee>> aInnerSchedule = a.getSchedule();
        
        //For each day in the schedules...
        for (int ii = 0; ii < 7; ii++)
        {
            
            Map<Shift, Employee> currentThisDay = this.scheduleWeek.get(ii);
            Map<Shift, Employee> currentADay = aInnerSchedule.get(ii);
            
            //Get all the keys and their values and store in separate arrays.
            Iterator<Shift> thisShiftIterator = currentThisDay.keySet().iterator();
            Iterator<Shift> aShiftIterator = currentADay.keySet().iterator();
            
            ArrayList<Shift> thisShiftArray = new ArrayList<>();
            ArrayList<Employee> thisEmployeeArray = new ArrayList<>();
            ArrayList<Shift> aShiftArray = new ArrayList<>();
            ArrayList<Employee> aEmployeeArray = new ArrayList<>();
            
            while( thisShiftIterator.hasNext() )
            {
                Shift currentShift = thisShiftIterator.next();
                thisShiftArray.add( currentShift );
                thisEmployeeArray.add( currentThisDay.get(currentShift) );
            }
            
            while( aShiftIterator.hasNext() )
            {
                Shift currentShift = aShiftIterator.next();
                aShiftArray.add( currentShift );
                aEmployeeArray.add( currentADay.get(currentShift) );
            }
            
            //Match all shifts
            boolean matched = false;
            for(Shift currentThisShift : thisShiftArray)
            {
                matched = false;
                for(Shift currentAShift : aShiftArray)
                {
                    if(currentThisShift.equals(currentAShift))
                    {
                        matched = true;
                        break;
                    }
                }
                if (matched == false)
                {
                    return false;    
                }
            }
            
            //Match all employees
            for(Employee currentThisEmployee : thisEmployeeArray)
            {
                matched = false;
                for(Employee currentAEmployee : aEmployeeArray)
                {
                    if(currentThisEmployee.equals(currentAEmployee))
                    {
                        matched = true;
                        break;
                    }
                }
                if (matched == false)
                {
                    return false;    
                }
            }
        }
        
        return true;
    }
        
    public void makeSchedule()
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
