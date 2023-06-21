package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DbConnection;

public class CrudUtil {
    
    @SuppressWarnings("unchecked")
    public static <T>T execute(String sql, Object ...ar) throws ClassNotFoundException, SQLException{

        PreparedStatement prepareStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < ar.length; i++) {
            prepareStatement.setObject((i+1), ar[i]); 
        }

        if(sql.startsWith("SELECT")){
            return (T)prepareStatement.executeQuery();
            
         
        }
        return (T)(Boolean)(prepareStatement.executeUpdate() > 0);
    }
}
