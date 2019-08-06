import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataUtility {
    
    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private boolean isConnected = true;
    
    public void connectToDataBase() {
        try {
            String hostName = "ec2-54-163-240-54.compute-1.amazonaws.com";
            String dbName = "d89l9begjikklj";
            String userName = "isscllglmxgeln";
            String passWord = "334f696049572d4bc9c3b6b78c3410301e24dd3b5fd2b96dc15bf4c1c6fed113";
            Class.forName("org.postgresql.Driver");
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
    
    public boolean executeCommand(String strsql) throws SQLException {
        return statement.execute(strsql);
    }
    
    public ResultSet exeQuery(String strsql) throws SQLException {
        resultSet = statement.executeQuery(strsql);
        return resultSet;
    }

    public ArrayList<String> getSimpleQueryColumnNames(String strsql) {
        strsql = strsql.replace("SELECT", "").replace("select", "").replace(" AS ", " as ");
        strsql = strsql.substring(0, strsql.indexOf("from"));
        StringTokenizer stringTokenizer = new StringTokenizer(strsql, ",");
        ArrayList<String> arrayList = new ArrayList<String>(5);
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
    
    public boolean disconnectFromDataBase() throws SQLException {
        connection.close();
        return isConnected = !connection.isClosed();
    }

    public boolean isConnected() {
        return isConnected;
    }
    
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public static void main(String args[]) {
        DataUtility du = new DataUtility();
        du.connectToDataBase();
        String sqlstr;
        sqlstr = "select count(classname) as classnamecount, classname, bangname, howareyou from tbl1 group by howareyou;";
        for(int i=0; i<du.getSimpleQueryColumnNames(sqlstr).size(); i++) {
            System.out.println(du.getSimpleQueryColumnNames(sqlstr).get(i));
        }
        try {
            String sqlstr2;
            sqlstr2 = "select productId, productName, price from products;";
            String sqlstr3;
            sqlstr3 = "update products set price = 1.1*price;";
            System.out.println( du.executeCommand(sqlstr3) );
            du.setResultSet( du.exeQuery(sqlstr2) );
            while(du.resultSet.next()) {
                System.out.println(du.resultSet.getString("productId"));
                System.out.println(du.resultSet.getString("productName"));
                System.out.println(du.resultSet.getString("price"));
            }
            System.out.println(du.disconnectFromDataBase());
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
        System.out.println(du.isConnected());
    }
}