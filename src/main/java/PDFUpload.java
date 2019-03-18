import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;

public class PDFUpload extends HttpServlet {

    File file;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int length = 0;
        try {
            //TODO
            BufferedReader br = req.getReader();
            String json = br.readLine();
            System.out.println(json);
            br.close();
            Canvas can = new Gson().fromJson(json, Canvas.class);
            file  = GeneratePDF.getPDF(can.getName()+".pdf",can);

            ServletOutputStream outStream = resp.getOutputStream();
            ServletContext context  = getServletConfig().getServletContext();
            String mimetype = context.getMimeType(file.getPath());

            // sets response content type
            if (mimetype == null) {
                mimetype = "application/octet-stream";
            }
            resp.setContentType(mimetype);
            resp.setContentLength((int)file.length());
            String fileName = (file.getName());

            // sets HTTP header
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            byte[] byteBuffer = new byte[4096];
            DataInputStream in = new DataInputStream(new FileInputStream(file));

            // reads the file bytes and writes them to the response stream
            while ((in != null) && ((length = in.read(byteBuffer)) != -1))
            {
                outStream.write(byteBuffer,0,length);
            }

            in.close();
            outStream.close();
        } catch (DocumentException | URISyntaxException e) {
            throw new IOException();
        }
    }
}
