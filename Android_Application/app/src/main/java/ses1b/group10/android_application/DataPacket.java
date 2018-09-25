package ses1b.group10.android_application;

public class DataPacket {
    private String messageTitle;
    private String textMessage;
    private String file;
    private String heartRate;
    private String date;
    private String time;


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    private String video;

    public DataPacket(String messageTitle, String textMessage, String date, String time, String video, String heartRate) {
        this.messageTitle = messageTitle;
        this.textMessage = textMessage;
        this.date = date;
        this.time = time;
        this.video = video;
        this.heartRate=heartRate;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getText() {
        return textMessage;
    }

    public void setText(String textMessage) {
        this.textMessage = textMessage;
    }



    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartrate) {
        this.heartRate = heartrate;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
}