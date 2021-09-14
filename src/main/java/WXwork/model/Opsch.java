
package WXwork.model;

public class Opsch {

    private String sch_dtime;
    private String patient_id;
    private String patient_name;
    private String dept;
    private String sequence;
    private String surgeon;
    private String assistant;
    private String room;
    private String opdept;
    private String weekday;
    private String supnurse;
    private String opnurse;
    private String opname;
    private String bedno;

    public Opsch() {
    }

    public String getBedno() {
        return bedno;
    }

    public void setBedno(String bedno) {
        this.bedno = bedno;
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getOpnurse() {
        return opnurse;
    }

    public void setOpnurse(String opnurse) {
        this.opnurse = opnurse;
    }

    public String getSupnurse() {
        return supnurse;
    }

    public void setSupnurse(String supnurse) {
        this.supnurse = supnurse;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getWeekday() {
        return this.weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getSch_dtime() {
        return this.sch_dtime;
    }

    public void setSch_dtime(String sch_dtime) {
        this.sch_dtime = sch_dtime;
    }

    public String getPatient_id() {
        return this.patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return this.patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDept() {
        return this.dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSurgeon() {
        return this.surgeon;
    }

    public void setSurgeon(String surgeon) {
        this.surgeon = surgeon;
    }

    public String getAssistant() {
        return this.assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getOpdept() {
        return this.opdept;
    }

    public void setOpdept(String opdept) {
        this.opdept = opdept;
    }
}
