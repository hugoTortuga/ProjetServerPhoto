package API;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;


public class QueryManager {
    private String URL;
    private String user;
    private String password;
    Connection connection = null;
    Statement st = null;

    public QueryManager () {

        this.URL = "jdbc:mysql://localhost/projet";
        this.user = "root";
        this.password = "Jklm4826oo!AZ";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(URL, user, password);
            st = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection() {
        try {
            assert connection != null;
            assert st != null;

            connection.close();
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
	public Hashtable<String, String> getQuery(String table, ArrayList<String> conditions, String... fields) throws SQLException {

        Hashtable<String, String> returnArray = new Hashtable<String, String>();

        String query = "SELECT ";
        boolean isFirstField = true;
        boolean isNoFieldSpecified = false;
        int nbFields = 0;

        for (String field : fields) {
            nbFields++;
            if(field.equals("*")) isNoFieldSpecified = true;
            if(isFirstField) {
                query += field;
                isFirstField = false;
            } else query += ", " + field;
        } if (nbFields != 1 && isNoFieldSpecified) {
            System.out.println("ERREUR: ELEMENT * AVEC UN AUTRE CHAMP");
            returnArray.put("ERREUR", "ELEMENT * AVEC UN AUTRE CHAMP");
            return returnArray;
        }
        query += " FROM " + table + " ";
        if(conditions.size() != 0){
            query += "WHERE ";
            boolean isFirstCondition = true;
            for (int i = 0; i < conditions.size(); i++) {
                if(isFirstCondition) {
                    query += conditions.get(i);
                    isFirstCondition = false;
                } else query += " AND " + conditions.get(i);
            }
        }
        ResultSet res = sendSelect(query);

        while(true) {
            try {
                if (!res.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if(isNoFieldSpecified) {
                ResultSetMetaData meta = res.getMetaData();
                int columnCount = meta.getColumnCount();
                int count = 1;
                String[] columnNames = new String[columnCount];

                while(count <= columnCount) {
                    columnNames [count-1] = meta.getColumnLabel(count);
                    count++;
                }
                int cptCurrentField = 0;
                for (String column : columnNames) {
                    System.out.println(column + ": " + res.getString(column));
                    returnArray.put(column, res.getString(column));
                }
            } else {
                for (String field : fields) {
                    System.out.println(field + ": " + res.getString(field));
                    returnArray.put(field, res.getString(field));
                }
            }
        }
        return returnArray;
    }

    public static void main(String[] args) throws SQLException {
        //QueryManager test = new QueryManager();
        //test.getQuery("person", formateConditions("id_person = 1", "name = 'Thierry'"), "password", "date_naissance");
        //test.getQuery("person", formateConditions("id_person = 1", "name = 'Thierry'"), "*");
        //test.closeConnection();
    }

    public ResultSet sendSelect(String query) {
        ResultSet res = null;
        try {
            res = st.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static ArrayList<String> formateConditions(String... conditions) {
        return new ArrayList<String>(Arrays.asList(conditions));
    }
    
    
    
}
