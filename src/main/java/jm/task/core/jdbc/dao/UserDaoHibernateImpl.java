package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String CREATE_TABLE = "CREATE TABLE if not exists users " +
                "(id BIGINT not NULL  AUTO_INCREMENT," +
                " name VARCHAR(70), " +
                " lastName VARCHAR(70), " +
                " age TINYINT, " +
                " PRIMARY KEY ( id ))";
        Session session = Util.getFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
            System.out.println("User: " + name + "/" + lastName + "/" + age + " added to db!");
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Session session = Util.getFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM User").list();
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}
