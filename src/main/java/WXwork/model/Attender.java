
package WXwork.model;

public class Attender extends User {

    private String signdate;
    private String mtpassword;

    public Attender() {
    }

    public String getEmpcode() {
        return super.mid;
    }

    public void setEmpcode(String empcode) {
        super.mid = empcode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        super.name = name;
    }

    public String getSigndate() {
        return this.signdate;
    }

    public void setSigndate(String signdate) {
        this.signdate = signdate;
    }

    public String getMtpassword() {
        return this.mtpassword;
    }

    public void setMtpassword(String mtpassword) {
        this.mtpassword = mtpassword;
    }
}
