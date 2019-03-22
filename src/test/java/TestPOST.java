import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import servlets.PDFUpload;

import java.io.*;

public class TestPOST {

    public static void main(String[] args) {
        Gson gson = new Gson();

        String str = gson.toJson(PDFUpload.getTestCanvas());
        System.out.println(str);


        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8080/upload");

        try {
            StringEntity entity = new StringEntity(str,"utf-8");
//            System.out.println(entity.getContentType().getValue());
//            BufferedInputStream i = new BufferedInputStream(entity.getContent());
//            byte d[] = new byte[1024];
//            i.read(d,0,1024);
//            System.out.write(d,0,1024);
            //entity.setContentType("charset=Windows-1251");
            post.addHeader("charset","Windows-1251");
            post.setEntity(entity);
            System.out.println(post.getEntity().getContentType().getValue());
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json; charset=utf-8");
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
