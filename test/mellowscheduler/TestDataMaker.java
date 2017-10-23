/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alex
 */
public class TestDataMaker {
    
    //Used to make an employee with all fields set
    public static Employee makeAlBundy()
    {
         Employee alBundy = new Employee();
        
        alBundy.setFirstName("Al");
        alBundy.setLastName("Bundy");
        alBundy.setHourlyWage(2.50);
        alBundy.setQuality(5);
        
        ArrayList<ArrayList<Shift>> allShiftList= new ArrayList<>();
        for(int ii = 0; ii < 7; ii++)
        {
            ArrayList<Shift> shiftList = new ArrayList<>();
            shiftList.add(makeOpeningShift());
            shiftList.add(makeClosingShift());
            
            allShiftList.add(shiftList);
        }
        
        alBundy.setUnavailable(allShiftList);
        
        return alBundy;
    }
    
      //Used to make an employee with all fields set
    public static Employee makeJohnDoe()
    {
         Employee johnDoe = new Employee();
        
        johnDoe.setFirstName("John");
        johnDoe.setLastName("Doe");
        johnDoe.setHourlyWage(5.0);
        johnDoe.setQuality(97);
        
        ArrayList<ArrayList<Shift>> allShiftList= new ArrayList<>();
        for(int ii = 0; ii < 7; ii++)
        {
            ArrayList<Shift> shiftList = new ArrayList<>();
            shiftList.add(makeOpeningShift());
            allShiftList.add(shiftList);
        }
        
        alBundy.setUnavailable(allShiftList);
        
        return johnDoe;
    }
    
    public static Employee makeBillyOvertime()
    {
        Employee billyOvertime = new Employee();
        
        billyOvertime.setFirstName("Billy");
        billyOvertime.setLastName("Overtime");
        billyOvertime.setHourlyWage(1.0);
        billyOvertime.setQuality(97);
        
        return billyOvertime;
    }
    
    //Makes Opening Shift with times filled out.
    public static Shift makeOpeningShift()
    {
        Shift openingShift =  new Shift();
        
        openingShift.setStartTime("0800");
        openingShift.setEndTime("1400");
        
        return openingShift;
    }
    
        //Makes Closing Shift with times filled out.
    public static Shift makeClosingShift()
    {
        Shift closingShift =  new Shift();
        
        closingShift.setStartTime("1400");
        closingShift.setEndTime("2000");
        
        return closingShift;
    }
    
    public static Schedule makeGenericSchedule()
    {
         //Make generic schedule 
        
        Schedule testSchedule = new Schedule();
        
        ArrayList<Map<Shift, Employee>> weeklySchedule = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            Map<Shift, Employee> oneDaySchedule = new HashMap<>();
            oneDaySchedule.put(makeOpeningShift(), makeAlBundy());
            oneDaySchedule.put(makeClosingShift(), makeJohnDoe());
            weeklySchedule.add(oneDaySchedule);
        }
        
        testSchedule.setSchedule(weeklySchedule);
        
        return testSchedule;
    }
    
    public static Schedule makeOvertimeSchedule()
    {
         //Make generic schedule 
        
        Schedule testSchedule = new Schedule();
        
        ArrayList<Map<Shift, Employee>> weeklySchedule = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            Map<Shift, Employee> oneDaySchedule = new HashMap<>();
            oneDaySchedule.put(makeOpeningShift(), makeBillyOvertime());
            oneDaySchedule.put(makeClosingShift(), makeBillyOvertime());
            weeklySchedule.add(oneDaySchedule);
        }
        
        testSchedule.setSchedule(weeklySchedule);
        
        return testSchedule;
    }

    public static Schedule makeUndertimeSchedule()
    {
         //Make generic schedule 
        
        Schedule testSchedule = new Schedule();
        
        ArrayList<Map<Shift, Employee>> weeklySchedule = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            Map<Shift, Employee> oneDaySchedule = new HashMap<>();
            if(ii < 3)
            {
                oneDaySchedule.put(makeOpeningShift(), makeBillyOvertime());
            }
            weeklySchedule.add(oneDaySchedule);
        }
        
        testSchedule.setSchedule(weeklySchedule);
        
        return testSchedule;
    }
    
    public static Schedule makeUnderstaffedSchedule()
    {
        //Make generic schedule 
        
        Schedule testSchedule = new Schedule();
        
        ArrayList<Map<Shift, Employee>> weeklySchedule = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            if (ii == 0)
            {
                Map<Shift, Employee> oneDaySchedule = new HashMap<>();
                oneDaySchedule.put(makeOpeningShift(), makeAlBundy());
                oneDaySchedule.put(makeClosingShift(), null);
                weeklySchedule.add(oneDaySchedule);                
            }
            else
            {
                Map<Shift, Employee> oneDaySchedule = new HashMap<>();
                oneDaySchedule.put(makeOpeningShift(), makeAlBundy());
                oneDaySchedule.put(makeClosingShift(), makeJohnDoe());
                weeklySchedule.add(oneDaySchedule);
            }
            
        }
        
        testSchedule.setSchedule(weeklySchedule);
        
        return testSchedule;
    }
    
}
