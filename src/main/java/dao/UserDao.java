package dao;

import entity.RoleEntity;
import entity.UserEntity;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDao implements Dao<UserEntity> {

    @Override
    public void add(UserEntity userEntity) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();

            String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, userEntity.getName());
            statement.setString(2, userEntity.getEmail());
            statement.setString(3, userEntity.getPassword());
            statement.setString(4, String.valueOf(userEntity.getRole()));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            userEntity.setId(generatedKeys.getInt(1));
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        }finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public UserEntity getById(int id) throws DaoException {
        return null;
    }

    @Override
    public List<UserEntity> getList(Filter filter) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();

            PreparedStatement statement;
            if (filter.existTestId()) {
                String sql = "SELECT * FROM users u, complete_tests t WHERE t.test_id = ? AND t.student_id = u.id";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, filter.getTestId());
            } else if (filter.existEmail()) {
                String sql = "SELECT * FROM users u WHERE email = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, filter.getEmail());
            } else {
                String sql = "SELECT * FROM users u";
                statement = connection.prepareStatement(sql);
            }

            ResultSet resultSet = statement.executeQuery();
            LinkedList<UserEntity> users = new LinkedList<UserEntity>();
            while (resultSet.next()) {
                int id = resultSet.getInt("u.id");
                String name = resultSet.getString("u.name");
                String email = resultSet.getString("u.email");
                String password = resultSet.getString("u.password");
                String role = resultSet.getString("u.role");

                UserEntity user = new UserEntity();
                user.setId(id);
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setRole(RoleEntity.valueOf(role));

                users.add(user);
            }

            return users;
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        }finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public void delete(UserEntity userEntity) throws DaoException {

    }
}
