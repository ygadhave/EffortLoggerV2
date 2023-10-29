package application;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

// Planning poker estimate generation test cases by Donovan Harp

public class EstimateGenerationTestCases {
	
	public Database database = new Database();
	public ConsoleManager consoleManager = new ConsoleManager(database);
	public PlanningPokerManager planningPokerManager = new PlanningPokerManager(database);
	ArrayList<EffortLog> logs;
	public final int STORY_POINTS_PER_HOUR = 60;
	
	public int calculate() {
		logs = planningPokerManager.GetEffortLogs();
		return planningPokerManager.CalculateAverage(logs, STORY_POINTS_PER_HOUR);
	}
	
	// No logs
	@Test
	public void TC1_1() {
		assertEquals(calculate(), 0);
		consoleManager.ClearEffortLogs();
	}
	
	// Single log, hours only
	@Test
	public void TC2_1() {
		consoleManager.CreateNewEffortLog(3, 0, 1, 0);
		logs = planningPokerManager.GetEffortLogs();
		assertEquals(calculate(), 180);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC2_2() {
		consoleManager.CreateNewEffortLog(1, 0, 1, 0);
		assertEquals(calculate(), 60);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC2_3() {
		consoleManager.CreateNewEffortLog(300, 0, 1, 0);
		assertEquals(calculate(), 18000);
		consoleManager.ClearEffortLogs();
	}
	
	// Single log, minutes only
	@Test
	public void TC3_1() {
		consoleManager.CreateNewEffortLog(0, 30, 1, 0);
		assertEquals(calculate(), 30);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC3_2() {
		consoleManager.CreateNewEffortLog(0, 1, 1, 0);
		assertEquals(calculate(), 1);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC3_3() {
		consoleManager.CreateNewEffortLog(0, 300, 1, 0);
		assertEquals(calculate(), 300);
		consoleManager.ClearEffortLogs();
	}
	
	// Single log, hours and minutes
	@Test
	public void TC4_1() {
		consoleManager.CreateNewEffortLog(3, 30, 1, 0);
		assertEquals(calculate(), 210);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC4_2() {
		consoleManager.CreateNewEffortLog(1, 1, 1, 0);
		assertEquals(calculate(), 61);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC4_3() {
		consoleManager.CreateNewEffortLog(300, 300, 1, 0);
		assertEquals(calculate(), 18300);
		consoleManager.ClearEffortLogs();
	}
	
	// Single log, weights and biases
	@Test
	public void TC5_1() {
		consoleManager.CreateNewEffortLog(3, 30, 2, 10);
		assertEquals(calculate(), 440);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC5_2() {
		consoleManager.CreateNewEffortLog(3, 30, 1, 10);
		assertEquals(calculate(), 220);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC5_3() {
		consoleManager.CreateNewEffortLog(3, 30, 2, 0);
		assertEquals(calculate(), 420);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC5_4() {
		consoleManager.CreateNewEffortLog(3, 30, 1, -200);
		assertEquals(calculate(), 10);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC5_5() {
		consoleManager.CreateNewEffortLog(3, 30, 1, -400);
		assertEquals(calculate(), 0);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC5_6() {
		consoleManager.CreateNewEffortLog(3, 30, 100, 10);
		assertEquals(calculate(), 22000);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC5_7() {
		consoleManager.CreateNewEffortLog(3, 30, 0.5, 10);
		assertEquals(calculate(), 110);
		consoleManager.ClearEffortLogs();
	}
	
	// Weird log parameters
	@Test
	public void TC6_1() {
		consoleManager.CreateNewEffortLog(0, 0, 0.5, 0);
		assertEquals(calculate(), 0);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC6_2() {
		consoleManager.CreateNewEffortLog(-1, -1, -2, -1);
		assertEquals(calculate(), 120);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC6_3() {
		consoleManager.CreateNewEffortLog(0, 0, 0, 0);
		assertEquals(calculate(), 0);
		consoleManager.ClearEffortLogs();
	}
	
	
	// Multiple Logs
	@Test
	public void TC7_1() {
		consoleManager.CreateNewEffortLog(3, 30, 2, 10);
		consoleManager.CreateNewEffortLog(3, 30, 2, 10);
		consoleManager.CreateNewEffortLog(3, 30, 2, 10);
		assertEquals(calculate(), 440);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC7_2() {
		consoleManager.CreateNewEffortLog(3, 30, 2, 10);   // 440
		consoleManager.CreateNewEffortLog(4, 20, 0.5, 30); // 145
		consoleManager.CreateNewEffortLog(1, 5, 5, -2);    // 315
		assertEquals(calculate(), 300);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC7_3() {
		consoleManager.CreateNewEffortLog(3, 30, 2, 10);   // 440
		consoleManager.CreateNewEffortLog(4, 23, 0.5, 30); // 146.5
		consoleManager.CreateNewEffortLog(1, 5, 5, -2);    // 315
		assertEquals(calculate(), 300);
		consoleManager.ClearEffortLogs();
	}
	@Test
	public void TC7_4() {
		for (int i = 0; i < 100; i++) {
			consoleManager.CreateNewEffortLog(3, 30, 2, 10);
		}
		assertEquals(calculate(), 440);
		consoleManager.ClearEffortLogs();
	}
}
