/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler.Constraints;

import java.util.ArrayList;
import java.util.Map;
import mellowscheduler.Employee;
import mellowscheduler.Schedule;
import mellowscheduler.Shift;

/**
 *
 * @author Alex
 */
public class EmployeeAvailabilityConstraint implements Constraint{
    
    
    public boolean satisfied(Object obj)
    {
        Schedule newSchedule = (Schedule) obj;
        ArrayList<Employee> scheduledEmployees = newSchedule.getScheduledEmployees();
        
        //For each day...
        for(int ii = 0; ii < 7; ii++)
        {
            //For each shift...
            Map<Shift, Employee> dailySchedule = newSchedule.getSchedule().get(ii);
            for(Shift currentShift : dailySchedule.keySet())
            {
                //If that shift overlaps with employee's availability, it's a bad schedule;
                Employee currentEmployee = dailySchedule.get(currentShift);
                
                // If we have a shift scheduled to a null employee, it should not fail based on availability.
                if(currentEmployee == null)
                {
                    continue;
                }
                
                for(Shift unavailableShift : currentEmployee.getUnavailable().get(ii))
                {
                    if(newSchedule.shiftOverlaps(unavailableShift, currentShift))
                    {
                        return false;
                    }
                }
                
            }
        }
        return true;
    }
    
    public boolean checkAvailable(int day, Shift openShift, Employee employee)
    {
        ArrayList<Shift> unavailableShiftsThatDay = employee.getUnavailable().get(day);
        
        for(Shift unavailableShift : unavailableShiftsThatDay)
        {
            if (unavailableShift.overlaps(openShift))
            {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String printString()
    {
        String printString = "Respect employee availability.";
        
        return printString;
    }
    
}
