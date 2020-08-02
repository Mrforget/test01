import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequestTest01 {
    /**
     * 向指定的url发送GET方法的请求
     * url 发送请求的url
     * param  请求参数，请求参数应该是name1=value1&name2=value2的形式
     * URL  所代表远程资源的响应结果
     */
    public static String sendGet(String url,String param){
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" +param;
            URL realUrl = new URL(urlNameString);
            //打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("Authorization", "Basic aXRfbGl5b3V6aGkwMjppdF9saXlvdXpoaTAyX29uZXM=");
            //设置超时的事件
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            //设置通用的请求属性

            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立连接
            connection.connect();
            //获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            //遍历所有的响应头字段
            for(String key:map.keySet()){
                System.out.println(key+"-->"+map.get(key));
            }
            //定义BufferedReader输入流来读取URL响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                result += line;
            }
        }catch (Exception e){
            System.out.println("发送get请求出现异常"+e);
            e.printStackTrace();
        }finally {
            //finally来关闭输入流
            try{
                if(in!=null){
                    in.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 向指定url发送POST方法的请求
     * url 发送请求的url
     * param  请求参数，请求参数应该是name1=value1&name2=value2的形式
     * URL  所代表远程资源的响应结果
     */
    public static String sendPost(String url,String param){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try{
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            //设置通用的请求属性
            connection.setRequestProperty("Authorization", "Basic aXRfbGl5b3V6aGkwMjppdF9saXlvdXpoaTAyX29uZXM=\n");
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //发送post请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);

            //建立连接     这一步不知道是不是必须的
            connection.connect();

            //获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            //发送请求参数
            out.print(param);
            //flush输出流的缓冲
            out.flush();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                result += line;
            }
        }catch (Exception e){
            System.out.println("发送post请求出现异常"+e);
            e.printStackTrace();
        }finally {
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null) {
                    in.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return result;
    }
}
