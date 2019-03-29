import controlers.JDBCUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJDBC {

    @Test
    public void testConnection(){
        try(Connection con = JDBCUtil.getConnection()) {
            if(con == null){
                Assert.fail("Connection = null");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail("Connection error");
        }
    }

    @Test
    public void checkValid(){
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from \"User\" where login=? and password=?");
            ps.setString(1,"admin");
            ps.setString(2,"admin");
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                Assert.assertEquals("Connection OK",id,1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail("Connection error");
        }
    }
}
