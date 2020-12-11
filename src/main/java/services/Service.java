package services;

import dao.*;
import entity.AnswerEntity;
import entity.RoleEntity;
import entity.TestEntity;
import entity.UserEntity;

import java.util.List;

public class Service {
    private final UserDao userDao = new UserDao();
    private final TestDao testDao = new TestDao();
    private final AnswerDao answerDao = new AnswerDao();

    public boolean signin(UserEntity userEntity) throws ServiceException {
        Filter filter = new Filter();
        filter.setEmail(userEntity.getEmail());
        UserEntity existingUser;

        try {
            existingUser = userDao.getList(filter).stream().findFirst().orElse(null);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        if (existingUser == null || !existingUser.getPassword().equals(userEntity.getPassword())) {
            return false;
        }

        userEntity.setId(existingUser.getId());
        userEntity.setName(existingUser.getName());
        userEntity.setRole(existingUser.getRole());

        return true;
    }

    public boolean signup(UserEntity user) throws ServiceException {
        Filter filter = new Filter();
        filter.setEmail(user.getEmail());
        UserEntity existingUser;
        try {
            existingUser = userDao.getList(filter).stream().findFirst().orElse(null);
            if (existingUser != null) {
                return false;
            }
            userDao.add(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    public List<TestEntity> getTests(UserEntity userEntity) throws ServiceException {
        Filter filter = new Filter();
        if (userEntity.getRole() == RoleEntity.Teacher) {
            filter.setTeacherId(userEntity.getId());
        }

        try {
            return testDao.getList(filter);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void saveTest(TestEntity test) throws ServiceException {
        try {
            testDao.add(test);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void saveAnswers(UserEntity user, List<AnswerEntity> answers, String testId) throws ServiceException {
        try {
            for (AnswerEntity answer : answers) {
                answerDao.add(answer);
            }

            testDao.saveCompleteTest(user, testId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public TestEntity getTest(String testId) throws ServiceException {
        try {
            return testDao.getById(Integer.parseInt(testId));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<AnswerEntity> getAnswers(TestEntity test, String id) throws ServiceException {
        Filter filter = new Filter();
        filter.setTestId(test.getId());
        filter.setStudentId(Integer.valueOf(id));

        try {
            return answerDao.getList(filter);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteTest(int testId) throws ServiceException {
        TestEntity test = new TestEntity();
        test.setId(testId);

        try {
            testDao.delete(test);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<UserEntity> getStudents(String testId) throws ServiceException {
        Filter filter = new Filter();
        filter.setTestId(Integer.parseInt(testId));
        try {
            return userDao.getList(filter);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<TestEntity> getTakenTests(UserEntity userEntity) throws ServiceException {
        Filter filter = new Filter();
        filter.setStudentId(userEntity.getId());

        try {
            return testDao.getList(filter);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
