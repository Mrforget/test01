import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Run {
    public static void main(String[] args) {
        //发送get请求
        //String sget = HttpRequestTest01.sendGet("","");
        //System.out.println(sget);
        //发送post请求
        //String spost = HttpRequestTest01.sendPost("","");
        //System.out.println(spost);

        Run r = new Run();
        //r.runtask();
        r.runtaskTest();
    }

    private void runtaskTest() {
        Timer timer = new Timer();

        MyTaskTest01 task = new MyTaskTest01();
        //一分钟之后进行执行，然后每间隔1分钟进行执行
        timer.scheduleAtFixedRate(task,1*10*1000,1*20*1000);
    }

    public void runtask() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        Timer timer = new Timer();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.DAY_OF_WEEK,Calendar.DAY_OF_WEEK+1);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MINUTE, 0);
        //第二天凌晨0点开始执行
        long diffs = cal2.getTimeInMillis() - cal1.getTimeInMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MyTask task = new MyTask();
        //每间隔24小时进行执行
        timer.scheduleAtFixedRate(task,diffs,24*60*60*1000);
    }
}
