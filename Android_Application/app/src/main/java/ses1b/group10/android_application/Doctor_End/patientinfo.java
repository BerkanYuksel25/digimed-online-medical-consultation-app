package ses1b.group10.android_application.Doctor_End;

public class patientinfo {

    private String  patientFirstName;
    private String patientFamilyName;
    private String patientImage;
    private String DOB;
    private String patientMedCon;
    private String gender;
    private String height;
    private String weight;

    public patientinfo(String patientFirstName, String patientFamilyName, String patientImage, String DOB, String patientMedCon, String gender, String height, String weight) {
        this.patientFirstName = patientFirstName;
        this.patientFamilyName = patientFamilyName;
        this.patientImage = patientImage;
        this.DOB = DOB;
        this.patientMedCon = patientMedCon;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientFamilyName() {
        return patientFamilyName;
    }

    public void setPatientFamilyName(String patientFamilyName) {
        this.patientFamilyName = patientFamilyName;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPatientMedCon() {
        return patientMedCon;
    }

    public void setPatientMedCon(String patientMedCon) {
        this.patientMedCon = patientMedCon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public patientinfo() {
    }
}
