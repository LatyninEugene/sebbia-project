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

public class DeleteCanvas extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        int user_id = CheckValid.isValid(login,password);
        if(user_id == -1)throw new ServletException();

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE from \"Canvas\" where id=? and id_user=?");
            ps.setInt(1,id);
            ps.setInt(2,user_id);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException();
        }
    }
}
