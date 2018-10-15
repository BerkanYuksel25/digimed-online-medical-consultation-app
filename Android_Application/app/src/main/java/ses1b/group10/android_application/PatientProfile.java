package ses1b.group10.android_application;

public class PatientProfile {
    private String patientFirstName;
    private String patientFamilyName;
    private String patientDOB;
    private String patientWeight;
    private String patientHeight;
    private String patientMedCon;
    private String patientGender;
    private String patientImage;


    PatientProfile(String patientFirstName, String patientFamilyName, String patientWeight, String patientHeight, String patientMedCon, String patientDOB, String patientGender, String patientImage) {
        this.patientFirstName = patientFirstName;
        this.patientFamilyName = patientFamilyName;
        this.patientDOB = patientDOB;
        this.patientWeight = patientWeight;
        this.patientHeight = patientHeight;
        this.patientMedCon = patientMedCon;
        this.patientGender = patientGender;
        this.patientImage =patientImage;

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

//    public int getPatientAge() {
//        return patientAge;
//    }
//
//    public void setPatientAge(int patientAge) {
//        this.patientAge = patientAge;
//    }

    public String getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(String  patientWeight) {
        this.patientWeight = patientWeight;
    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public void setPatientHeight(String patientHeight) {
        this.patientHeight = patientHeight;
    }

    public String getPatientMedCon() {
        return patientMedCon;
    }

    public void setPatientMedCon(String patientMedCon) {
        this.patientMedCon = patientMedCon;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }



    public String getPatientDOB() {
        return patientDOB;
    }

    public void setPatientDOB(String patientDOB) {
        this.patientDOB = patientDOB;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public PatientProfile() {
    }
}
