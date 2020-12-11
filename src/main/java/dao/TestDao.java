package dao;

import entity.QuestionEntity;
import entity.TestEntity;
import entity.UserEntity;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TestDao implements Dao<TestEntity>{
    @Override
    public void add(TestEntity testEntity) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();

            String sql = "INSERT INTO tests (name, teacher_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, testEntity.getName());
            statement.setInt(2, testEntity.getTeacherId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            testEntity.setId(generatedKeys.getInt(1));

            String sql2 = "INSERT INTO questions (task, test_id, correct_answer) VALUES (?, ?, ?)";
            for (QuestionEntity question : testEntity.getQuestions()) {
                statement = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, question.getText());
                statement.setInt(2, testEntity.getId());
                statement.setString(3, question.getCorrectAnswer());
                statement.executeUpdate();

                generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                question.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public TestEntity getById(int id) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();

            String selectTests = "SELECT * FROM tests t, questions q WHERE t.id = ? AND t.id = q.test_id";
            PreparedStatement statement = connection.prepareStatement(selectTests);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            TestEntity testEntity = null;
            LinkedList<QuestionEntity> questions = new LinkedList<QuestionEntity>();
            if (resultSet.next()) {
                int testId = resultSet.getInt("t.id");
                String name = resultSet.getString("t.name");
                int teacherId = resultSet.getInt("t.teacher_id");

                testEntity = new TestEntity();
                testEntity.setId(testId);
                testEntity.setName(name);
                testEntity.setTeacherId(teacherId);

                do {
                    QuestionEntity question = new QuestionEntity();
                    int questionId = resultSet.getInt("q.id");
                    String text = resultSet.getString("q.task");
                    String correctAnswer = resultSet.getString("q.correct_answer");

                    question.setId(questionId);
                    question.setText(text);
                    question.setCorrectAnswer(correctAnswer);
                    questions.add(question);
                } while (resultSet.next());

                testEntity.setQuestions(questions);
            }

            return testEntity;
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public List<TestEntity> getList(Filter filter) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();

            PreparedStatement statement;
            if (filter.existTeacherId()) {
                String sql = "SELECT * FROM tests WHERE teacher_id = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, filter.getTeacherId());
            } else if (filter.existStudentId()) {
                String sql = "SELECT * FROM tests t, complete_tests ct WHERE ct.test_id = t.id AND ct.student_id = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, filter.getStudentId());
            } else {
                String sql = "SELECT * FROM tests";
                statement = connection.prepareStatement(sql);
            }

            ResultSet resultSet = statement.executeQuery();
            LinkedList<TestEntity> tests = new LinkedList<TestEntity>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int teacherId = resultSet.getInt("teacher_id");

                TestEntity test = new TestEntity();
                test.setId(id);
                test.setName(name);
                test.setTeacherId(teacherId);

                tests.add(test);
            }
            return tests;
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public void delete(TestEntity testEntity) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();
            String sql = "DELETE FROM tests WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, testEntity.getId());
            statement.executeUpdate();

            String sql2 = "DELETE questions, answers FROM questions INNER JOIN answers WHERE questions.id = answers.question_id AND questions.test_id = ?";
            statement = connection.prepareStatement(sql2);
            statement.setInt(1, testEntity.getId());
            statement.executeUpdate();

            String sql3 = "DELETE FROM complete_tests WHERE test_id = ?";
            statement = connection.prepareStatement(sql3);
            statement.setInt(1, testEntity.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    public void saveCompleteTest(UserEntity user, String testId) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getConnectionPool();
            connection = pool.getConnection();

            String sql = "INSERT INTO complete_tests (test_id, student_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, testId);
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }
}
