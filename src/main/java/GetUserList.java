import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class GetUserList extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Map<Integer,String> canName = new TreeMap<>();
        int id = CheckValid.isValid(login,password);
        if(id == -1){throw new ServletException();}
        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM \"Canvas\" WHERE id_user=?");
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                canName.put(resultSet.getInt("id"),resultSet.getString("name"));
            }
            System.out.println(new Gson().toJson(canName));
            System.out.println(req.getCharacterEncoding());
            resp.setCharacterEncoding(req.getCharacterEncoding());
            resp.getWriter().write(new Gson().toJson(canName));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
