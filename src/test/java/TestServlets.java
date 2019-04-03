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

    @Test
    public void checkValid(){
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/checkValid");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        byte[] b = new byte[12];
        try {
            i.read(b,0,12);
            String str = new String(b);
            str = str.trim();
            System.out.println(str);
            Assert.assertEquals(str,"1 2");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Validation error");
        }
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
        byte[] b = new byte[1024];
        try {
            i.read(b);
            String str = new String(b);
            str = str.trim();
            Assert.assertEquals(str,"{\"1\":\"newCanvas\",\"2\":\"ÕÓ‚˚È  ‡Ì‚‡Ò\"}");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Validation error");
        }
    }
    @Test
    public void getUsersListForAdmin(){
        List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        arguments.add(new BasicNameValuePair("id", "1"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getUsersList");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        byte[] b = new byte[1024];
        try {
            i.read(b);
            String str = new String(b);
            str = str.trim();
            Assert.assertEquals(str,"");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Validation error");
        }
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
        byte[] b = new byte[1024];
        try {
            i.read(b);
            String str = new String(b);
            str = str.trim();
            Assert.assertEquals(str,"");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Validation error");
        }
    }
    @Test
    public void getCanvas(){
        List<NameValuePair> arguments = new ArrayList<>(1);
        arguments.add(new BasicNameValuePair("id", "6"));
        BufferedInputStream i = sendPost(arguments, "http://localhost:8080/getCanvas");
        if(i == null){
            Assert.fail("NullPointerException");
        }
        byte[] b = new byte[1024];
        try {
            i.read(b);
            String str = new String(b);
            str = str.trim();
            String str1 = "{\"name\":\"canvas\"," +
                    "\"blocks\":{\"b0\":[\"Œœ¿—Õ€≈ »ƒ≈»\",\"\"]," +
                        "\"b1\":[\"œŒ◊≈Ã”? / «¿◊≈Ã?\",\"\"]," +
                        "\"b2\":[\" ŒÕ“≈ —“Õ€≈ —»“”¿÷»»\",\"\"]," +
                        "\"b3\":[\"œ–Œ¡À≈Ã€\",\"\"],\"b4\":[\"÷≈ÕÕŒ—“‹\",\"\"]," +
                        "\"b5\":[\"¬Œ«ÃŒ∆ÕŒ—“»\",\"\"],\"b6\":[\"Œ√–¿Õ»◊≈Õ»ﬂ\",\"\"]}," +
                    "\"bloksPos\":{\"b0\":[1,1,1,1],\"b1\":[2,1,2,1],\"b2\":[3,1,1,1]," +
                        "\"b3\":[1,2,2,1],\"b4\":[3,2,1,1],\"b5\":[2,3,1,1]," +
                        "\"b6\":[3,3,1,1]},\"x\":3,\"y\":3}";
            Assert.assertEquals(str,str1);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Validation error");
        }
    }
}
