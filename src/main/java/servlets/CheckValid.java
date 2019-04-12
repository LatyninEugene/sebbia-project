package servlets;

import controlers.AuthHelper;
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

    private int id;
    private int type;
    private final long DAYS = 2;

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
                id = resultSet.getInt("id");
                type = resultSet.getInt("type");
                resp.getWriter().write(AuthHelper.createJsonWebToken(""+id,""+type,DAYS));
            }else {
                throw new ServletException();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException();
        }
    }
}
