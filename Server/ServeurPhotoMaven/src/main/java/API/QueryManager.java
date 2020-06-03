package API;

import Hashage.HashManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;


public class QueryManager {
    private String URL, user, password;
    Connection connection = null;
    Statement st = null;

    public QueryManager () {
    	this.URL = "jdbc:mysql://localhost:3308/serveurphoto?autoReconnect=true&useSSL=false";
        this.user = "root";
        this.password = "";

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

    private void closeConnection () {
        try {
            assert connection != null;
            assert st != null;

            connection.close();
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Hashtable<String, String> getQuery (String table, ArrayList<String> conditions, String... fields) throws SQLException {

        Hashtable<String, String> returnArray = new Hashtable<String, String>();

        String query = "SELECT ";
        boolean isFirstField = true;
        boolean isNoFieldSpecified = false;
        int nbFields = 0;

        for (String field : fields) {
            nbFields++;
            if (field.equals("*")) isNoFieldSpecified = true;
            if (isFirstField) {
                query += field;
                isFirstField = false;
            } else query += ", " + field;
        }
        if (nbFields != 1 && isNoFieldSpecified) {
            System.out.println("ERREUR: ELEMENT * AVEC UN AUTRE CHAMP");
            returnArray.put("ERREUR", "ELEMENT * AVEC UN AUTRE CHAMP");
            return returnArray;
        }
        query += " FROM " + table + " ";
        if (conditions.size() != 0) {
            query += "WHERE ";
            boolean isFirstCondition = true;
            for (int i = 0; i < conditions.size(); i++) {
                if (isFirstCondition) {
                    query += conditions.get(i);
                    isFirstCondition = false;
                } else query += " AND " + conditions.get(i);
            }
        }
        System.out.println(query);
        ResultSet res = sendSelect(query);

        while (true) {
            try {
                if (!res.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (isNoFieldSpecified) {
                ResultSetMetaData meta = res.getMetaData();
                int columnCount = meta.getColumnCount();
                int count = 1;
                String[] columnNames = new String[columnCount];

                while (count <= columnCount) {
                    columnNames[count - 1] = meta.getColumnLabel(count);
                    count++;
                }
                int cptCurrentField = 0;
                for (String column : columnNames) {
                    if (res.getString(column) == null) {
                        returnArray.put(column, "null");
                        System.out.println(column + ": null");
                    } else {
                        returnArray.put(column, res.getString(column));
                        System.out.println(column + ": " + res.getString(column));
                    }
                    returnArray.put(column, res.getString(column));
                }
            } else {
                for (String field : fields) {
                    if (res.getString(field) == null) {
                        System.out.println(field + ": null");
                        returnArray.put(field, "null");
                    } else {
                        System.out.println(field + ": " + res.getString(field));
                        returnArray.put(field, res.getString(field));
                    }
                }
            }
        }
        closeConnection();
        return returnArray;
    }

    public ResultSet sendSelect (String query) {
        ResultSet res = null;
        try {
            res = st.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static ArrayList<String> formateConditions (String... conditions) {
        return new ArrayList<String>(Arrays.asList(conditions));
    }

    public int createUser (String name, String last_name, String mail, byte[] password, String date_naissance, int isAdmin) {
        int res = 0;
        try {
            try {
                ResultSet resSQL = st.executeQuery("SELECT * FROM person WHERE mail = '" + mail + "'");
                if (resSQL.next()) {
                    System.out.println("ERREUR: L'ADRESSE MAIL FOURNIE EST DEJA ENREGISTREE DANS LA BASE");
                    res = 99;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (res != 99) {
            try {
                res = st.executeUpdate("INSERT INTO person VALUES(0, '" + name + "', '" + last_name + "', '" + mail + "', '" + password + "', '" + date_naissance + "', " + isAdmin + ")");
                System.out.println(res);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return res;
    }


    public int insert (String query) {
        int res = 0;
        try {
            res = st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return res;
        }
    }

    public int setFieldAttribute (String condition, boolean isTrue) { //setPhotoDisplay setAdmin
        int res = 0;
        int tinyInt = 0;
        if (isTrue) tinyInt = 1;

        String query = "UPDATE PHOTO SET is_displayed = '" + tinyInt + "' WHERE " + condition;
        try {
            res = st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return res;
        }
    }

    public int setInfosUser (String name, String last_name, String mail, String password, String date_naissance, int id_person) {
        int res = 0;

        String query = "UPDATE PERSON SET name = '" + name + "', last_name ='" + last_name + "', mail = '" + mail + "', password = '" + HashManager.hash(password) + "', date_naissance = '" + date_naissance + "'  WHERE id_person = '" + id_person + "'";

        try {
            res = st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        } finally {
            return res;
        }
    }

    public int uploadPhoto (int id_person, String title, String path, String datePost, String description, String localisation, String tag_wording) { //avec tag
        int res = 0;

        int id_tag = 1; //si on a pas encore de tag dans la bdd

        try{
            ResultSet resIdTag = st.executeQuery("SELECT MAX(id_tag) FROM tag");
            if (resIdTag.next()){
                id_tag = Integer.parseInt(resIdTag.getString(1));
                id_tag++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(uploadPhoto(id_person, title, path, datePost, description, localisation) > 0) {
            try {
                String query = "INSERT INTO TAG VALUES('" + id_tag + "'";
                if(tag_wording == null || tag_wording.trim().equals("")) { //retire les espaces inutiles
                    tag_wording = "null";
                }
                query += ", '" + tag_wording + "')";
                System.out.println(query);
                res = st.executeUpdate(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(res > 0) {
            try {
                boolean isPhoto = false;
                int id_photo = 0;
                System.out.println("SELECT id_photo FROM photo WHERE id_person = '" + id_person + "' AND title = '" + title +"'");
                ResultSet resIdPhoto = st.executeQuery("SELECT id_photo FROM photo WHERE id_person = '" + id_person + "' AND title = '" + title +"'");
                if(resIdPhoto.next()) { //obligé de verifier si next sinon erreur
                    isPhoto = true;
                    id_photo = Integer.parseInt(resIdPhoto.getString(1));
                }

                if(isPhoto) {
                    String query = "INSERT INTO REFERS VALUES (" + id_photo + ", " + id_tag + ")";
                    System.out.println(query);
                    st.executeUpdate(query);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return res;
    }

    public int uploadPhoto (int id_person, String title, String path, String datePost, String description, String localisation) { //sans tag
        if (path == null || title == null || datePost == null) {
            System.out.println("INFORMATIONS MANQUANTES");
            return 0;
        }
        int res = 0;
        String query = "INSERT INTO PHOTO VALUES ('0', '" + id_person + "', '" + title + "', '" + path + "', '" + datePost + "', '" + description + "', '" + localisation + "', '1', '0')";
        try {
            res = st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return res;
        }
    }
}
