package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class Util {

    private static final String CONFIG_NAME = "db.properties";
    private static final Properties GLOBAL_CONFIG = new Properties();
    private static SessionFactory factory;

    static {
        try (Reader fr = new FileReader(CONFIG_NAME)) {
            GLOBAL_CONFIG.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static Configuration getInitializedConfiguration(){
        Configuration configuration = new Configuration()
                .setProperties(GLOBAL_CONFIG)
                .addAnnotatedClass(User.class);
        return configuration;
    }

    public static SessionFactory getFactory() {
        if (factory == null) {
            Configuration configuration = getInitializedConfiguration();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            factory = configuration.buildSessionFactory(serviceRegistry);
        }
        return factory;
    }
}

