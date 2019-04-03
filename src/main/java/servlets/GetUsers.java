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

public class GetUsers extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int id = CheckValid.isValid(login,password);
        if(id == -1){throw new ServletException();}
        if (CheckValid.getType(id)==2) {
            try (Connection con = JDBCUtil.getConnection()) {
                Map<Integer, String> users = new TreeMap<>();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM \"User\" WHERE type=?");
                ps.setInt(1, 1);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    users.put(resultSet.getInt("id"), resultSet.getString("login"));
                }
                resp.getWriter().write(new Gson().toJson(users));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else resp.getWriter().write("�� �� �����");
    }
}