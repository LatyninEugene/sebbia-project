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
        Gson gson = new Gson();
        Canvas c = gson.fromJson("{\"name\":\"Business canvas\"," +
                "\"blocks\":{\"b0\":[\"КЛЮЧЕВЫЕ ПАРТНЕРЫ\"," +
                "\"Кто ваши ключевые партнеры/поставщики? Что мотивирует их сотрудничать с вами? Какие ключевые мероприятия выполняют ваши партнеры?\"," +
                "\"Ответ 1 \nОтвет 2\"]," +
                "\"b1\":[\"КЛЮЧЕВЫЕ МЕРОПРИЯТИЯ\"," +
                "\"Какие виды мероприятий вы выполняете каждый день чтобы создавать и доносить ценностное предложение?\"," +
                "\"\"]," +
                "\"b2\":[\"ЦЕННОСТНОЕ ПРЕДЛОЖЕНИЕ\"," +
                "\"Какую ценность вы создаете для клиентов? Какие проблемы клиентов вы помогаете решить? Почему вы считаете что данная ценность нужна вашим клиентам? Что вы обещаете клиентам? Какой продукт и/или сервис вы создаете для клиентов?\"," +
                "\"Ответ 1\"]," +
                "\"b3\":[\"ОТНОШЕНИЯ С КЛИЕНТАМИ\"," +
                "\"Какие отношения с клиентами вы ожидаете иметь? Опишите по каждому сегменту\"," +
                "\"Хреновое\"]," +
                "\"b4\":[\"ПОЛЬЗОВАТЕЛЬСКИЕ СЕГМЕНТЫ\"," +
                "\"Для кого вы создаете ценность? Какие пользовательские сегменты будут платить или давать вам что-то полезное?\"," +
                "\"\"]," +
                "\"b5\":[\"КЛЮЧЕВЫЕ РЕСУРСЫ\"," +
                "\"Какие ресурсы вам необходимы для создания ценности для клиентов?\"," +
                "\"\"]," +
                "\"b6\":[\"КАНАЛЫ\"," +
                "\"Как ваши клиенты узнают о ценности которую вы создаете? Где ваши клиенты могут использовать ваш продукт и/или сервис?\"," +
                "\"\"]," +
                "\"b7\":[\"СТРУКТУРА РАСХОДОВ\"," +
                "\"Какие важные расходы вы понесете при создании ценности для клиентов?\"," +
                "\"\"]," +
                "\"b8\":[\"ДОХОД\"," +
                "\"Как пользователи платят вам? Какие у вас есть способы монетизации?\"" +
                ",\"\"]}," +
                "\"bloksPos\":{\"b0\":[1,1,2,1],\"b1\":[2,1,1,1],\"b2\":[3,1,2,1],\"b3\":[4,1,1,1],\"b4\":[5,1,2,1]," +
                "\"b5\":[2,2,1,1],\"b6\":[4,2,1,1],\"b7\":[1,3,1,3],\"b8\":[4,3,1,2]},\"x\":5,\"y\":3}",
                Canvas.class);
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
