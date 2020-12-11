package dao;

import entity.AnswerEntity;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AnswerDao implements Dao<AnswerEntity>{

    @Override
    public void add(AnswerEntity answerEntity) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();

            String sql = "INSERT INTO answers (text, question_id, student_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, answerEntity.getText());
            statement.setInt(2, answerEntity.getQuestionId());
            statement.setInt(3, answerEntity.getStudentId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                answerEntity.setId(Integer.parseInt(generatedKeys.getString(1)));
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public AnswerEntity getById(int id) throws DaoException {
        return null;
    }

    @Override
    public List<AnswerEntity> getList(Filter filter) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();

            PreparedStatement statement;
            if (filter.existTestId() && filter.existStudentId()) {
                String sql = "SELECT * FROM questions q, answers a WHERE q.test_id = ? AND a.student_id = ? AND q.id = a.question_id";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, filter.getTestId());
                statement.setInt(2, filter.getStudentId());
            } else {
                String sql = "SELECT * FROM questions q, answers a WHERE q.id = a.question_id";
                statement = connection.prepareStatement(sql);
            }

            ResultSet resultSet = statement.executeQuery();
            LinkedList<AnswerEntity> answers = new LinkedList<AnswerEntity>();
            while (resultSet.next()) {
                int id = resultSet.getInt("a.id");
                String text = resultSet.getString("a.text");
                int questionId = resultSet.getInt("q.id");

                AnswerEntity answer = new AnswerEntity();
                answer.setText(String.valueOf(id));
                answer.setText(text);
                answer.setQuestionId(questionId);

                answers.add(answer);
            }

            return answers;
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public void delete(AnswerEntity answerEntity) throws DaoException {

    }
}
