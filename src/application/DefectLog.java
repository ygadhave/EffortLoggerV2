package application;

import java.io.Serializable;

public class DefectLog implements Serializable {
	private static final long serialVersionUID = 1L;
	// Defect attributes
	private String defectName;
	private String defectCategory;
	private String stepWhenInjected;
    private String stepWhenRemoved;
    private String defectSymptoms;
    private String fix;
    private boolean isClosed;
    
    // Data attributes
	private int storyPoints;
	private boolean selected;


	// Default Constructor
	public DefectLog() {
		storyPoints = 0;
		selected = false;
		defectCategory = "";
		defectName = "";
		stepWhenInjected = "";
		stepWhenRemoved = "";
		defectSymptoms = "";
		fix = "";
	}

	// Basic Constructor
	public DefectLog(int points) {
		storyPoints = points;
		selected = false;
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

	public void setStoryPoints(int points) {
		if (points < 0) {
			System.out.println("Warning: Defect log story points cannot be less than zero. Setting to zero instead.");
			storyPoints = 0;
		}
		else {
			storyPoints = points;
		}
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

    public void setDefectCategory(String defectCategory) {
        this.defectCategory = defectCategory;
    }
    
	public String getDefectCategory() {
        return defectCategory;
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
