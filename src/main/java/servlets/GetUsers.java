package servlets;

import com.google.gson.Gson;
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
import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;

public class GetUsers extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String json = "{}";
        String send;
        String result;
        Gson g = new Gson();
        int allCount = 0;
        int count = 20;
        int page = 1;
        try {
            count = Integer.parseInt(req.getParameter("count"));
        }catch (Exception e){}
        try {
            page = Integer.parseInt(req.getParameter("page"));
        }catch (Exception e){}

        try (Connection con = JDBCUtil.getConnection()) {
            TokenInfo tokenInfo = AuthHelper.verifyToken(token);
            if (Integer.parseInt(tokenInfo.getUserType())==2) {
                    Map<Integer, String> users = new TreeMap<>();
                    PreparedStatement ps1 = con.prepareStatement("select count(*) FROM \"User\"");
                    ResultSet res = ps1.executeQuery();
                    if(res.next()){
                        allCount = res.getInt(1);
                    }
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM \"User\" LIMIT ? OFFSET ?");
                    ps.setInt(1,count);
                    int offset = count*(page-1);
                    ps.setInt(2,offset);
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        users.put(resultSet.getInt("id"), resultSet.getString("login"));
                    }
                    json = g.toJson(users);
                    result = "SUCCESS";
                } else result = "NOT_ACCESS";
        } catch (Exception e) {
            result = "ERROR";
        }

        send="{\"json\":"+json+",\"count\":\""+allCount+"\",\"result\":\""+result+"\"}";
        resp.getWriter().write(send);
    }
}
