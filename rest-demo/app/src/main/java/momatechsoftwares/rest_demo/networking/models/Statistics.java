package momatechsoftwares.rest_demo.networking.models;

import java.io.Serializable;

public class Statistics implements Serializable{

    private String subjectName;
    private String className;
    private String percentage;
    private int progress;

    public Statistics() {
    }

    public Statistics(String subjectName, String className, String percentage, int progress) {
        this.subjectName = subjectName;
        this.className = className;
        this.percentage = percentage;
        this.progress = progress;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "subjectName='" + subjectName + '\'' +
                ", className='" + className + '\'' +
                ", percentage='" + percentage + '\'' +
                ", progress=" + progress +
                '}';
    }
}
