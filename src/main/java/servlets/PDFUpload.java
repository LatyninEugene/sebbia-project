package servlets;

import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import controlers.GeneratePDF;
import model.Canvas;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

public class PDFUpload extends HttpServlet {

    File file;
    int length = 0;

    public static Canvas getTestCanvas(){
        Map<String, String[]> map = new TreeMap<>();
        map.put("b0",new String[]{"q1","t1"});
        map.put("b2",new String[]{"q2","t2"});
        map.put("b3",new String[]{"q3","t3","таск 3.1"});
        map.put("b4",new String[]{"q4","t4","все ок"});
        map.put("b5",new String[]{"q5","t5"});
        map.put("b6",new String[]{"q5","t5"});
        map.put("b7",new String[]{"q5","t5"});

        Map<String, int[]> mapPos = new TreeMap<>();
        mapPos.put("b0", new int[]{1,1,1,1});
        mapPos.put("b2", new int[]{2,1,2,1});
        mapPos.put("b3", new int[]{3,1,1,1});
        mapPos.put("b4", new int[]{1,2,2,1});
        mapPos.put("b5", new int[]{3,2,1,1});
        mapPos.put("b6", new int[]{2,3,1,1});
        mapPos.put("b7", new int[]{3,3,1,1});
        int x = 3;
        int y = 3;
        Canvas c = new Canvas("canvas",map,mapPos,x,y);
        return c;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            file = GeneratePDF.getPDF(getTestCanvas().getName() + ".pdf", getTestCanvas());

            ServletOutputStream outStream = resp.getOutputStream();
            ServletContext context = getServletConfig().getServletContext();
            String mimetype = context.getMimeType(file.getPath());

            // sets response content type
            if (mimetype == null) {
                mimetype = "application/pdf";
            }
            resp.setContentType(mimetype);
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
        }catch (Exception e){
            System.out.println("erroro");

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String json = req.getParameter("json");
            System.out.println("Main:"+json);
            Canvas can = new Gson().fromJson(json, Canvas.class);
            file  = GeneratePDF.getPDF(can.getName()+".pdf",can);

            ServletOutputStream outStream = resp.getOutputStream();
            ServletContext context  = getServletConfig().getServletContext();
            String mimetype = context.getMimeType(file.getPath());

            // sets response content type
            if (mimetype == null) {
                mimetype = "application/pdf";
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
