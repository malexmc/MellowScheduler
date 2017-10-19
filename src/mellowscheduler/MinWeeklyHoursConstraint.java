/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

/**
 *
 * @author Alex
 */
public class MinWeeklyHoursConstraint implements Constraint{
    private Employee employee;
    private int hours;
    
    public void setEmployee(Employee e)
    {
        employee = e;
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
    
    //Checks that constraint is satisfied when applied to all employees
    @Override
    public boolean satisfied(Schedule constrainedSchedule)
    {
        for (Employee currentEmployee : constrainedSchedule.getScheduledEmployees())
        {
            if ( constrainedSchedule.getEmployeeWeeklyMinutes(currentEmployee) < (hours * 60))
            {
                return false;
            }
        }
        
        return true;
    }
}
