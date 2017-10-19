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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
        
    
    public JSONObject employeesToJSON(Employee employeeToWrite, String fileName)
    {
        //Get all currently written employees before we destroy them.
        ArrayList<Employee> employees = new ArrayList<>();
        JSONReader(employees, fileName);

        //Make sure new employee is not already in system
        for (Employee currentEmployee : employees)
        {
            //Assume employees with same name are the same person
            if (currentEmployee.getFirstName().equals(employeeToWrite.getFirstName()) && currentEmployee.getLastName().equals(employeeToWrite.getLastName()))
            {
                //TODO: make this return some message to let user know employee already existed.
                return null;
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
        return employeesJSON;
    }
    
    public JSONObject scheduleToJSON(Schedule scheduleToWrite)
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
                JSONObject workPair = new JSONObject();
                Employee currentEmployee = currentDayPairs.get(currentShift);
                
                JSONObject currentShiftJSON = new JSONObject();
                JSONObject currentEmployeeJSON = new JSONObject();
                
                
                currentShiftJSON.put("start time", currentShift.getStartTime());
                currentShiftJSON.put("end time", currentShift.getEndTime());
                
                currentEmployeeJSON.put("first name", currentEmployee.getFirstName());
                currentEmployeeJSON.put("last name", currentEmployee.getLastName());
                currentEmployeeJSON.put("hourly wage", currentEmployee.getHourlyWage().toString());
                currentEmployeeJSON.put("quality", currentEmployee.getQuality().toString());
                
                workPair.put("shift", currentShiftJSON);
                workPair.put("employee", currentEmployeeJSON);
                workPairs.add(workPair);
            }
            
            currentDay.put("work pairs", workPairs);
            dailySchedulesJSON.add(currentDay);
        }
        weekScheduleJSON.put("daily schedules", dailySchedulesJSON);
        return weekScheduleJSON;
    }
    
    
        //Currently used to write employee data stored in memory into a JSON file.
    public void JSONWriter(Employee employeeToWrite, String fileName)
    {
                 
        JSONObject employeesJSON = employeesToJSON(employeeToWrite, fileName);
                 
        if (employeesJSON != null)
        {
                 //Get write handle right before because it destroys everything
                 try 
                 {
                    FileWriter employeeWriteHandle = new FileWriter("./" + fileName + ".JSON", false );
                    employeeWriteHandle.write(employeesJSON.toJSONString());    
                 }
                 catch(Exception e)
                 {
                 }
        }
            
                 
               
    }
    
     //Calls JSONWriter with the default file name
    public void JSONWriter(Employee employeeToWrite)
    {
        JSONWriter(employeeToWrite, "Employees");
    }
    

    
    public void JSONWriter(Schedule scheduleToWrite)
    {

        JSONObject weekScheduleJSON = scheduleToJSON(scheduleToWrite);
            
        //Get write handle right before because it destroys everything
        try (FileWriter employeeWriteHandle = new FileWriter("./" + scheduleToWrite.getName() + ".JSON", false ))
        {
           employeeWriteHandle.write(weekScheduleJSON.toJSONString());    
        }
        catch(Exception e)
        {
        }
    }
    
    public ArrayList<Employee> JSONReader(ArrayList<Employee> employees, String fileName)
    {
        
        JSONParser parser = new JSONParser();

        Object obj = null;
       
        
        try
        {
            FileReader JSONFile = new FileReader("./" + fileName + ".JSON");

            //Parses the file
            obj = parser.parse(JSONFile);
        }
        catch (FileNotFoundException e)
        {
            System.out.print("Could not find file: ./" + fileName + ".JSON to read\nAssuming no employees exist." );
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        } 
        catch (ParseException e)
        {
            System.out.print("Fle: ./" + fileName + ".JSON contains bad JSON data.");
        }

        if (obj != null)
        {
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

        return employees;
    }
    
    
    public Schedule parseScheduleJSON(Object obj)
    {
        Schedule currentSchedule = new Schedule();

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

            //From each schedule, get the workpairs object and store them.
            JSONArray workPairsJSON = (JSONArray) currentScheduleJSON.get("work pairs");
            Iterator<JSONObject> workPairsIterator = workPairsJSON.iterator();

            //Make a map to keep track of individual day's shift-employee pairs
            Map<Shift, Employee> currentWorkPairs = new HashMap<>();

            while(workPairsIterator.hasNext())
            {
                //Get the array of the array of shift-employee pairs
                JSONObject currentWorkPairJSON = (JSONObject) workPairsIterator.next();

                //Get each shift-employee array, and parse it
                JSONObject currentShiftJSON = (JSONObject)currentWorkPairJSON.get("shift");
                JSONObject currentEmployeeJSON = (JSONObject) currentWorkPairJSON.get("employee");

                //Make employee and shift objects
                Employee currentEmployee = new Employee();
                Shift currentShift = new Shift();

                //Assign Employee Stuff
                currentEmployee.setFirstName( (String) currentEmployeeJSON.get("first name") );
                currentEmployee.setLastName( (String) currentEmployeeJSON.get("last name") );
                currentEmployee.setHourlyWage( Double.parseDouble( (String) currentEmployeeJSON.get("hourly wage") ) );
                currentEmployee.setQuality( Integer.parseInt( (String) currentEmployeeJSON.get("quality") ) );

                //Assign Shift Stuff
                currentShift.setStartTime( (String) currentShiftJSON.get("start time") );
                currentShift.setEndTime( (String) currentShiftJSON.get("end time") );

                //Add the employee and shift to the map
                currentWorkPairs.put(currentShift, currentEmployee);

            }
            //Add the generated map to the list of maps
            dailySchedules.add(currentWorkPairs);
        }
        //set the completed list of maps to be the schedule
        currentSchedule.setSchedule(dailySchedules);
        return currentSchedule;
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

        try (FileReader JSONFile = new FileReader("./" + scheduleName + ".JSON"))
        {
            Object obj = parser.parse(JSONFile);
            return parseScheduleJSON(obj);
            
        }   
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
