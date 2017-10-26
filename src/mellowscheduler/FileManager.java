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
import java.util.EnumMap;
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
        
    public ArrayList<Employee> addUniqueEmployee(Employee employeeToWrite, ArrayList<Employee> employees)
    {
        if(employeeToWrite != null)
        {
        //Make sure new employee is not already in system
            for (Employee currentEmployee : employees)
            {
                //If they are equal...
                if (currentEmployee.equals(employeeToWrite))
                {

                    //TODO: make this return some message to let user know employee already existed.
                    return null;
                }
                //But they do have the same name...
                else if(currentEmployee.getFirstName().equals(employeeToWrite.getFirstName())
                            && currentEmployee.getLastName().equals(employeeToWrite.getLastName()))
                    {
                        //Need to remove the old employee so that the information can be updated
                        employees.remove(employees.indexOf(currentEmployee));
                        break;
                    }
            }
        }
        else
        {
            return null;
        }

        employees.add(employeeToWrite);
        return employees;
    }
    

    public JSONObject employeesToJSON(ArrayList<Employee> employees)
    {
        //Make container for employees
        JSONObject employeesJSON = new JSONObject();

        //Make list for employees and add employee JSONs to it
        JSONArray employeeJSONs = new JSONArray();

            for (Employee currentEmployee : employees)
            {
                //Make employee into JSON object, and add to list.
                JSONObject newJSON = new JSONObject();

                newJSON.put("first name", currentEmployee.getFirstName());
                newJSON.put("last name", currentEmployee.getLastName());
                newJSON.put("hourly wage", currentEmployee.getHourlyWage().toString());
                newJSON.put("quality", currentEmployee.getQuality().toString());

                
                JSONArray allUnavailabilities = new JSONArray();
                ArrayList< ArrayList<Shift>> unavailability = currentEmployee.getUnavailable();
                for(int ii = 0; ii < 7; ii++)
                {
                    JSONObject currentDayJSON = new JSONObject();
                    ArrayList<Shift> dayShifts = unavailability.get(ii);
                    
                    JSONArray dayShiftsJSON = new JSONArray();
                    for(Shift currentShift : dayShifts)
                    {
                        JSONObject shiftJSON = new JSONObject();
                        
                        shiftJSON.put("start time", currentShift.getStartTime());
                        shiftJSON.put("end time", currentShift.getEndTime());
                        
                        dayShiftsJSON.add(shiftJSON);
                    }
                    
                    currentDayJSON.put("shifts", dayShiftsJSON);
                    allUnavailabilities.add(currentDayJSON);
                }
                
                newJSON.put("unavailable", allUnavailabilities);
                
                employeeJSONs.add(newJSON);
            }

         employeesJSON.put("employees", employeeJSONs);
        return employeesJSON;
    }    

    public JSONObject employeesToJSON(String fileName)
    {
        //Get all currently written employees before we destroy them.
        ArrayList<Employee> employees = new ArrayList<>();
        JSONReader(employees, fileName);


        //Make container for employees
        JSONObject employeesJSON = new JSONObject();

        //Make list for employees and add employee JSONs to it
        JSONArray employeeJSONs = new JSONArray();

            for (Employee currentEmployee : employees)
            {
                //Make employee into JSON object, and add to list.
                JSONObject newJSON = new JSONObject();

                newJSON.put("first name", currentEmployee.getFirstName());
                newJSON.put("last name", currentEmployee.getLastName());
                newJSON.put("hourly wage", currentEmployee.getHourlyWage().toString());
                newJSON.put("quality", currentEmployee.getQuality().toString());

                
                JSONArray allUnavailabilities = new JSONArray();
                ArrayList< ArrayList<Shift>> unavailability = currentEmployee.getUnavailable();
                for(int ii = 0; ii < 7; ii++)
                {
                    JSONObject currentDayJSON = new JSONObject();
                    ArrayList<Shift> dayShifts = unavailability.get(ii);
                    
                    JSONArray dayShiftsJSON = new JSONArray();
                    for(Shift currentShift : dayShifts)
                    {
                        JSONObject shiftJSON = new JSONObject();
                        
                        shiftJSON.put("start time", currentShift.getStartTime());
                        shiftJSON.put("end time", currentShift.getEndTime());
                        
                        dayShiftsJSON.add(shiftJSON);
                    }
                    
                    currentDayJSON.put("shifts", dayShiftsJSON);
                    allUnavailabilities.add(currentDayJSON);
                }
                
                newJSON.put("unavailable", allUnavailabilities);
                
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
    
    
//    //Currently used to write employee data stored in memory into a JSON file.
//    public void JSONWriter(Employee employeeToWrite, String fileName)
//    {
//        ArrayList<>
//        JSONObject employeesJSON = employeesToJSON(employeeToWrite, fileName);
//                 
//        if (employeesJSON != null)
//        {
//                 //Get write handle right before because it destroys everything
//                 try 
//                 {
//                    FileWriter employeeWriteHandle = new FileWriter("./" + fileName + ".JSON", false );
//                    String wtf = employeesJSON.toJSONString();
//                    employeeWriteHandle.write(employeesJSON.toJSONString());
//                    employeeWriteHandle.close();
//                 }
//                 catch(Exception e)
//                 {
//                     e.printStackTrace();
//                 }
//        }
//    }
    
    //Currently used to write employee data stored in memory into a JSON file.
    public void JSONWriter(JSONObject employeesJSON, String fileName)
    {
                 
        if (employeesJSON != null)
        {
                 //Get write handle right before because it destroys everything
                 try 
                 {
                    FileWriter employeeWriteHandle = new FileWriter("./" + fileName + ".JSON", false );
                    String wtf = employeesJSON.toJSONString();
                    employeeWriteHandle.write(employeesJSON.toJSONString());
                    employeeWriteHandle.close();
                 }
                 catch(Exception e)
                 {
                     e.printStackTrace();
                 }
        }
    }
    
     //Calls JSONWriter with the default file name
    public void JSONWriter(Employee employeeToWrite)
    {
        //Read all the employees
        ArrayList<Employee> employees = JSONReader(new ArrayList<Employee>(), "Employees");
        
        //Add this new employee to the list if it is unique
        employees = addUniqueEmployee(employeeToWrite, employees);
        
        //Call writer
        JSONWriter(employeesToJSON(employees), "Employees");
    }
    
    //Calls JSONWriter with the default file name
    public void JSONWriter(ArrayList<Employee> employees)
    {
        //Call writer
        JSONWriter(employeesToJSON(employees), "Employees");
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
            System.out.print("Could not find file: ./" + fileName + ".JSON to read. Assuming no employees exist.\n" );
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        } 
        catch (ParseException e)
        {
            System.out.print("File: ./" + fileName + ".JSON contains bad JSON data.");
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

                //For the unavailable daily Shift list...
                JSONArray unavailableJSON = (JSONArray) currentEmployeeJSON.get("unavailable");
                Iterator<JSONObject> unavailableIterator = unavailableJSON.iterator();
                
                //Make a List to store the daily shifts lists
                ArrayList<ArrayList<Shift>> allShiftLists = new ArrayList<>();
                
                //Iterate over each day's list
                while(unavailableIterator.hasNext())
                {
                    JSONObject currentDayJSON = (JSONObject) unavailableIterator.next();

                    //For each day's list...
                    ArrayList<Shift> shiftList = new ArrayList<>();
                    JSONArray shiftArrayJSON = (JSONArray) currentDayJSON.get("shifts");
                    Iterator<JSONObject> shiftsIterator = shiftArrayJSON.iterator();
                    
                    //Iterate over each shift
                    while(shiftsIterator.hasNext())
                    {
                        //Get the Shift object JSON
                        JSONObject shiftJSON = (JSONObject) shiftsIterator.next();
                        Shift currentShift = new Shift();
                        
                        //Store in Shift object
                        currentShift.setStartTime((String) shiftJSON.get("start time"));
                        currentShift.setEndTime((String) shiftJSON.get("end time"));
                        
                        //Add shift to day's list
                        shiftList.add(currentShift);
                    }
                    //Add day's list to week's list
                    allShiftLists.add(shiftList);
                }
                
                currentEmployee.setUnavailable(allShiftLists);
                
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


