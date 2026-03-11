Serenity Mental Health Therapy Center System <br>
Serenity is a robust backend-driven management system designed to digitize the operations of a mental health therapy center. It focuses on secure data persistence, therapist-patient coordination, and structured mental health resource delivery. <br>

🛠️ Tech Stack <br>
Language: Java

Framework: Hibernate (ORM) / JPA<br>

Database: MySQL<br>

Build Tool: Maven<br>

Driver: MySQL Connector/J (com.mysql.cj.jdbc.Driver)<br>

🔑 Core Features<br>
Automated Schema Management: Utilizes Hibernate to dynamically manage database tables and relationships.<br>

Secure Authentication: Centralized user management for therapists and patients.<br>

Therapy Session Tracking: Backend logic to manage appointments, session logs, and patient history.<br>

Mental Wellness Assessment: Support for storing and retrieving mental health quizzes and user results.<br>

Data Persistence: Optimized MySQL integration with a custom dialect for high-performance querying.<br>

📁 Database Configuration<br>
The system is configured to connect to a local MySQL instance. Ensure your hibernate.properties or application.properties reflects the following: (make it under src/resources)<br>

Properties<br>
hibernate.connection.driver_class=com.mysql.cj.jdbc.Driver<br>
hibernate.connection.url=jdbc:mysql://localhost:3306/serenity_therapy?createDatabaseIfNotExist=true<br>
hibernate.connection.username=YOUR_Name<br>
hibernate.connection.password=YOUR_PASSWORD<br>
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect<br>
hibernate.show_sql=true<br>
 Set to 'update' for development, 'validate' for production<br>
hibernate.hbm2ddl.auto=update <br><br>
🚀 Getting Started <br>
Prerequisites<br>
Java Development Kit (JDK) 11 or higher<br>

MySQL Server 8.0+<br>

Maven<br>

Installation<br>
Clone the Repository<br>

Bash <br>
git clone https://github.com/AsanI2003/Serenity-Mental-Health-Therapy-Center-System.git <br>
Configure Database <br>

Open the project in your IDE.<br>

Locate the Hibernate configuration file.<br>

Update the hibernate.connection.password to match your local MySQL root password.<br>

Build the Project<br>

Bash<br>
mvn clean install<br>
Run the System<br>
Execute the main entry point class to initialize the Hibernate SessionFactory and start the application.
