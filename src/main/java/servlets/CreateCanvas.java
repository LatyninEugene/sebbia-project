package servlets;

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

public class CreateCanvas extends HttpServlet {
    private String defCanvas;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String name = req.getParameter("name");
        int type = Integer.parseInt(req.getParameter("type"));

        TokenInfo tokenInfo = AuthHelper.verifyToken(token);

        try(Connection con = JDBCUtil.getConnection()){
            defCanvas = getDefCanvas(con,type);
            PreparedStatement ps = con.prepareStatement("INSERT into \"Canvas\"(name,text,id_user) values(?,?,?)");
            ps.setString(1,name);
            ps.setString(2,defCanvas);
            ps.setInt(3, Integer.parseInt(tokenInfo.getUserId()));
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException();
        }
    }

    private String getDefCanvas(Connection con, int type) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * from \"Default_Canvas\" where id=?");
        ps.setInt(1,type);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()){
            System.out.println(resultSet.getInt("id"));
           return resultSet.getString("json");
        }
        return "";
    }
}
