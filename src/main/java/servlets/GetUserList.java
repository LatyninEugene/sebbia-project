package servlets;

import com.google.gson.Gson;
import controlers.AuthHelper;
import controlers.JDBCUtil;
import model.TokenInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class GetUserList extends HttpServlet {

    private Map<Integer,String[]> getCanvasList(HttpServletRequest req, HttpServletResponse resp, int id) throws IOException {

        try (Connection con = JDBCUtil.getConnection()) {
            Map<Integer,String[]> canList = new TreeMap<>();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM \"Canvas\" WHERE id_user=?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            ResultSet resultSetForDef;
            while (resultSet.next()) {
                String[] arr = new String[2];
                int id_can = resultSet.getInt("id");
                arr[0] = resultSet.getString("name");
                int id_def = resultSet.getInt("type");
                ps = con.prepareStatement("SELECT * FROM \"Default_Canvas\" WHERE id=?");
                ps.setInt(1,id_def);
                resultSetForDef = ps.executeQuery();
                if(resultSetForDef.next()){
                    arr[1] = resultSetForDef.getString("name");
                }else arr[1] = "Not determined";
                canList.put(id_can, arr);
            }
            return canList;
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        Map<Integer,String[]> canList;
        String myList = "";
        String json = "{}";
        String result;
        String send;
        Gson g = new Gson();

        try { myList = req.getParameter("myList").toUpperCase();
        }catch (Exception e){}

        try {
            TokenInfo tokenInfo = AuthHelper.verifyToken(token);

            if (Integer.parseInt(tokenInfo.getUserType()) == 2 && myList.equals("FALSE")) {
                int id_user = Integer.parseInt(req.getParameter("id"));
                canList = getCanvasList(req, resp, id_user);
                result = "SUCCESS_FOR_ADMIN";
            }else {
                canList = getCanvasList(req, resp, Integer.parseInt(tokenInfo.getUserId()));
                result = "SUCCESS";
            }
            if(canList == null)result = "LIST_NOT_FOUND";
            else {json = g.toJson(canList);}
        }catch (Exception e){
            result = "ERROR";
        }

        send="{\"json\":"+json+",\"result\":\""+result+"\"}";
        resp.getWriter().print(send);
    }
}
