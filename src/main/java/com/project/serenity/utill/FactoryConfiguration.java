package com.project.serenity.utill;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("Hibernate.properties"));

            sessionFactory = new Configuration()
                    .setProperties(properties)
                   .addAnnotatedClass(com.project.serenity.entity.Patient.class)
                    .addAnnotatedClass(com.project.serenity.entity.Payment.class)
                    .addAnnotatedClass(com.project.serenity.entity.Therapist.class)
                    .addAnnotatedClass(com.project.serenity.entity.TherapyProgram.class)
                    .addAnnotatedClass(com.project.serenity.entity.TherapySession.class)
                    .addAnnotatedClass(com.project.serenity.entity.User.class)


                    .buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure Hibernate: " + e.getMessage(), e);
        }
    }

    public static FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
