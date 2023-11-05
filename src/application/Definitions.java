package application;

public class Definitions {
	private String exampleDefinition;
	private String[] exampleTaskTypeArray = new String[3];
	
	public Definitions() {
		exampleDefinition = "Example Default Definition";
		
		exampleTaskTypeArray[0] = "Example Default Task Type 1";
		exampleTaskTypeArray[1] = "Example Default Task Type 2";
		exampleTaskTypeArray[2] = "Example Default Task Type 3";
	}
	
	public String GetExampleDefinition() {
		return exampleDefinition;
	}
	
	public void SetExampleDefinition(String definition) {
		exampleDefinition = definition;
	}
	
	public String GetExampleTaskType(int index) {
		return exampleTaskTypeArray[index];
	}
	
	public void SetExampleTaskType(int index, String type) {
		exampleTaskTypeArray[index] = type;
	}
}
