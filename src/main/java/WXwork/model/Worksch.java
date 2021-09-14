
package WXwork.model;

public class Worksch {

    private String wkcalendar;
    private String shift;
    private String weekday;

    public Worksch() {
    }

    public String getWeekday() {
        return this.weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getWkcalendar() {
        return this.wkcalendar;
    }

    public void setWkcalendar(String wkcalendar) {
        this.wkcalendar = wkcalendar;
    }

    public String getShift() {
        return this.shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
