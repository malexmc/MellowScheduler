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
import java.util.Iterator;
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

                        newJSON.put("First Name", employeeToWrite.getFirstName());
                        newJSON.put("Last Name", employeeToWrite.getLastName());
                        newJSON.put("Hourly Wage", employeeToWrite.getHourlyWage().toString());
                        newJSON.put("Quality", employeeToWrite.getQuality().toString());

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
            
                currentEmployee.setFirstName( (String) currentEmployeeJSON.get("First Name") );
                currentEmployee.setLastName( (String) currentEmployeeJSON.get("Last Name") );
                currentEmployee.setHourlyWage( Double.parseDouble( (String) currentEmployeeJSON.get("Hourly Wage") ) );
                currentEmployee.setQuality( Integer.parseInt( (String) currentEmployeeJSON.get("Quality") ) );

                employees.add(currentEmployee);
            }
            

        }

        catch (Exception e)
        {
            e.printStackTrace();
        } 


        return employees;
    }
    
}
