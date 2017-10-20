/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import mellowscheduler.Constraints.Constraint;
import mellowscheduler.Constraints.EmployeeAvailabilityConstraint;

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
    
    public boolean shiftOverlaps(Shift shift1, Shift shift2)
    {
        Integer shiftOneStart = shift1.timeToMinutes(shift1.getStartTime());
        Integer shiftOneEnd = shift1.timeToMinutes(shift1.getEndTime());
        Integer shiftTwoStart = shift1.timeToMinutes(shift2.getStartTime());
        Integer shiftTwoEnd = shift1.timeToMinutes(shift2.getEndTime())        ;
        
        // If shift one starts or ends in the middle of shift two, then it overlaps
        if ( ( (shiftOneStart - shiftTwoStart) >= 0) && ((shiftOneStart - shiftTwoEnd) <= 0) 
             || 
             ((shiftOneEnd - shiftTwoStart) >= 0) &&  ((shiftOneEnd - shiftTwoEnd) <= 0)
            )
        {
            return true;
        }
        return false;
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
        //If parameter object is null, it can never be equal to our Schedule
        if(obj == null)
        {
            return false;
        }
        
        
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
        
    public void makeSchedule(ArrayList<Constraint> constraints, ArrayList<Shift> shifts, ArrayList<Employee> employees)
    {
        //Take in constraints
        //Take in employees
        //Take in shifts
        
        //While we don't have a good schedule
        while(true)
        {
            //Make blank schedule
            Schedule newSchedule = new Schedule();
            ArrayList<Map<Shift, Employee>> innerSchedule = newSchedule.getSchedule();
        
            //For each day...
            for(int ii = 0; ii < 7; ii++)
            {
                Map<Shift, Employee> dailySchedule = new HashMap<>();
                
                ArrayList<ArrayList<Employee>> allShiftAvailableEmployees = new ArrayList<>();
                Map<Employee, Shift> works = new HashMap<>();
                EmployeeAvailabilityConstraint available = new EmployeeAvailabilityConstraint();
                
                //For each shift, save all employees that can work it based on availability constraints
                for (Shift currentShift : shifts)
                {
                    ArrayList<Employee> availableEmployees = new ArrayList<>();
                    for(Employee currentEmployee : employees)
                    {
                        if(available.satisfied(newSchedule))
                        {
                            availableEmployees.add(currentEmployee);
                        }
                    }
                    allShiftAvailableEmployees.add(availableEmployees);
                }
                
            //Move through day -> shift-employee arrays
                for(int jj = 0; jj < allShiftAvailableEmployees.size(); jj++)
                {
                    //If employee can be added to a shift, and doesn't have an overlapping shift, schedule him/her
                    Shift currentShift = shifts.get(jj);
                    for(Employee currentEmployee : allShiftAvailableEmployees.get(jj))
                    {
                        boolean isWorking = false;
                        for(Employee workingEmployee : works.keySet())
                        {
                            if(workingEmployee.equals(currentEmployee))
                            {
                                isWorking = true;
                            }
                        }
                        
                        if(isWorking = true)
                        {
                            
                        }
                    }

                }

            //Once all shifts are added, file through days to make sure other constraints are met

            //If shift is all good, return it.
            }

        }

    }
}
