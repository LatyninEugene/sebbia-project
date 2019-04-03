package servlets;

import controlers.CheckValid;
import controlers.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CanvasUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String json = req.getParameter("json");
        String name = req.getParameter("name");
        int id_can = Integer.parseInt(req.getParameter("id"));

        int id = CheckValid.isValid(login,password);
        if(id == -1){throw new ServletException();}

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE \"Canvas\" SET name=?,text=? WHERE id=? and id_user=?");
            ps.setString(1,name);
            ps.setString(2,json);
            ps.setInt(3,id_can);
            ps.setInt(4,id);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException();
        }
    }
}
