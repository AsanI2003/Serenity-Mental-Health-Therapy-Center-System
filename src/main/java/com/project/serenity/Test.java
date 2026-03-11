package com.project.serenity;

import com.project.serenity.entity.User;
import com.project.serenity.utill.FactoryConfiguration;
import org.hibernate.Session;

public class Test {
    public static void main(String[] args) {
        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();

            User user = new User(1,"","","");
            session.save(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Hibernate test completed successfully.");
    }
}
