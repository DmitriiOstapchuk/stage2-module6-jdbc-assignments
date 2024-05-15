package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJDBCRepository {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    private static final String createUserSQL = "insert into myusers (firstName, lastName, age) values (?, ?, ?)";
    private static final String updateUserSQL = "update myusers set firstName = ?, lastName = ?, age = ? where id = ?";
    private static final String deleteUser = "delete from myusers where id = ?";
    private static final String findUserByIdSQL = "select * from myusers where id = ?";
    private static final String findUserByNameSQL = "select * from myusers where firstName = ?";
    private static final String findAllUserSQL = "select * from myusers";

    public Long createUser(User user) {
        try (Connection connection = CustomDataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(createUserSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findUserById(Long userId) {
        try (Connection connection = CustomDataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findUserByIdSQL);
            preparedStatement.setLong(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setFirstName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getInt(4));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findUserByName(String userName) {
        try (Connection connection = CustomDataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findUserByNameSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setFirstName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getInt(4));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findAllUser() {
        try (Connection connection = CustomDataSource.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(findAllUserSQL);
            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setFirstName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getInt(4));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User updateUser() {
        try (Connection connection = CustomDataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "firstName");
            preparedStatement.setString(2, "lastName");
            preparedStatement.setInt(3, 30);
            preparedStatement.setLong(4, 1);
            preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setFirstName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getInt(4));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteUser(Long userId) {
        try (Connection connection = CustomDataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteUser);
            preparedStatement.setLong(1, userId);
            ResultSet result = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
