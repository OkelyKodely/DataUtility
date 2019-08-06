package test;

import data.DataUtility;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestDataUtility {

    public static void main(String args[]) {

        DataUtility du = new DataUtility();
        du.connectToDataBase();

        String sqlstr;
        sqlstr = "select count(classname) as classnamecount, classname, bangname, howareyou from tbl1 group by howareyou;";

        ArrayList<String> arrayList = du.getSimpleQueryColumnNames(sqlstr);
        
        for(int i=0; i<arrayList.size(); i++) {

            System.out.println(arrayList.get(i));
        }

        try {
            
            String sqlstr2;
            sqlstr2 = "select productId, productName, price from products;";

            String sqlstr3;
            sqlstr3 = "update products set price = 1.0*price;";

            System.out.println( du.executeCommand(sqlstr3) );

            du.setResultSet( du.executeQuery(sqlstr2) );

            while(du.getResultSet().next()) {
                
                System.out.println(du.getResultSet().getString("productId"));
                System.out.println(du.getResultSet().getString("productName"));
                System.out.println(du.getResultSet().getString("price"));
            }

            System.out.println(du.disconnectFromDataBase());

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        System.out.println(du.isConnected());
    }
}