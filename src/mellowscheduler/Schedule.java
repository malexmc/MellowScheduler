/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
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
    
    public String getPrintableString()
    {
        String printString = "";
        printString += "+++++++++++++++++++++++++\n";
        if(scheduleWeek == null)
        {
            printString += "Schedule is blank\n";
            return printString;
        }
        
        ArrayList<String> days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        
        for(int ii = 0; ii < 7; ii++)
        {
            printString += "Day: " + days.get(ii) + "\n";
            Map<Shift, Employee> currentMap = scheduleWeek.get(ii);
            for (Shift currentShift :  currentMap.keySet())
            {
                Employee currentEmployee = currentMap.get(currentShift);
                
                printString += "    " + currentEmployee.getFirstName() + " " + currentEmployee.getLastName() + " works " + currentShift.getStartTime() + "-" + currentShift.getEndTime() + "\n";
            }
            
            printString += "\n";

        }
        
        printString += "+++++++++++++++++++++++++\n";
        return printString;
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
        
    public void makeSchedule(ArrayList<Constraint> constraints, ArrayList<Shift> shifts, ArrayList<Employee> employees) throws Exception
    {
        int x = 0;
        while(x < 100)
        {
            //shuffle all arrays to make our schedule somewhat random
            long seed = System.nanoTime();
            Collections.shuffle(constraints, new Random(seed));

            seed = System.nanoTime();
            Collections.shuffle(shifts, new Random(seed));

            seed = System.nanoTime();
            Collections.shuffle(employees, new Random(seed));


            //Make blank schedule
            Schedule newSchedule = new Schedule();
            ArrayList<Map<Shift, Employee>> innerSchedule = new ArrayList<>();

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

                    //Get the current shift
                    Shift currentShift = shifts.get(jj);

                    //For every employee available for currentShift...
                    for(Employee currentEmployee : allShiftAvailableEmployees.get(jj))
                    {
                        boolean alreadyCommitted = false;

                        //for each employee currently working...
                        for(Employee workingEmployee : works.keySet())
                        {

                            //If current employee is working...
                            if(workingEmployee.equals(currentEmployee))
                            {
                                //check if the shift overlaps with the current shift
                                if (newSchedule.shiftOverlaps(works.get(workingEmployee), currentShift))
                                {
                                    alreadyCommitted = true;
                                }

                            }
                        }

                        //If employee is free that day, add the shift.
                        if(alreadyCommitted == false)
                        {
                            works.put(currentEmployee, currentShift);
                            dailySchedule.put(currentShift, currentEmployee);
                            break;
                        }
                    }

                }

                //Add this day's map to the schedule
                innerSchedule.add(dailySchedule);
            }
            newSchedule.setSchedule(innerSchedule);
            
            //Iterate through constraints and check if schedule is good
            boolean goodSchedule = true;
            for(Constraint currentConstraint : constraints)
            {
                if(currentConstraint.satisfied(newSchedule) == false)
                {
                    goodSchedule = false;
                    break;
                }
            }
            
            //If schedule is good, set the schedule leave this god-forsaken loop
            if(goodSchedule)
            {
                setSchedule(innerSchedule);
                break;
            }
            
            //Increment X
            x++;
        }
        
        if(getSchedule() == null)
        {
            throw new Exception();
        }
    }
}
