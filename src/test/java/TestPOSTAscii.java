import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import servlets.PDFUpload;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestPOSTAscii {

    public static void main(String[] args) {
        Gson gson = new Gson();

        String str = gson.toJson(PDFUpload.getTestCanvas());
        System.out.println("TEST:"+str);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8080/uploadAscii");

        try {
            List<NameValuePair> arguments = new ArrayList<>(1);
            arguments.add(new BasicNameValuePair("json", str));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(arguments,"utf-8");
            post.setEntity(entity);
            CloseableHttpResponse response = client.execute(post);
                BufferedInputStream in = new BufferedInputStream(response.getEntity().getContent());
                byte data[] = new byte[1024];
                int count;
                FileOutputStream out = new FileOutputStream("asciiCanvas.adoc");
                while((count = in.read(data,0,1024)) != -1)
                {
                    out.write(data, 0, count);
                }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
