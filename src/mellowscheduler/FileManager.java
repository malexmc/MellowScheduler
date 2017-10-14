/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Alex
 */
public class FileManager {
    
    //Makes sure the file a given directory exists
    private  boolean ensure_file_exist(String filePath )
    {
        
        //TODO: Make directory dynamic
        File employeeFile = new File(filePath);
  
        try {
            //Create the file
            if (employeeFile.createNewFile())
            {
                System.out.println("File is created!");
            }
            else
            {
                System.out.println("File already exists.");
                return false;
            }
            
                    } 
        catch (IOException e) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        
        return true;
    }
        
    //Currently used to write employee data stored in memory into a JSON file.
    //TODO: Save JSON data intelligently so that employees are not duplicated
    public void JSONWriter(Employee employeeToWrite)
    {
                 //Get all currently written employees before we destroy them.
                ArrayList<Employee> employees = new ArrayList<>();
                JSONReader(employees);
                
                //Make sure new employee is not already in system
                for (Employee currentEmployee : employees)
                {
                    //Assume employees with same name are the same person
                    if (currentEmployee.getFirstName().equals(employeeToWrite.getFirstName()) && currentEmployee.getLastName().equals(employeeToWrite.getLastName()))
                    {
                        //TODO: make this return some message to let user know employee already existed.
                        return;
                    }
                }
                
                employees.add(employeeToWrite);

                //Make container for employees
                JSONObject employeesJSON = new JSONObject();
                
                //Make list for employees and add employee JSONs to it
                JSONArray employeeJSONs = new JSONArray();
                
                    for (Employee currentEmployee : employees)
                    {
                        //Make employee into JSON object, and add to list.
                        JSONObject newJSON = new JSONObject();

                        newJSON.put("first name", employeeToWrite.getFirstName());
                        newJSON.put("last name", employeeToWrite.getLastName());
                        newJSON.put("hourly wage", employeeToWrite.getHourlyWage().toString());
                        newJSON.put("quality", employeeToWrite.getQuality().toString());

                        employeeJSONs.add(newJSON);
                    }
                
                 employeesJSON.put("employees", employeeJSONs);
                 
                 
                 //Get write handle right before because it destroys everything
                 try (FileWriter employeeWriteHandle = new FileWriter("./Employees.JSON", false ))
                 {
                    employeeWriteHandle.write(employeesJSON.toJSONString());    
                 }
                 catch(Exception e)
                 {
                 }
                 
               
    }
    
    public void JSONWriter(Schedule scheduleToWrite)
    {
        //Make JSONObject to hold week schedule
        JSONObject weekScheduleJSON = new JSONObject();
        
        //Make list for dailySchedules
        JSONArray dailySchedulesJSON = new JSONArray();
        
        //Make JSON Object for each day
        for (int ii = 0; ii < 7; ii++)
        {
            
            JSONObject currentDay = new JSONObject();
            JSONArray workPairs = new JSONArray();
            
            Map<Shift, Employee> currentDayPairs = scheduleToWrite.getSchedule().get(ii);
            
            
            for(Shift currentShift : currentDayPairs.keySet())
            {
                JSONArray workPair = new JSONArray();
                Employee currentEmployee = currentDayPairs.get(currentShift);
                
                JSONObject currentShiftJSON = new JSONObject();
                JSONObject currentEmployeeJSON = new JSONObject();
                
                
                currentShiftJSON.put("start time", currentShift.getStartTime());
                currentShiftJSON.put("end time", currentShift.getEndTime());
                
                currentEmployeeJSON.put("first name", currentEmployee.getFirstName());
                currentEmployeeJSON.put("Last name", currentEmployee.getLastName());
                currentEmployeeJSON.put("hourly wage", currentEmployee.getHourlyWage().toString());
                currentEmployeeJSON.put("quality", currentEmployee.getQuality().toString());
                
                workPair.add(currentShiftJSON);
                workPair.add(currentEmployeeJSON);
                workPairs.add(workPair);
            }
            
            currentDay.put("work pairs", workPairs);
            dailySchedulesJSON.add(currentDay);
        }
        weekScheduleJSON.put("daily schedules", dailySchedulesJSON);
            
        //Get write handle right before because it destroys everything
        try (FileWriter employeeWriteHandle = new FileWriter("./" + scheduleToWrite.getName() + ".JSON", false ))
        {
           employeeWriteHandle.write(weekScheduleJSON.toJSONString());    
        }
        catch(Exception e)
        {
        }
    }
    
