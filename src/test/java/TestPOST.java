import com.google.gson.Gson;
import controlers.JDBCUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import servlets.CreateCanvas;
import servlets.PDFUpload;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestPOST {

    public static void main(String[] args) {
        String str = "";
        try(Connection con = JDBCUtil.getConnection()) {
            str = CreateCanvas.getDefCanvas(con,1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("TEST:"+str);

        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost post = new HttpPost("https://sebbia-project.herokuapp.com/upload");
        HttpPost post = new HttpPost("http://localhost:8090/uploadPDF");

        try {
            List<NameValuePair> arguments = new ArrayList<>(2);
            arguments.add(new BasicNameValuePair("json", str));
            arguments.add(new BasicNameValuePair("name", "My Lean Canvas"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(arguments,"utf-8");
            post.setEntity(entity);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
