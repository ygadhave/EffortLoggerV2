package application;

public class DefectLog {
	// Data attributes
	private int defectHours;
	private int defectMinutes;
	private double weight;
	private int bias;
	private int storyPoints;
	private boolean selected;
	private String defectCategory;
	private String defectName;
	private String stepWhenInjected;
    private String stepWhenRemoved;
    private String defectSymptoms;
    private String fix;
    private boolean isClosed;

	// Default Constructor
	public DefectLog() {
		defectHours = 0;
		defectMinutes = 0;
		weight = 1;
		bias = 0;
		storyPoints = -1;
		selected = true;
		defectCategory = "";
		defectName = "";
		stepWhenInjected = "";
		stepWhenRemoved = "";
		defectSymptoms = "";
		fix = "";
	}

	// Basic Constructor
	public DefectLog(int hours, int minutes) {
		defectHours = Math.abs(hours);
		defectMinutes = Math.abs(minutes);
		weight = 1;
		bias = 0;
		storyPoints = -1;
		selected = true;
		defectCategory = "";
		defectName = "";
		stepWhenInjected = "";
		stepWhenRemoved = "";
		defectSymptoms = "";
		fix = "";
	}

	// Advanced Constructor
	public DefectLog(int hours, int minutes, double w, int b) {
		defectHours = Math.abs(hours);
		defectMinutes = Math.abs(minutes);
		weight = w;
		bias = b;
		storyPoints = -1;
		selected = true;
		defectCategory = "";
		defectName = "";
		stepWhenInjected = "";
		stepWhenRemoved = "";
		defectSymptoms = "";
		fix = "";
	}



	public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

	public String getDefectName() {
        return defectName;
    }

	public void setTime(int hours, int minutes) {
		defectHours = hours;
		defectMinutes = minutes;
	}

	public int getHours() {
		return defectHours;
	}

	public int getMinutes() {
		return defectMinutes;
	}

	public void setWeight(double w) {
		weight = w;
	}

	public double getWeight() {
		return weight;
	}

	public void setBias(int b) {
		bias = b;
	}

	public int getBias() {
		return bias;
	}

	public void setStoryPoints(int points) {
		storyPoints = points;
	}

	public int getStoryPoints() {
		return storyPoints;
	}

	public void setSelected(boolean s) {
		selected = s;
	}

	public boolean getSelected() {
		return selected;
	}

	public String getDefectCategory() {
        return defectCategory;
    }

    public void setDefectCategory(String defectCategory) {
        this.defectCategory = defectCategory;
    }

    public void setStepWhenInjected(String stepWhenInjected) {
        this.stepWhenInjected = stepWhenInjected;
    }

    public String getStepWhenInjected() {
        return stepWhenInjected;
    }

    public void setStepWhenRemoved(String stepWhenRemoved) {
        this.stepWhenRemoved = stepWhenRemoved;
    }

    public String getStepWhenRemoved() {
        return stepWhenRemoved;
    }

    public void setDefectSymptoms(String defectSymptoms) {
        this.defectSymptoms = defectSymptoms;
    }

    public String getDefectSymptoms() {
        return defectSymptoms;
    }

    public void setFix(String fix) {
    	this.fix = fix;
    }

    public String getFix() {
    	return fix;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public boolean getIsClosed() {
        return this.isClosed;
    }

}
