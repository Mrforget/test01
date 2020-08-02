import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.net.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

public class MyTaskTest01 extends TimerTask {

    public void run() {
        int i = 0;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("当前时间："+sdf.format(c.getTime()));
        //控制任务发送的时间，AM_PM确定上午还是下午
        if(cal.get(Calendar.HOUR) == 0 && cal.get(Calendar.AM_PM)==1){
            i++;
            System.out.println("当前时间："+sdf.format(cal.getTime()));
            System.out.println("第"+i+"次执行任务开始");
            //读取报警信息
            //判断是否重复（本地放置一个文件，文件存储任务名称，如果重复的话不插入并且跳过，如果不重复就插入，然后生成一个ones）
            //生成param以及url
            //执行创建
           // String s = HttpRequestTest01.sendGet("http://ones.vip.sankuai.com/api/1.0/ones/project/20675/modules","");   //在这里进行处理，读取相应的报警信息
            String paraTest = "{\"cc\":[\"liyouzhi02\"]}";
            JSONObject paraTestObject = JSONObject.parseObject(paraTest);
            //通过desc来生成表格并且拼接表格的内容
            String desc = "<html>\n" +
                    "<body>\n" +
                    "\n" +
                    "<table border=\"1\">\n" +
                    "  <tr>\n" +
                    "    <th>Month</th>\n" +
                    "    <th>Savings</th>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>January</td>\n" +
                    "    <td>$100</td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n";
            paraTestObject.put("name","task05");
            paraTestObject.put("desc",desc);
            paraTestObject.put("type","DEVTASK");
            paraTestObject.put("projectId",20675);
            paraTestObject.put("assigned","it_liyouzhi02");
            paraTestObject.put("priority",1);

            JSONObject json2 = HttpRequestTest02.sendPost("http://ones.vip.sankuai.com/api/1.0/ones/projects/20675/issue", paraTestObject);
            //System.out.println("返回结果为："+s);
            String s2 = JSONObject.toJSONString(json2);
            System.out.println("返回结果为："+s2);
            System.out.println("第"+i+"次执行任务结束");
        }
    }
}
