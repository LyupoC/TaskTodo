# TodoList Task Manager

No live DEMO of The Task Manager currently

A task manager, Implemented with thymeleaf and spring boot. The main features are:
  * Spring Boot Security with custom database login
  * Spring Boot JPA for data extraction
  * Spring MVC for views, using thymeleaf as a template Manager
  * spring boot validation for form input validation
  * Thymeleaf
  * PostgreSQL

![thymeleaf logo](https://avatars.githubusercontent.com/u/1492367?s=200&v=4)
&emsp;**+**&emsp;<img src="readme_images/springBoot.png?raw=true" width="300" alt="springBoot logo" />
&emsp;**+**<img src="readme_images/postgreSqlLogo.png" width="230" alt="postgreSql logo" />

## Interfaces
|| Web | Mobile |
|:--|:-----:|-----:|
|List|<img src="readme_images/listsList.png" width="600" alt="" />  |  <img src="readme_images/listList_mobile.png" width="210" alt="" />|
|New List|<img src="readme_images/createList.png" width="610" alt="" />  | <img src="readme_images/newList_mobile.png" width="210" alt="" /> |
|Update List| <img src="readme_images/updateList.png" width="610" alt="" />  |  <img src="readme_images/updateList_mobile.png" width="210" alt="" /> |
|Task View|<img src="readme_images/taskView.png" width="610" alt="" style="min-width:300px;" />|<img src="readme_images/taskView_mobile.png" width="210" alt="" />|

## Create Database Tables

Run the database script from sql_scripts/todolist.sql to generate the postgreSQL tables for the application and users and roles.


Default users generated with the script:
```bash
user: susan ; password: demo123
user: john ; password: demo123
```
<img src="readme_images/sqlDiagram.png" width="800" alt="" style="min-width:300px;" />


## Run

using maven and spring boot:

```bash
mvn spring-boot:run
```
or generating manually the jar by building the project with mave3n:

```bash
mvn clean install
```

and then executing the jar from the home project directory

```bash
java -jar .\target\TaskManager-0.0.1-SNAPSHOT.jar
```
