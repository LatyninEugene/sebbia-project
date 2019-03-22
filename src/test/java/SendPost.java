import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendPost {
    public static void main(String[] args) {
        System.out.println(getUsersList());
        System.out.println(getCanvas());
        //System.out.println(createCanvas());
    }
    private static boolean sendPost(List<NameValuePair> arg, String url){
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(arg,"windows-1251");
            post.setEntity(entity);
            System.out.println(EntityUtils.toString(entity));
            System.out.println(post.getEntity().getContentType().getValue());
            CloseableHttpResponse response = client.execute(post);

            HttpEntity httpEntity = response.getEntity();
            BufferedInputStream i = new BufferedInputStream(httpEntity.getContent());
            byte d[] = new byte[1024];
            i.read(d,0,1024);
            System.out.write(d,0,1024);
            client.close();
            System.out.println();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean getUsersList(){
        System.out.println("\nList:");
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        return sendPost(arguments, "http://localhost:8080/getUsersList");
    }
    private static boolean getCanvas(){
        System.out.println("\nCanvas:");
        List<NameValuePair> arguments = new ArrayList<>(1);
        arguments.add(new BasicNameValuePair("id", "1"));
        return sendPost(arguments, "http://localhost:8080/getCanvas");
    }
    private static boolean createCanvas(){
        System.out.println("\ncreateCanvas:");
        List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("login", "admin"));
        arguments.add(new BasicNameValuePair("password", "admin"));
        arguments.add(new BasicNameValuePair("name", "newCanvas"));
        return sendPost(arguments, "http://localhost:8080/createCanvas");
    }
}
