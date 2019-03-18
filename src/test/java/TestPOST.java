import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.*;

public class TestPOST {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Map<String, String[]> map = new TreeMap<>();
        map.put("b1",new String[]{"q1","t1"});
        map.put("b2",new String[]{"q2","t2","t2.1"});
        map.put("b3",new String[]{"q3","t3","t3.1"});
        map.put("b4",new String[]{"q4","t4"});
        map.put("b5",new String[]{"q5","t5"});

        Map<String, int[]> mapPos = new TreeMap<>();
        mapPos.put("b1", new int[]{1,1,1,2});
        mapPos.put("b2", new int[]{1,2,1,1});
        mapPos.put("b3", new int[]{2,2,1,1});
        mapPos.put("b4", new int[]{3,1,3,1});
        mapPos.put("b5", new int[]{1,3,1,2});
        int x = 3;
        int y = 3;
        Canvas c = new Canvas("canvas",map,mapPos,x,y);
        String str = gson.toJson(c);
        System.out.println(str);



        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8080/upload");

//        // Create some NameValuePair for HttpPost parameters
//        List<NameValuePair> arguments = new ArrayList<>(3);
//        arguments.add(new BasicNameValuePair("username", "admin"));
//        arguments.add(new BasicNameValuePair("firstName", "System"));
//        arguments.add(new BasicNameValuePair("lastName", "Administrator"));

        try {
            StringEntity entity = new StringEntity(str);
            post.setEntity(entity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(post);
                BufferedInputStream in = new BufferedInputStream(response.getEntity().getContent());
                byte data[] = new byte[1024];
                int count;
                FileOutputStream out = new FileOutputStream("testFile.pdf");
                while((count = in.read(data,0,1024)) != -1)
                {
                    out.write(data, 0, count);
                }
            client.close();
            // Print out the response message
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
