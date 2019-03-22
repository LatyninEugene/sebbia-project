import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateCanvas extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        String defCanvas = "{\"name\":\"canvas\"," +
                "\"blocks\":{" +
                "\"b0\":[\"ноюямше хдех\",\"\"],\"b1\":[\"онвелс? / гювел?\",\"\"],\"b2\":[\"йнмрейярмше яхрсюжхх\",\"\"]," +
                "\"b3\":[\"опнакелш\",\"\"],\"b4\":[\"жеммнярэ\",\"\"],\"b5\":[\"бнглнфмнярх\",\"\"]," +
                "\"b6\":[\"нцпюмхвемхъ\",\"\"]}," +
                "\"bloksPos\":{\"b0\":[1,1,1,1],\"b1\":[2,1,2,1],\"b2\":[3,1,1,1]," +
                "\"b3\":[1,2,2,1],\"b4\":[3,2,1,1],\"b5\":[2,3,1,1],\"b6\":[3,3,1,1]},\"x\":3,\"y\":3}";

        int id = CheckValid.isValid(login,password);
        if(id == -1){throw  new ServletException();}

        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT into \"Canvas\"(name,text,id_user) values(?,?,?)");
            ps.setString(1,name);
            ps.setString(2,defCanvas);
            ps.setInt(3,id);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException();
        }
    }
}
