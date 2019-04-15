package servlets;

import controlers.AuthHelper;
import controlers.JDBCUtil;
import model.TokenInfo;

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
        String token = req.getParameter("token");

        String result = "ERROR";
        String json = "{}";
        String send;

        TokenInfo tokenInfo = AuthHelper.verifyToken(token);

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM \"Canvas\" WHERE id=?");
            ps.setInt(1,id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){

                int id_user = resultSet.getInt("id_user");
                if(Integer.parseInt(tokenInfo.getUserId())==id_user){
                    result = "SUCCESS";
                    json = resultSet.getString("text");
                }else if(Integer.parseInt(tokenInfo.getUserType())==2){
                    result = "SUCCESS_FOR_ADMIN";
                    json = resultSet.getString("text");
                }else {
                    json="{}";
                    result = "NOT_ACCESS";
                }
            }else {
                result = "CANVAS_NOT_FOUND";
            }

        } catch (SQLException | ClassNotFoundException e) {
            result = "ERROR";
        }
        send="{\"json\":"+json+",\"result\":\""+result+"\"}";
        resp.getWriter().print(send);
    }
}
