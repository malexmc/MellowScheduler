/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler.Constraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mellowscheduler.Employee;
import mellowscheduler.Schedule;
import mellowscheduler.Shift;
import mellowscheduler.TestDataMaker;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Alex
 */
public class EmployeeAvailabilityConstraintTest {
    
    public EmployeeAvailabilityConstraintTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of satisfied method, of class EmployeeAvailabilityConstraint.
     */
    @Test
    public void testSatisfied_BadSchedule() {
        ArrayList<Map<Shift, Employee>> newInnerSchedule = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            Map<Shift, Employee> badShift= new HashMap<>();
            badShift.put(TestDataMaker.makeOpeningShift(), TestDataMaker.makeAlBundy());
            newInnerSchedule.add(badShift);
        }
        
        Schedule testSchedule = new Schedule();
        testSchedule.setSchedule(newInnerSchedule);
        
        EmployeeAvailabilityConstraint constraint = new EmployeeAvailabilityConstraint();
        assertFalse(constraint.satisfied(testSchedule));
    }
    
        /**
     * Test of satisfied method, of class EmployeeAvailabilityConstraint.
     */
    @Test
    public void testSatisfied_GoodSchedule() {
        ArrayList<Map<Shift, Employee>> newInnerSchedule = new ArrayList<>();
        
        for(int ii = 0; ii < 7; ii++)
        {
            Map<Shift, Employee> badShift= new HashMap<>();
            badShift.put(TestDataMaker.makeOpeningShift(), TestDataMaker.makeBillyOvertime());
            newInnerSchedule.add(badShift);
        }
        
        Schedule testSchedule = new Schedule();
        testSchedule.setSchedule(newInnerSchedule);
        
        EmployeeAvailabilityConstraint constraint = new EmployeeAvailabilityConstraint();
        assertTrue(constraint.satisfied(testSchedule));
    }
    
}
