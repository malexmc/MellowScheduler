/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mellowscheduler;

import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
/**
 *
 * @author Alex
 */
public class MinWeeklyHoursConstraintTest {
    
    public MinWeeklyHoursConstraintTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of satisfied method, of class minWeeklyHoursConstraint.
     */
    @Test
    public void testSatisfied_GoodSchedule() {
        
        Schedule testSchedule = TestDataMaker.makeOvertimeSchedule();
        
        MinWeeklyHoursConstraint noLessThanFourty = new MinWeeklyHoursConstraint();
        
        noLessThanFourty.setHours(40);
        
        assertTrue(noLessThanFourty.satisfied(testSchedule));        
    }

    @Test
    public void testSatisfied_BadSchedule() {
        
        Schedule testSchedule = TestDataMaker.makeUndertimeSchedule();
        
        MinWeeklyHoursConstraint noLessThanFourty = new MinWeeklyHoursConstraint();
        
        noLessThanFourty.setHours(40);
        
        assertFalse(noLessThanFourty.satisfied(testSchedule));        
    }
    
}
