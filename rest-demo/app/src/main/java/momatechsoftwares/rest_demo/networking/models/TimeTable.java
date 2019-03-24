package momatechsoftwares.rest_demo.networking.models;

public class TimeTable {

    private String subjectName;
    private String className;
    private String period;
    private String link;

    public TimeTable() {
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "subjectName='" + subjectName + '\'' +
                ", className='" + className + '\'' +
                ", period='" + period + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
