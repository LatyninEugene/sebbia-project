import controlers.AuthHelper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestServlets {

    static int count = 0;
    private static String TOKEN;

    private static BufferedInputStream sendPost(List<NameValuePair> arg, String url){
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(arg,"windows-1251");
            post.setEntity(entity);
            CloseableHttpResponse response = client.execute(post);

            HttpEntity httpEntity = response.getEntity();
            BufferedInputStream i = new BufferedInputStream(httpEntity.getContent());
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void print(BufferedInputStream i){
        byte[] b = new byte[1024];
        try {
            i.read(b);
            String str = new String(b);
            str = str.trim();
            FileWriter fw = new FileWriter(new File("log"),true);
            fw.write(Thread.currentThread().getName()+" "+str+"\n");
            fw.close();
            count++;
            System.out.println(count+" - "+Thread.currentThread().getName()+"-"+str);
        } catch (IOException e) {
            Assert.fail("Validation error");
        }
    }
    @Test
    @Ignore
    public void checkValid(){
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/checkValid");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        byte[] b = new byte[1024];
        try {
            i.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TOKEN = new String(b);
        System.out.println(TOKEN);
    }
    @Test
    @Ignore
    public void getUsersList(){
        List<NameValuePair> arguments = new ArrayList<>(1);
        arguments.add(new BasicNameValuePair("token", TOKEN));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getUsersList");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
    @Test
    @Ignore
    public void getUsersListForAdmin(){
        List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("token", TOKEN));
        arguments.add(new BasicNameValuePair("id", "5"));
        arguments.add(new BasicNameValuePair("myList", "False"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getUsersList");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
    @Test
    @Ignore
    public void getUsersForAdmin(){
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("token", TOKEN));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getUsers");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
    @Test
    @Ignore
    public void getCanvas(){
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("token", TOKEN));
        arguments.add(new BasicNameValuePair("id", "1"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getCanvas");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
}
