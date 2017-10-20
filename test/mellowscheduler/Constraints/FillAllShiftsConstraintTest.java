/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler.Constraints;

import mellowscheduler.Schedule;
import mellowscheduler.TestDataMaker;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Alex McClellan
 */
public class FillAllShiftsConstraintTest {
    
    public FillAllShiftsConstraintTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of satisfied method, of class FillAllShiftsConstraint.
     */
    @Test
    public void testSatisfied_BadSchedule() {
        Schedule testSchedule = TestDataMaker.makeUnderstaffedSchedule();
        
        Constraint fillSchedule = new FillAllShiftsConstraint();
        assertFalse(fillSchedule.satisfied(testSchedule));
        
    }
    
    /**
     * Test of satisfied method, of class FillAllShiftsConstraint.
     */
    @Test
    public void testSatisfied_GoodSchedule() {
        Schedule testSchedule = TestDataMaker.makeGenericSchedule();
        
        Constraint fillSchedule = new FillAllShiftsConstraint();
        assertTrue(fillSchedule.satisfied(testSchedule));
    }
    
}
