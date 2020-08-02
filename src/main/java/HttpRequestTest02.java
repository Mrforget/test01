import com.alibaba.fastjson.JSONObject;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;


public class HttpRequestTest02 {
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
            //设置超时的事件
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            //设置通用的请求属性
            connection.setRequestProperty("Authorization", "Basic aXRfbGl5b3V6aGkwMjppdF9saXlvdXpoaTAyX29uZXM=");
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
            connection.setRequestProperty("Authorization", "Basic aXRfbGl5b3V6aGkwMjppdF9saXlvdXpoaTAyX29uZXM=");
            //设置通用的请求属性
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

    /**
     * post请求封装 参数为{"a":1,"b":2,"c":3}
     * @param url
     * @param info
     * @return
     */
    public static JSONObject sendPost(String url,JSONObject info){
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        httppost.setHeader("Content-Type", "application/json");
        httppost.setHeader("Authorization", "Basic aXRfbGl5b3V6aGkwMjppdF9saXlvdXpoaTAyX29uZXM=");
        httppost.setHeader("USERNAME","it_liyouzhi02");
        /**
         *  HttpPost httppost = new HttpPost("要访问的对外接口API");
         *  httppost.setHeader("Authorization", "Basic " + encoding);
         *  httppost.setHeader("USERNAME","此处为misId");
         */
        InputStream inStream = null;
        String result = "";
        try{
            //封装这个请求参数成为一个entity
            StringEntity s = new StringEntity(info.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
            httppost.setEntity(s);

            //发送请求
            HttpResponse httpResponse = client.execute(httppost);

            //获取相应的输入流
            inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder str = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null)
                str.append(line + "\n");
            inStream.close();
            result = str.toString();
            System.out.println(result);
            //判断服务器请求是否成功
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            } else {
                System.out.println("请求服务端失败");
            }
        }catch (Exception e){
            System.out.println("请求异常");
            throw  new RuntimeException(e);
        }finally {
            try {
                if(inStream != null)
                    inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JSONObject.parseObject(result);
    }

    /**
     * post请求封装 参数为?a=1&b=2&c=3
     * @param path 接口地址
     * @param Info 参数
     * @return
     * @throws IOException
     */
    public static JSONObject sendPost2(String path,String info) throws IOException {
        URL url = new URL(path);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置提交类型
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic aXRfbGl5b3V6aGkwMjppdF9saXlvdXpoaTAyX29uZXM=");
        //设置允许写出数据,默认是不允许 false
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true
        //向服务器写出数据的流
        OutputStream os = conn.getOutputStream();
        os.write(info.getBytes());
        os.flush();
        //获取相应的数据
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        String str = br.readLine();
        JSONObject json = JSONObject.parseObject(str);
        System.out.println("相应内容为："+json);
        return json;
    }
}
