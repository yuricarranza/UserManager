# User Manager
This project was created to manage users.
It's a rest Api created using:
- Java
- Spring boot
- Sql server
- Docker

Before start the project is needed to have the following objects in the Sql server database:
```sql
CREATE TABLE [dbo].[User](
[id] [int] NOT NULL,
[name] [varchar](50) NULL,
[email] [varchar](50) NULL,
[phone] [varchar](50) NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[User] ADD PRIMARY KEY CLUSTERED
(
[id] ASC
);
```
Additionally, you should have the application.properties file in the root with the corresponding values:
```
spring.application.name=demo
server.port=8081
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=UserManager;encrypt=false
spring.datasource.username=sa
spring.datasource.password=admin1234.
database.maxRows=5
```
You can find the swagger documentation at:
http://localhost:8081/swagger-ui/index.html