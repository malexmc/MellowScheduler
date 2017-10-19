/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

/**
 *
 * @author Alex McClellan
 */
public class Shift {
    private String startTime;
    private String endTime;
    
    public Shift()
    {
        startTime = "0000";
        endTime = "0000";
    }
    
    public String getStartTime()
    {
        return startTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setStartTime(String newStartTime)
    {
        startTime = newStartTime;
    }
    
    public void setEndTime(String newEndTime)
    {
        endTime = newEndTime;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        Shift a = (Shift) obj;
        
        return this.startTime.equals(a.getStartTime())
               && this.endTime.equals(a.getEndTime());
    }
}
