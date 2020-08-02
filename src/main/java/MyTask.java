import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

public class MyTask extends TimerTask {
    public void run() {
    }
    public static void run(String param,String owner){
        int i = 0;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("当前时间："+sdf.format(c.getTime()));
        //当时间是每天的6点时，发送post请求
        if(cal.get(Calendar.HOUR) == 0 && cal.get(Calendar.AM_PM)==1){
            i++;
            System.out.println("当前时间："+sdf.format(cal.getTime()));
            System.out.println("第"+i+"次执行任务开始");
            //这里进行param和owner的解析，然后进行拼接封装
            String para1 = "{\"cc\":[\"liyouzhi02\"]}";
            JSONObject paraObject = JSONObject.parseObject(para1);
            paraObject.put("","");//多个进行
            String s = HttpRequestTest02.sendPost("","");   //在这里进行处理，读取相应的报警信息

            System.out.println("返回结果为："+s);
            System.out.println("第"+i+"次执行任务结束");
        }
    }
}
