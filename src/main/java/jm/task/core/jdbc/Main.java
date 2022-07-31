package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Mukhammad", "Davronov", (byte) 28);
        service.saveUser("Elon", "Musk", (byte) 51);
        service.saveUser("Tom", "Cruise", (byte) 60);
        service.saveUser("Iron", "Man", (byte) 57);
        service.removeUserById(1);
        System.out.println(service.getAllUsers());
        service.dropUsersTable();
        Util.getFactory().close();
    }
}

