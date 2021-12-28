package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String QUERY = "SELECT * FROM users";
    private String INSERT = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?);";
    private String DELETE = "DELETE FROM users WHERE id = ?";
    private String DROP = "DROP TABLE users";
    private String CLEAN = "DELETE FROM users";
    private String CREATE = "CREATE TABLE users("
            + "id BIGINT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
            + "name VARCHAR(255),"
            + "lastName VARCHAR (255),"
            + "age TINYINT(3))";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP)) {
            preparedStatement.execute(DROP);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            PreparedStatement allUsers = connection.prepareStatement(QUERY);
            ResultSet rs = allUsers.executeQuery();

            while (rs.next()) {
                String name1 = rs.getString("name");
                String lastName1 = rs.getString("lastName");
                Byte age1 = rs.getByte("age");

                users.add(new User(name1, lastName1, age1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                Byte age = rs.getByte("age");

                users.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN)) {
            preparedStatement.execute(CLEAN);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
