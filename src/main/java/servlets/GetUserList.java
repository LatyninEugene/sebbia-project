package servlets;

import com.google.gson.Gson;
import controlers.AuthHelper;
import controlers.CheckValid;
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

    private void getCanvasList(HttpServletRequest req, HttpServletResponse resp, int id) throws IOException {
        try (Connection con = JDBCUtil.getConnection()) {
            Map<Integer,String> canName = new TreeMap<>();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM \"Canvas\" WHERE id_user=?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                canName.put(resultSet.getInt("id"), resultSet.getString("name"));
            }
            resp.getWriter().write(new Gson().toJson(canName));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String myList = "";

        try { myList = req.getParameter("myList").toUpperCase();
        }catch (Exception e){}

        TokenInfo tokenInfo = AuthHelper.verifyToken(token);

        if (Integer.parseInt(tokenInfo.getUserType()) == 2 && myList.equals("FALSE")) {
            int id_user = Integer.parseInt(req.getParameter("id"));
            getCanvasList(req, resp, id_user);
            return;
        }else getCanvasList(req, resp, Integer.parseInt(tokenInfo.getUserId()));
    }
}
