package servlets;

import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import controlers.GenerateAsciiDoc;
import controlers.GeneratePDF;
import model.Canvas;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class AsciiDocUpload extends HttpServlet {

    File file;
    int length = 0;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getParameter("json");
        System.out.println("Main:" + json);
        Canvas can = new Gson().fromJson(json, Canvas.class);
        file = GenerateAsciiDoc.getAsciiDoc(can);

        ServletOutputStream outStream = resp.getOutputStream();
        ServletContext context = getServletConfig().getServletContext();

        resp.setContentLength((int) file.length());
        String fileName = (file.getName());

        // sets HTTP header
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        byte[] byteBuffer = new byte[4096];
        DataInputStream in = new DataInputStream(new FileInputStream(file));

        // reads the file bytes and writes them to the response stream
        while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
            outStream.write(byteBuffer, 0, length);
        }

        in.close();
        outStream.close();
    }
}

