/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Alex
 */
public class FileManager {
    
    //Currently used to write employee data stored in memory into a JSON file.
    //TODO: Save JSON data intelligently so that employees are not duplicated
    public void JSONWriter(Employee employeeToWrite)
    {
        //TODO: Make directory dynamic
        try (FileWriter file = new FileWriter("C:\\Users\\Alex\\Documents\\Employees.JSON", true) ) 
        {
                
                JSONObject newJSON = new JSONObject();
                
                newJSON.put("First Name", employeeToWrite.getFirstName());
                newJSON.put("Last Name", employeeToWrite.getLastName());
                newJSON.put("Hourly Wage", employeeToWrite.getHourlyWage().toString());
                newJSON.put("Quality", employeeToWrite.getQuality().toString());
                
                file.write(newJSON.toJSONString());
                System.out.println("Successfully Copied JSON Object to File...");
                System.out.println("\nJSON Object: " + newJSON);
        }
        catch (Exception e)
        {
                    
        }
    }
    
    public ArrayList<Employee> JSONReader(ArrayList<Employee> employees)
    {
        
        JSONParser parser = new JSONParser();

        try 
        {
            
            //Parses one employee JSON object and adds it to the employees array
            Object obj = parser.parse(new FileReader("C:\\Users\\Alex\\Documents\\Employees.JSON"));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            Employee currentEmployee = new Employee();
            
            currentEmployee.setFirstName( (String) jsonObject.get("First Name") );
            currentEmployee.setLastName( (String) jsonObject.get("Last Name") );
            currentEmployee.setHourlyWage( (Double) jsonObject.get("Hourly Wage") );
            currentEmployee.setQuality( (Integer) jsonObject.get("Quality") );

            employees.add(currentEmployee);
        }
//            String name = (String) jsonObject.get("name");
//            System.out.println(name);
//
//            long age = (Long) jsonObject.get("age");
//            System.out.println(age);
//
//            // loop array
//            JSONArray msg = (JSONArray) jsonObject.get("messages");
//            Iterator<String> iterator = msg.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        return employees;
    }
    
}
