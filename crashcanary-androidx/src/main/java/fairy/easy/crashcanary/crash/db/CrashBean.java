package fairy.easy.crashcanary.crash.db;


import java.io.Serializable;

public class CrashBean implements Serializable {
    private String startTime;

    private String normalError;

    private String detailedError;

    private int id;

    @Override
    public String toString() {
        return "CrashBean{" +
                "startTime='" + startTime + '\'' +
                ", normalError='" + normalError + '\'' +
                ", detailedError='" + detailedError + '\'' +
                ", id=" + id +
                '}';
    }


    public CrashBean(String startTime, String normalError, String detailedError, int id) {
        this.startTime = startTime;
        this.normalError = normalError;
        this.detailedError = detailedError;
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNormalError() {
        return normalError;
    }

    public void setNormalError(String normalError) {
        this.normalError = normalError;
    }

    public String getDetailedError() {
        return detailedError;
    }

    public void setDetailedError(String detailedError) {
        this.detailedError = detailedError;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
