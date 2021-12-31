package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            session.beginTransaction();
            session.createSQLQuery(sql);
            session.getTransaction().commit();
            System.out.println("Таблица \"users\" создана");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            String sql = "Drop table if exists users";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица \"users\" удалена");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Добавлен новый юзер в таблицу \"users\"");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("Юзер удалён по ID");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            String sql = "Select * from users";
            users = session.createSQLQuery(sql).list();
            session.getTransaction().commit();
            System.out.println("Список всех юзеров:");
        } catch (Exception e) {
            e.getStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("Delete from users").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Список всех юзеров удалён");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
