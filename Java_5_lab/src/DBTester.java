import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBTester {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/groupdb?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_Driver = "com.mysql.cj.jdbc.Driver";

    public static void main(String... args) {
        try {
            //viewItemsInGroupWithJoin(connectToDB(), "Поэма");
            //removeItemFromGroup(connectToDB(), "Три сестры", "Фантастика");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception");
        }
    }

    public static void test() {
        Connection connection;
        try {
            connection = connectToDB();
            doWork();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("ClassNotFoundException");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException");
        }
    }

    public static void doWork() throws SQLException{

    }

    public static Connection connectToDB() throws SQLException, ClassNotFoundException {
        Class.forName(DB_Driver);
        Connection connection = DriverManager.getConnection(DB_URL, "root", "root1");
        System.out.println("Connection Succesful");
        return connection;
    }

    public static void viewItems(Connection connection) throws SQLException{
        String getAuthorsQuery = "SELECT * FROM books";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getAuthorsQuery);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String book = resultSet.getString(2);
            int genreNum = resultSet.getInt(3);
            System.out.println("id: " + id + "   book: " + book + "   genreNum: " + genreNum);
        }
        resultSet.close();
    }

    public static void viewGroups(Connection connection) throws SQLException{
        String getGroupsString = "SELECT * FROM group_books";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getGroupsString);
        while(resultSet.next()){
            int genreNum = resultSet.getInt(1);
            String genre = resultSet.getString(2);
            System.out.println("genreNum: " + genreNum + "   genre: " + genre);
        }
        resultSet.close();
    }

    public static void viewUniversal(Connection connection, String table) throws  SQLException{
        String queryString = "SELECT * FROM " + table;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(queryString);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsCount = resultSetMetaData.getColumnCount();
        while(resultSet.next()){
            for (int i = 0; i < columnsCount; i++){
                //resultSetMetaData.getColumnClassName(i)
                System.out.printf(resultSetMetaData.getColumnName(i) + ": " + resultSet.getObject(i) + "   ");
            }
        }
        resultSet.close();
    }
    public static Integer getGroupID(Connection connection, String key) throws  SQLException{
        String prepareStatementString = "SELECT id_of_genre FROM group_books WHERE genre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(prepareStatementString);
        preparedStatement.setString(1, key);
        ResultSet resultSet = preparedStatement.executeQuery();
        int id = resultSet.getInt(1);
        resultSet.close();
        return id;
    }

    public static void viewItemsInGroup(Connection connection, String groupName) throws SQLException {
        int id = getGroupID(connection, groupName);
        viewItemsInGroup(connection, id);
    }

    public static void viewItemsInGroupWithJoin(Connection connection, String groupName) throws SQLException{
        Statement statement = connection.createStatement();
        String statementString = "SELECT books.book, group_books.genre FROM books INNER JOIN group_books ON books.id_of_genre = group_books.id_of_genre";
        ResultSet resultSet = statement.executeQuery(statementString);
        String prepareStatementString = "SELECT book FROM " +
                "(SELECT * FROM (SELECT books.book as book, group_books.genre as genre FROM books INNER JOIN group_books ON books.id_of_genre = group_books.id_of_genre) AS resultSet WHERE genre = ?) as t2";
        PreparedStatement preparedStatement = connection.prepareStatement(prepareStatementString);
        preparedStatement.setString(1, groupName);
        ResultSet resultSet2 = preparedStatement.executeQuery();
        while(resultSet2.next()) {
            System.out.println("book:  " + resultSet2.getObject(1));
        }
        resultSet.close();
    }
    public static void viewItemsInGroup(Connection connection, int groupID) throws  SQLException{
        String preparedStatementString = "SELECT book FROM books WHERE id_of_genre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementString);
        preparedStatement.setInt(1, groupID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println("book: " + resultSet.getString(1));
        }
        resultSet.close();
    }
    public static void addItemToGroup(Connection connection, String book,  String groupName) throws SQLException{
        String prepareStatementString = "UPDATE books SET id_of_genre = ? WHERE book = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(prepareStatementString);
        preparedStatement.setString(1, getGroupID(connection, groupName).toString());
        preparedStatement.setString(2, book);
        ResultSet resultSet = preparedStatement.executeQuery();
    }
    static void removeItemFromGroup(Connection connection, String book, String groupname)throws SQLException{
        String prepareStatementString = "UPDATE books SET id_of_genre = NULL WHERE book = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(prepareStatementString);
        preparedStatement.setString(1, book);
        ResultSet resultSet = preparedStatement.executeQuery();
    }
    //книга + фантастика
    private static void transactionOfManipulatingItems(Connection connection) throws SQLException{
        String line;
        connection.setAutoCommit(false);
        String addingItemToGroupString = "INSERT INTO books(book,id_of_genre) VALUES(?,?)";
        String deleteItemFromGroupString = "DELETE FROM books WHERE book=?";
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/txt/input.txt"))){
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("([а-я]*)([+-])([а-я]*)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);
                matcher.matches();
                String group = matcher.group(1);
                String action = matcher.group(2);
                String item = matcher.group(3);
                switch (action){
                    case "-":
                        PreparedStatement preparedStatement1 = connection.prepareStatement(deleteItemFromGroupString);
                        preparedStatement1.setString(1, item);
                        preparedStatement1.executeUpdate();
                        break;
                    case "+":
                        PreparedStatement preparedStatement2 = connection.prepareStatement(addingItemToGroupString);
                        preparedStatement2.setString(1, item);
                        preparedStatement2.setString(2, getGroupID(connection, group).toString());
                        preparedStatement2.executeUpdate();
                        break;
                }
            }
            connection.commit();
            connection.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (SQLException e){
            System.out.println("Transaction didn't evaluate, connection was rollbacked");
            e.printStackTrace();
            connection.rollback();
            connection.close();
        }
    }
    //+комедия
    private static void transactionOfEditingGroups(Connection connection) throws SQLException{
        String line;
        String getResultSetString = "SELECT * FROM group_books";
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(getResultSetString);
        String addingString = "INSERT INTO group_books(genre) VALUES(?)";
        String deletingString = "DELETE FROM group_books WHERE genre=?";
        Set<String> groupsToAdd = new HashSet<>();
        Set<String> groupsToDelete = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/txt/groups.txt"));
            while ((line = bufferedReader.readLine()) != null) {
                char action = line.charAt(0);
                String groupName = line.substring(1);
                switch(action){
                    case '+':
                        groupsToAdd.add(groupName);
//                        PreparedStatement preparedStatement1 = connection.prepareStatement(addingString);
//                        preparedStatement1.setString(1, groupName);
//                        preparedStatement1.executeUpdate();
                        break;
                    case '-':
                        groupsToDelete.add(groupName);
//                        PreparedStatement preparedStatement2 = connection.prepareStatement(deletingString);
//                        preparedStatement2.setString(1, groupName);
//                        preparedStatement2.executeUpdate();
                        break;
                }
            }

            for (String groupName : groupsToAdd) {
                PreparedStatement preparedStatement = connection.prepareStatement(addingString);
                preparedStatement.setString(1, groupName);
                preparedStatement.executeUpdate();
            }
            for (String groupName : groupsToDelete){
                PreparedStatement preparedStatement = connection.prepareStatement(deletingString);
                preparedStatement.setString(1, groupName);
                preparedStatement.executeUpdate();
            }
            connection.commit();
            connection.close();
            System.out.println("Transaction was commited");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            System.out.println("Transaction didn't evaluate, connection was rollbacked");
            e.printStackTrace();
            connection.rollback();
            connection.close();
        }
    }

}
