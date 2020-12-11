package controllers;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.Service;
import services.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Controller extends javax.servlet.http.HttpServlet {

    private final Service service = new Service();
    private final Logger logger = LogManager.getLogger();

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String[] str = request.getRequestURI().split("/");
        switch (str[str.length - 1]) {
            case "login.html" :{
                signin(request, response);
                break;
            }
            case "registration.html" :{
                signup(request, response);
                break;
            }
            case "save-test.html": {
                saveTest(request, response);
                break;
            }
            case "save-answer.html": {
                saveAnswer(request, response);
                break;
            }
            default : {
                response.sendError(404);
                break;
            }
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String[] str = request.getRequestURI().split("/");


        switch (str[str.length - 1]){
            case "registration.html":{
                request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request,response);
                break;
            }
            case "login.html":{
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
                break;
            }
            case "list.html":{
                list(request, response);
                break;
            }
            case "add.html": {
                request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);
                break;
            }
            case "answer.html":{
                answer(request,response);
                break;
            }
            case "details.html" :{
                details(request,response);
                break;
            }
            case "delete-test.html": {
                deleteTest(request, response);
                break;
            }
            case "students.html":{
                students(request, response);
                break;
            }
            case "lang.html":{
                lang(request, response);
                break;
            }
            case "signout.html":{
                signout(request, response);
                break;
            }
            default: {
                response.sendError(404);
                break;
            }
        }
    }

    private void signout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void lang(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lang = request.getParameter("value");
        HttpSession session = request.getSession();
        session.setAttribute("lang", lang);
        response.getWriter().println("Language changed");
    }

    private void students(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String testId = request.getParameter("id");

        List<UserEntity> students;
        try {
            students = service.getStudents(testId);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }

        request.setAttribute("students", students);
        request.setAttribute("testId", testId);
        request.getRequestDispatcher("/WEB-INF/jsp/students.jsp").forward(request, response);
    }

    private void deleteTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int testId = Integer.parseInt(request.getParameter("id"));
        try {
            service.deleteTest(testId);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/app/list.html");
    }

    private void details(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String testId = request.getParameter("id");

        TestEntity test;
        try {
            test = service.getTest(testId);
            request.setAttribute("test", test);

            if (request.getParameter("studentId") != null) {
                List<AnswerEntity> answers = service.getAnswers(test, request.getParameter("studentId"));
                request.setAttribute("answers", answers);
            }

            request.getRequestDispatcher("/WEB-INF/jsp/details.jsp").forward(request, response);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        RoleEntity role = RoleEntity.valueOf(request.getParameter("role"));

        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setRole(role);

        boolean signed;
        try {
            signed = service.signup(userEntity);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }

        if (signed) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userEntity);
            response.sendRedirect(request.getContextPath() + "/app/list.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/app/login.html");
        }
    }

    private void signin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(password);

        boolean signed;
        try {
            signed = service.signin(user);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }

        if (signed) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/app/list.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/app/login.html");
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        try {
            List<TestEntity> tests = service.getTests(user);

            if (user.getRole() == RoleEntity.Student) {
                List<TestEntity> completeTests;
                completeTests = service.getTakenTests(user);
                request.setAttribute("completeTests", completeTests);
            }

            request.setAttribute("tests", tests);
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
    }

    private void saveTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        Enumeration<String> parameterNames = request.getParameterNames();

        LinkedList<QuestionEntity> questions = new LinkedList<QuestionEntity>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.startsWith("question-")) {
                QuestionEntity question = new QuestionEntity();
                question.setText(request.getParameter(parameterName));
                question.setCorrectAnswer(request.getParameter("answer-" + parameterName.substring(9)));
                questions.add(question);
            }
        }

        TestEntity test = new TestEntity();
        test.setQuestions(questions);
        test.setTeacherId(user.getId());
        test.setName(request.getParameter("test-name"));

        try {
            service.saveTest(test);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/app/list.html");
    }

    private void saveAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        Enumeration<String> parameterNames = request.getParameterNames();

        LinkedList<AnswerEntity> answers = new LinkedList<AnswerEntity>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.startsWith("question-")) {
                AnswerEntity answer = new AnswerEntity();
                answer.setText(request.getParameter(parameterName));
                answer.setQuestionId(Integer.parseInt(parameterName.substring(9)));
                answer.setStudentId(user.getId());
                answers.add(answer);
            }
        }

        try {
            service.saveAnswers(user, answers, request.getParameter("id"));
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/app/list.html");
    }

    private void answer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String testId = request.getParameter("id");

        TestEntity test;
        try {
            test = service.getTest(testId);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }

        request.setAttribute("test", test);
        request.getRequestDispatcher("/WEB-INF/jsp/answer.jsp").forward(request, response);
    }
}
