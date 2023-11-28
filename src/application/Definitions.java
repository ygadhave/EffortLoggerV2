package application;

import java.io.Serializable;

// made by Troy Reiling

public class Definitions implements Serializable {
	private static final long serialVersionUID = 1L;
    private String cycleName;
    private String effortCategory;
    private String deliverable;

    public Definitions() {
        this.cycleName = "New Cycle";
        this.effortCategory = "";
        this.deliverable = "";
    }

    public Definitions(String cycleName, String effortCategory, String deliverable) {
        this.cycleName = cycleName;
        this.effortCategory = effortCategory;
        this.deliverable = deliverable;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public String getEffortCategory() {
        return effortCategory;
    }

    public void setEffortCategory(String effortCategory) {
        this.effortCategory = effortCategory;
    }

    public String getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(String deliverable) {
        this.deliverable = deliverable;
    }
}
