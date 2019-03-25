package servlets;

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

public class CheckValid extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("select * from \"User\" where login=? and password=?");
            ps.setString(1,login);
            ps.setString(2,password);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                resp.getWriter().write(String.valueOf(resultSet.getInt("id")));
            }else {
                resp.getWriter().write(String.valueOf(-1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException();
        }
    }
}
