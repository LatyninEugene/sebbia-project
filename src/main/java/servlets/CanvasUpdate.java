package servlets;

import controlers.AuthHelper;
import controlers.CheckValid;
import controlers.JDBCUtil;
import model.TokenInfo;
import model.User;

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
        String token = req.getParameter("token");
        String json = req.getParameter("json");
        String name = req.getParameter("name");
        int id_can = Integer.parseInt(req.getParameter("id"));

        TokenInfo tokenInfo = AuthHelper.verifyToken(token);

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE \"Canvas\" SET name=?,text=? WHERE id=? and id_user=?");
            ps.setString(1,name);
            ps.setString(2,json);
            ps.setInt(3,id_can);
            ps.setInt(4, Integer.parseInt(tokenInfo.getUserId()));
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException();
        }
    }
}
