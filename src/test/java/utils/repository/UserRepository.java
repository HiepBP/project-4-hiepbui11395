package utils.repository;

import cs601.project4.jdbc.ConnectionUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private static UserRepository instance;

    private UserRepository() { }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private final String SQL_INSERT = "insert into `user` (`username`)" +
            "values (?)";
    private final String SQL_UPDATE = "update `user` set `username`=? " +
            "where `id`=?";
    private final String SQL_DELETE = "delete from `user`";
    private final String SQL_COUNT = "select count(*) from `user`";

    public void deleteAll(){
        BasicDataSource dataSource = ConnectionUtil.getMyConnection();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE))
        {
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int count(){
        BasicDataSource dataSource = ConnectionUtil.getMyConnection();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_COUNT);
            ResultSet rs = statement.executeQuery())
        {
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }


}