    public ArrayList<Employee> JSONReader(ArrayList<Employee> employees)
    {
        
        JSONParser parser = new JSONParser();

        try (FileReader JSONFile = new FileReader("./Employees.JSON") )
        {
            
            //Parses the file
            Object obj = parser.parse(JSONFile);

            
            JSONObject employeeContainerJSON = (JSONObject) obj;

            //From the employee container, we'll get the list of employees, and set up an iterator for it
            JSONArray employeesJSON = (JSONArray) employeeContainerJSON.get("employees");
            Iterator<JSONObject> employeesIterator = employeesJSON.iterator();
            
            //For as long as we have more employees, turn them into employee objects, and add them to the employees list.
            while (employeesIterator.hasNext())
            {
                JSONObject currentEmployeeJSON = employeesIterator.next();
                Employee currentEmployee = new Employee();
            
                currentEmployee.setFirstName( (String) currentEmployeeJSON.get("first name") );
                currentEmployee.setLastName( (String) currentEmployeeJSON.get("last name") );
                currentEmployee.setHourlyWage( Double.parseDouble( (String) currentEmployeeJSON.get("hourly wage") ) );
                currentEmployee.setQuality( Integer.parseInt( (String) currentEmployeeJSON.get("quality") ) );

                employees.add(currentEmployee);
            }
            

        }

        catch (Exception e)
        {
            e.printStackTrace();
        } 


        return employees;
    }
    
    
    
//    schedules : 
//    {
//        { "day" : "monday" ,
//          "workpairs" : [   [ "shift" : {SHIFTINFO}
//                                    "employee" : {EMPLOYEEINFO}],
//                                   ...
//                             ]
//        },
//        ...
//    }
    
    public Schedule JSONScheduleReader(String scheduleName)
    {
        
        JSONParser parser = new JSONParser();
        Schedule currentSchedule = new Schedule();
        
        try (FileReader JSONFile = new FileReader("./" + scheduleName + ".JSON"))
        {
            Object obj = parser.parse(JSONFile);
            
            //Store the scheduleContainer
            JSONObject scheduleContainer = (JSONObject) obj;
            
            //From the schedule container, get the list of schedules
            JSONArray dailySchedulesJSON = (JSONArray) scheduleContainer.get("daily schedules");
            Iterator<JSONObject> schedulesIterator = dailySchedulesJSON.iterator();

            //Make a list to store the daily shift-employee pairings
            ArrayList< Map<Shift, Employee> > dailySchedules = new ArrayList<>();
            
            while (schedulesIterator.hasNext())
            {
                
                
                JSONObject currentScheduleJSON = schedulesIterator.next();
                
                //From each schedule, get the workpairs and store them.
                JSONArray workPairsJSON = (JSONArray) currentScheduleJSON.get("work pairs");
                Iterator<JSONObject> workPairsIterator = workPairsJSON.iterator();
                
                //Make a map to keep track of individual day's shift-employee pairs
                Map<Shift, Employee> currentWorkPairs = new HashMap<>();
                
                while(workPairsIterator.hasNext())
                {
                    
                    JSONObject currentWorkPair = (JSONObject) workPairsIterator.next();
                    
                    //Extract employee and shift from workpair
                    JSONObject currentShiftJSON =  (JSONObject) currentWorkPair.get("shift");
                    JSONObject currentEmployeeJSON =  (JSONObject) currentWorkPair.get("employee");
                    
                    //Make employee and shift objects
                    Employee currentEmployee = new Employee();
                    Shift currentShift = new Shift();

                    //Assign Employee Stuff
                    currentEmployee.setFirstName( (String) currentEmployeeJSON.get("first name") );
                    currentEmployee.setLastName( (String) currentEmployeeJSON.get("nast name") );
                    currentEmployee.setHourlyWage( Double.parseDouble( (String) currentEmployeeJSON.get("hourly wage") ) );
                    currentEmployee.setQuality( Integer.parseInt( (String) currentEmployeeJSON.get("quality") ) );
                    
                    //Assign Shift Stuff
                    currentShift.setStartTime( (String) currentEmployeeJSON.get("start time") );
                    currentShift.setEndTime( (String) currentEmployeeJSON.get("end time") );
                     
                    //Add the employee and shift to the map
                    currentWorkPairs.put(currentShift, currentEmployee);
                }
                //Add the generated map to the list of maps
                dailySchedules.add(currentWorkPairs);
            }
            //set the completed list of maps to be the schedule
            currentSchedule.setSchedule(dailySchedules);
        }   
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return currentSchedule;
    }
}
