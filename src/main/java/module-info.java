module com.project.serenity {
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;
    requires com.jfoenix;
    requires javafx.controls;
    requires static lombok;
    requires bcrypt;
    opens com.project.serenity.entity to org.hibernate.orm.core;
    exports com.project.serenity.entity;
    opens com.project.serenity.controller to javafx.fxml;

    exports com.project.serenity;
}