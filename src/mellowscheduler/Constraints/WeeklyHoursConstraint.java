/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler.Constraints;

import mellowscheduler.Constraints.Constraint;
import mellowscheduler.Employee;
import mellowscheduler.Schedule;

/**
 *
 * @author Alex
 */
public class WeeklyHoursConstraint implements Constraint{
    private Employee employee;
    private int hours;
    private boolean max;
    
    public void Constraint()
    {
        max = false;
    }
    
    public void setEmployee(Employee e)
    {
        employee = e;
    }
    
    public void setMax(Boolean max)
    {
        this.max = max;
    }
    
    public void setHours(int h)
    {
        hours = h;
    }
    
    public Employee getEmployee()
    {
        return employee;
    }
    
    public int getHours()
    {
        return hours;
    }
    
    public Boolean getMax()
    {
        return max;
    }
    
    // Checks that constraint is satisfied when applied to all employees
    @Override
    public boolean satisfied(Object obj)
            
    {
        
        Schedule constrainedSchedule = (Schedule) obj;
        
        // For each employee
        for (Employee currentEmployee : constrainedSchedule.getScheduledEmployees())
        {
            // If we want a min...
            if (max == false)
            {
                if ( constrainedSchedule.getEmployeeWeeklyMinutes(currentEmployee) < (hours * 60))
                {
                    return false;
                }                
            }
            // If we want a max...
            else
            {
                if ( constrainedSchedule.getEmployeeWeeklyMinutes(currentEmployee) > (hours * 60))
                {
                    return false;
                }                                
            }

        }
        
        return true;
    }
        
    @Override
    public String printString()
    {
        String printString = "Make sure that " + employee.getFirstName() + " " + employee.getLastName() + " works no ";

        if(max)
        {
            printString += "more than ";
        }
        else
        {
            printString += "less than ";
        }
        
        printString += Integer.toString(hours) + " hours.";
        
        return printString;
    }
}
