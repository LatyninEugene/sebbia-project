package servlets;

import com.google.gson.Gson;
import controlers.CheckValid;
import controlers.JDBCUtil;

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
    @Deprecated
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if(id == -1){throw new ServletException();}
        getCanvasList(req, resp, id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int id = CheckValid.isValid(login,password);
        if(id == -1){throw new ServletException();}
        getCanvasList(req, resp, id);
    }
}
