package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public interface IDataUtility {

    public void connectToDataBase();
    
    public boolean executeCommand(String strsql) throws SQLException;
    
    public ResultSet executeQuery(String strsql) throws SQLException;

    public ArrayList<String> getSimpleQueryColumnNames(String strsql);
    
    public boolean disconnectFromDataBase() throws SQLException;

    public boolean isConnected();
    
    public void setResultSet(ResultSet resultSet);

    public ResultSet getResultSet();
    
    public Statement getNewStatement() throws SQLException;

    public Statement getCurrentStatement() throws SQLException;
}