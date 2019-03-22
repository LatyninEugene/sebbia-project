package controlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckValid {
    public static int isValid(String login, String pass){
        int id;
        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM \"User\" WHERE login=? and password=?");
            ps.setString(1,login);
            ps.setString(2, pass);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                id = resultSet.getInt("id");
                System.out.println(resultSet.getString("login"));
                return id;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
