
package TXmanage.sync;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServlet;
import java.util.Timer;
import java.util.TimerTask;

@Controller
public class timer extends HttpServlet {

    public timer() {
        this.init();
    }

    private static final Timer timer1 = new Timer();
    private static final Timer timer2 = new Timer();
    private static final Timer timer3 = new Timer();
    private static final Timer timer4 = new Timer();
    private static final Timer timer5 = new Timer();

    public static void tokenTimer() {
        System.out.println("____________start_____________");
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    sync.syncToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 10 * 1000, 100 * 60 * 1000);
    }

    public static void userTimer() {
        System.out.println("____________start_____________");
        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    sync.syncGroup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3 * 60 * 1000, 24 * 3600 * 1000);
    }

    public static void aliasTimer() {
        System.out.println("____________start_____________");
        timer3.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    sync.syncAlias();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5 * 60 * 1000, 6 * 3600 * 1000);
    }

    public static void dakaTimer() {
        System.out.println("____________start_____________");
        timer4.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    sync.syncDaka();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5000, 10 * 60 * 1000);
    }

    public static void deptimer() {
        System.out.println("____________start_____________");
        timer5.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    sync.syncdep();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000 * 3600, 24 * 5 * 3600 * 1000);
    }

    public void init() {

        tokenTimer();
        aliasTimer();
        userTimer();
        dakaTimer();
        deptimer();

    }
}
