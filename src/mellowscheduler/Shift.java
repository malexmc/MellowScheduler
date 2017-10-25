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
    
    public Shift(String start, String end)
    {
        startTime = start;
        endTime = end;
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
    
    public int timeToMinutes(String time)
    {
               //Parse first to chars as hours. *60 to get mins. Parse second pair of chars as minutes. Add together to get total mins      
        int hours = (Integer.parseInt( time.substring(0, 2) ) *60 );
        int minutes = Integer.parseInt ( time.substring(2,4) );
        return (hours + minutes);
    }
    
    public boolean overlaps(Shift otherShift)
    {
        Integer shiftOneStart = this.timeToMinutes(this.getStartTime());
        Integer shiftOneEnd = this.timeToMinutes(this.getEndTime());
        Integer shiftTwoStart = this.timeToMinutes(otherShift.getStartTime());
        Integer shiftTwoEnd = this.timeToMinutes(otherShift.getEndTime())        ;
        
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
    
    @Override
    public boolean equals(Object obj)
    {
        //If parameter object is null, it can never be equal to our Shift
        if(obj == null)
        {
            return false;
        }
        
        Shift a = (Shift) obj;
        
        return this.startTime.equals(a.getStartTime())
               && this.endTime.equals(a.getEndTime());
    }
}
