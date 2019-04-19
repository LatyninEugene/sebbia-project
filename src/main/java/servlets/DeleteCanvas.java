package servlets;

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
import java.sql.SQLException;

public class DeleteCanvas extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String token = req.getParameter("token");

        TokenInfo tokenInfo = AuthHelper.verifyToken(token);

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE from \"Canvas\" where id=? and id_user=?");
            ps.setInt(1,id);
            ps.setInt(2, Integer.parseInt(tokenInfo.getUserId()));
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException();
        }
    }
}
