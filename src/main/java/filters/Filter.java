package filters;

import com.mysql.cj.Session;
import dao.DaoException;
import dao.TestDao;
import entity.RoleEntity;
import entity.TestEntity;
import entity.UserEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Filter extends HttpFilter {

    private final TestDao testDao = new TestDao();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String[] str = request.getRequestURI().split("/");

        switch (str[str.length - 1]) {
            case "list.html" :{
                requireSignin(request, response, chain);
                break;
            }
            case "students.html":
            case "add.html": {
                requireRole(request, response, chain, RoleEntity.Teacher);
                break;
            }
            case "answer.html": {
                requireRole(request, response, chain, RoleEntity.Student);
                break;
            }
            case "details.html":{
                authorizeDetails(request, response, chain);
                break;
            }
            default: {
                super.doFilter(request, response, chain);
                break;
            }
        }
    }

    private void authorizeDetails(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        String studentId = request.getParameter("studentId");
        String testId = request.getParameter("id");

        if (user != null) {
            if (user.getRole() == RoleEntity.Student && studentId != null && Integer.parseInt(studentId) == user.getId()) {
                super.doFilter(request, response, chain);
                return;
            }

            TestEntity test;
            try {
                test = testDao.getById(Integer.parseInt(testId));
            } catch (DaoException e) {
                response.sendError(500);
                return;
            }

            if (test == null) {
                response.sendError(404);
                return;
            }

            if (user.getRole() == RoleEntity.Teacher && test.getTeacherId() == user.getId()) {
                super.doFilter(request, response, chain);
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/app/login.html");
    }

    private void requireSignin(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/app/login.html");
        } else {
            super.doFilter(request, response, chain);
        }
    }

    private void requireRole(HttpServletRequest request, HttpServletResponse response, FilterChain chain, RoleEntity role) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        if (user == null || user.getRole() != role) {
            response.sendRedirect(request.getContextPath() + "/app/login.html" );
        } else {
            super.doFilter(request, response, chain);
        }
    }
}
