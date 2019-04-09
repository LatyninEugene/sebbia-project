package servlets;

import controlers.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCanvas extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM \"Canvas\" WHERE id=?");
            ps.setInt(1,id);
            String json = "{}";
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                json = resultSet.getString("text");
            }
            resp.getWriter().print(json);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
