package application;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/* 
 * Author: Reese Johnson     add comments below as per canvas
 * ************************
 * contain internal comments that explain how the implementation aligns with
 * the design and comments that explain how the results demonstrate the design
 * goal for this prototype has been satisfied.
 * ************************
 */

/**
 * Test class for log viewing with different numbers of logs
 */
public class LogGenerationTestCases {

    private Database database;                  
    private ArrayList<EffortLog> logs;  
    private Random random;

    /**
     * Initializes the database, log list, and random before each test case
     */
    @Before
    public void setUp() {
        database = new Database();
        logs = database.GetEffortLogs();
        random = new Random();
    }

    /**
     * Adds a specified number of random logs to the logs list to simulate 
     * a project, user story within a project, etc.
     * 
     * @param numberOfLogs The number of logs to add
     */
    private void addRandomLogs(int numberOfLogs) {
        for (int i = 0; i < numberOfLogs; i++) {
            int hours = random.nextInt(10);  // Random hours up to 10
            int minutes = random.nextInt(60);  // Random minutes up to 60
            double weight = random.nextDouble();  // Random weight
            int bias = random.nextInt(5);  // Random bias up to 5
            EffortLog log = new EffortLog(hours, minutes, weight, bias);
            logs.add(log);
        }
    }

    /**
     * Display certain data in the logs depending on the number of logs
     * 
     * @return A string containing the data in the logs
     */
    private String displayLogs() {
        StringBuilder output = new StringBuilder();
        if (logs.size() < 3) {
            for (EffortLog log : logs) {
                output.append("Story Points: " + log.GetStoryPoints() + "\\n");
            }
        } else if (logs.size() < 10) {
            for (EffortLog log : logs) {
                output.append("Weight: " + log.GetWeight() + ", Bias: " + log.GetBias() + ", Story Points: " + log.GetStoryPoints() + "\\n");
            }
        } else {
            for (EffortLog log : logs) {
                output.append("Hours: " + log.GetHours() + ", Minutes: " + log.GetMinutes() + ", Weight: " + log.GetWeight() + ", Bias: " + log.GetBias() + ", Story Points: " + log.GetStoryPoints() + "\\n");
            }
        }
        return output.toString();
    }

    
    
    // Test cases:
    
    // Note, the numbers 3 and 10 in terms of the number of logs are 
    // simply place holders for this test. 
    
    
    // Tests the data displayed for zero logs.
    @Test
    public void testZeroLogs() {
        String output = displayLogs();
        assertTrue(output.isEmpty());
    }
    
    
    // Tests the data displayed for 1 to 3 logs
    @Test
    public void testFewerThanThreeLogs() {
        addRandomLogs(2);
        String output = displayLogs();
        assertTrue(output.contains("Story Points"));
        assertFalse(output.contains("Weight"));
        assertFalse(output.contains("Bias"));
        assertFalse(output.contains("Hours"));
        assertFalse(output.contains("Minutes"));
    }

    // Tests the data displayed for between 3 and 9 logs.
    @Test
    public void testThreeToNineLogs() {
        addRandomLogs(5);
        String output = displayLogs();
        assertTrue(output.contains("Story Points"));
        assertTrue(output.contains("Weight"));
        assertTrue(output.contains("Bias"));
        assertFalse(output.contains("Hours"));
        assertFalse(output.contains("Minutes"));
    }

    // Tests the data displayed for 10 or more logs.
    @Test
    public void testTenOrMoreLogs() {
        addRandomLogs(12);
        String output = displayLogs();
        assertTrue(output.contains("Story Points"));
        assertTrue(output.contains("Weight"));
        assertTrue(output.contains("Bias"));
        assertTrue(output.contains("Hours"));
        assertTrue(output.contains("Minutes"));
    }


    // Tests the edge case of a negative number being found
    @Test
    public void testNegativeNumberOfLogs() {
        addRandomLogs(-5);
        String output = displayLogs();
        assertTrue(output.isEmpty());
    }

    // Tests the display for a large number of logs.
    @Test
    public void testLargeNumberOfLogs() {
        addRandomLogs(10000);
        String output = displayLogs();
        assertTrue(output.contains("Story Points"));
        assertTrue(output.contains("Weight"));
        assertTrue(output.contains("Bias"));
        assertTrue(output.contains("Hours"));
        assertTrue(output.contains("Minutes"));
    }
}

