import java.util.Timer;

public class OnesUtils {
    //param参数、owner拥有者
    public static void alarm(String param,String owner){
        OnesUtils onesUtils = new OnesUtils();
        onesUtils.runTask(param,owner);
    }

    public static void runTask(String param,String owner) {
        Timer timer = new Timer();
        MyTask task = new MyTask();
        task.run(param,owner);
        //一分钟之后进行执行，然后每间隔1分钟进行执行
        timer.scheduleAtFixedRate(task,1*10*1000,1*20*1000);
    }
}
