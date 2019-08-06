package data;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataUtility implements IDataUtility {
    
    private Connection connection = null;

    private Statement statement = null;

    private ResultSet resultSet = null;

    private boolean isConnected = true;

    @Override
    public ArrayList<String> getSimpleQueryColumnNames(String strsql) {

        strsql = strsql.replace("SELECT", "").replace("select", "").replace(" AS ", " as ");
        strsql = strsql.substring(0, strsql.indexOf("from"));

        StringTokenizer stringTokenizer = new StringTokenizer(strsql, ",");
        
        ArrayList<String> arrayList = new ArrayList<>(5);

        while(stringTokenizer.hasMoreTokens()) {

            String st;
            st = stringTokenizer.nextToken();

            if(st.contains(" as ")) {
                st = st.substring(st.indexOf(" as ") + 4, st.length());
            }

            st = st.trim();
            st = st.toLowerCase();

            arrayList.add( st );
        }

        return arrayList;
    }

    @Override
    public void connectToDataBase() {

        try {
            
            Class.forName("org.postgresql.Driver");

            String hostName = "ec2-54-163-240-54.compute-1.amazonaws.com";
            
            String dbName = "d89l9begjikklj";
            
            String userName = "isscllglmxgeln";
            String passWord = "334f696049572d4bc9c3b6b78c3410301e24dd3b5fd2b96dc15bf4c1c6fed113";
            
            String url = "jdbc:postgresql://" + hostName + "/" + dbName + "?user=" + userName + "&password=" + passWord + "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

            connection = DriverManager.getConnection(url);

            statement = connection.createStatement();

        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        isConnected = true;
    }

    @Override
    public Statement getNewStatement() throws SQLException {

        return (statement = connection.createStatement());
    }
    
    @Override
    public Statement getCurrentStatement() throws SQLException {

        return statement;
    }

    @Override
    public boolean executeCommand(String strsql) throws SQLException {

        return statement.execute(strsql);
    }
    
    @Override
    public ResultSet executeQuery(String strsql) throws SQLException {

        resultSet = statement.executeQuery(strsql);

        return resultSet;
    }
    
    @Override
    public boolean disconnectFromDataBase() throws SQLException {

        connection.close();
        return (isConnected = !connection.isClosed());
    }

    @Override
    public boolean isConnected() {

        return isConnected;
    }
    
    @Override
    public void setResultSet(ResultSet resultSet) {

        this.resultSet = resultSet;
    }

    @Override
    public ResultSet getResultSet() {

        return resultSet;
    }
}