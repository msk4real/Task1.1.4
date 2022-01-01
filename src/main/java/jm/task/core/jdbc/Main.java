package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Util.getSessionFactory();
        UserDaoHibernateImpl uhd = new UserDaoHibernateImpl();
        uhd.dropUsersTable();
        uhd.createUsersTable();
        uhd.saveUser("Anastasiya", "Epishina", (byte)26);
//        uhd.removeUserById(1L);
//        uhd.saveUser("Anastasiya", "Epishina", (byte)26);
//        uhd.saveUser("Anastasiya", "Epishina", (byte)26);
//        uhd.saveUser("Anastasiya", "Epishina", (byte)26);
        List<User> users  = uhd.getAllUsers();
        System.out.println(users);
//        uhd.cleanUsersTable();
    }
}
