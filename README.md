Serenity Mental Health Therapy Center System
Serenity is a robust backend-driven management system designed to digitize the operations of a mental health therapy center. It focuses on secure data persistence, therapist-patient coordination, and structured mental health resource delivery.

🛠️ Tech Stack
Language: Java

Framework: Hibernate (ORM) / JPA

Database: MySQL

Build Tool: Maven

Driver: MySQL Connector/J (com.mysql.cj.jdbc.Driver)

🔑 Core Features
Automated Schema Management: Utilizes Hibernate to dynamically manage database tables and relationships.

Secure Authentication: Centralized user management for therapists and patients.

Therapy Session Tracking: Backend logic to manage appointments, session logs, and patient history.

Mental Wellness Assessment: Support for storing and retrieving mental health quizzes and user results.

Data Persistence: Optimized MySQL integration with a custom dialect for high-performance querying.

📁 Database Configuration
The system is configured to connect to a local MySQL instance. Ensure your hibernate.properties or application.properties reflects the following: (make it under src/resources)

Properties
hibernate.connection.driver_class=com.mysql.cj.jdbc.Driver
hibernate.connection.url=jdbc:mysql://localhost:3306/serenity_therapy?createDatabaseIfNotExist=true
hibernate.connection.username=YOUR_Name
hibernate.connection.password=YOUR_PASSWORD
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
hibernate.show_sql=true
# Set to 'update' for development, 'validate' for production
hibernate.hbm2ddl.auto=update 
🚀 Getting Started
Prerequisites
Java Development Kit (JDK) 11 or higher

MySQL Server 8.0+

Maven

Installation
Clone the Repository

Bash
git clone https://github.com/AsanI2003/Serenity-Mental-Health-Therapy-Center-System.git
Configure Database

Open the project in your IDE.

Locate the Hibernate configuration file.

Update the hibernate.connection.password to match your local MySQL root password.

Build the Project

Bash
mvn clean install
Run the System
Execute the main entry point class to initialize the Hibernate SessionFactory and start the application.
