import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestServlets {

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
            System.out.println(str);
        } catch (IOException e) {
            Assert.fail("Validation error");
        }
    }
    @Test
    public void checkValid(){
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/checkValid");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
    @Test
    public void getUsersList(){
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("login", "4"));
        arguments.add(new BasicNameValuePair("password", "4"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getUsersList");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
    @Test
    public void getUsersListForAdminMyList(){
        List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getUsersList");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
    @Test
    public void getUsersListForAdmin(){
        List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        arguments.add(new BasicNameValuePair("id", "5"));
        arguments.add(new BasicNameValuePair("myList", "False"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getUsersList");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
    @Test
    public void getUsersForAdmin(){
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getUsers");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
    @Test
    public void getCanvas(){
        List<NameValuePair> arguments = new ArrayList<>(1);
        arguments.add(new BasicNameValuePair("id", "6"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getCanvas");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        print(i);
    }
}
