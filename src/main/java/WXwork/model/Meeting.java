
package WXwork.model;

public class Meeting {
    private String title;
    private String creemp;
    private String addr;
    private String password;
    private String starttime;
    private String endtime;

    public Meeting() {
    }

    public String getCreemp() {
        return this.creemp;
    }

    public void setCreemp(String creemp) {
        this.creemp = creemp;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStarttime() {
        return this.starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return this.endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
