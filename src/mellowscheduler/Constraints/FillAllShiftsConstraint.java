/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler.Constraints;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import mellowscheduler.Employee;
import mellowscheduler.Schedule;
import mellowscheduler.Shift;

/**
 *
 * @author Alex McClellan
 */
public class FillAllShiftsConstraint implements Constraint{
    
    
    //Param: Expects a Schedule object
    @Override
    public boolean satisfied(Object obj)
    {
        
        Schedule constrainedSchedule = (Schedule) obj;
        
        ArrayList<Map<Shift, Employee>> innerSchedule = constrainedSchedule.getSchedule();
        
        //For each day in the schedules...
        for (int ii = 0; ii < 7; ii++)
        {
            //Get the map for the current day
            Map<Shift, Employee> currentDay = innerSchedule.get(ii);
            
            //Make iterator for the keys
            Iterator<Shift> shiftIterator = currentDay.keySet().iterator();
            
            while( shiftIterator.hasNext() )
            {
                Shift currentShift = shiftIterator.next();
                
                //If we find a shift without an employee, we failed.
                if(currentDay.get(currentShift) == null)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
