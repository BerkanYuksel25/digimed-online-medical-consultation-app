package ses1b.group10.android_application;

public class DoctorProfile {
    private String  docFirstName;
    private String docFamilyName;
    private String docImage;
    private String DOB;
    private String docOccupation;
    private String gender;
    private String height;
    private String weight;

    public String getDocFirstName() {
        return docFirstName;
    }


    public void setDocFirstName(String docFirstName) {
        this.docFirstName = docFirstName;
    }

    public String getDocFamilyName() {
        return docFamilyName;
    }

    public void setDocFamilyName(String docFamilyName) {
        this.docFamilyName = docFamilyName;
    }

    public String getDocImage() {
        return docImage;
    }

    public void setDocImage(String docImage) {
        this.docImage = docImage;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getDocOccupation() {
        return docOccupation;
    }

    public void setDocOccupation(String docOccupation) {
        this.docOccupation = docOccupation;
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

    public DoctorProfile(String docFirstName, String docFamilyName, String docImage, String DOB, String docOccupation, String gender, String height, String weight) {
        this.docFirstName = docFirstName;
        this.docFamilyName = docFamilyName;
        this.docImage = docImage;
        this.DOB = DOB;
        this.docOccupation = docOccupation;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }
    public DoctorProfile() {


    }
}
