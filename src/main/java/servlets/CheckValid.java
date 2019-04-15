package servlets;

import com.google.gson.Gson;
import controlers.AuthHelper;
import controlers.JDBCUtil;
import model.AccessObject;

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
        String token = "";
        String role = "";
        String result = "ERROR";
        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("select * from \"User\" where login=? and password=?");
            ps.setString(1,login);
            ps.setString(2,password);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                id = resultSet.getInt("id");
                type = resultSet.getInt("type");
                token  = AuthHelper.createJsonWebToken(""+id,""+type,DAYS);
                if(type==1)role="USER";
                else if(type==2)role="ADMIN";
                result = "SUCCESS";
            }else {
                result = "USER_NOT_FOUND";
            }
        } catch (SQLException | ClassNotFoundException e) {
        }
        AccessObject ao = new AccessObject(token,role,result);
        Gson g  = new Gson();
        resp.getWriter().write(g.toJson(ao,AccessObject.class));
    }
}
