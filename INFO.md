# Work Integrated Learning Management Application (WILMA)

## MySQL Database & User
- [create-db-and-user.sql](sql/create-db-and-user.sql)

## Profiles
- [application.yml](web/src/main/resources/application.yml) - Common settings including setting the active profile
- [application-aws.yml](web/src/main/resources/application-aws.yml) - Settings specific to AWS
- [application-h2.yml](web/src/main/resources/application-h2.yml) - Settings for using an embedded H2 database
- [application-local.yml](web/src/main/resources/application-local.yml) - Local development using a local instance of MySQL database

<table style="undefined;table-layout: fixed; width: 671px">
<colgroup>
<col style="width: 221px">
<col style="width: 450px">
</colgroup>
<thead>
  <tr>
    <th colspan="2">Assignment</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td>Assignment</td>
    <td>Application Development Project</td>
  </tr>
  <tr>
    <td>Due Date</td>
    <td>Friday 7th Oct 2022</td>
  </tr>
  <tr>
    <td>Unit Coordinator</td>
    <td>Lily Li</td>
  </tr>
  <tr>
<td rowspan="4">Team Members</td>
    <td>Nathan Downes</td>
  </tr>
  <tr>
    <td>Nicholas McGuffin</td>
  </tr>
  <tr>
    <td>Rory Allen</td>
  </tr>
<tr>
    <td>Nathan Snow</td>
  </tr>
  <tr>
    <td colspan="2" align="center">Project</td>
  </tr>
  <tr>
    <td>Language</td>
    <td>Java (Spring Boot framework)</td>
  </tr>
  <tr>
    <td>JDK Version</td>
    <td>11</td>
  </tr>
  <tr>
    <td>Build</td>
    <td>Maven</td>
  </tr>
  <tr>
    <td>Main Class</td>
    <td>~/web/src/main/java/com/example/App.java</td>
  </tr>
  <tr>
    <td rowspan="4">Modules</td>
    <td>entity</td>
  </tr>
  <tr>
    <td>repository</td>
  </tr>
  <tr>
    <td>service</td>
  </tr>
  <tr>
    <td>web</td>
  </tr>
  <tr>
    <td>Repository</td>
    <td><a href="https://github.com/nick-mcguffin/Application-Development-Project" target="_blank" title="View the project repository" rel="noopener noreferrer">https://github.com/nick-mcguffin/Application-Development-Project</a></td>
  </tr>
  <tr>
    <td>OpenAPI Dashboard (Swagger)</td>
    <td><a href="http://localhost:8080/swagger-ui/index.html" title="View and test application REST endpoints" target="_blank">http://localhost:8080/swagger-ui/index.html</a></td>
  </tr>
<tr>
    <td>OpenAPI Data (JSON)</td>
    <td><a href="http://localhost:8080/v3/api-docs" title="View and download the application's OpenAPI data" target="_blank">http://localhost:8080/v3/api-docs</a></td>
  </tr>
</tbody>
</table>

The capstone project of a Bachelor of Information Technology (Application Development) at Central Queensland University.

The goal of the project is to complete a fully functioning Work Integrated Learning Management Application (WILMA).
Tasks include:
- Project Management and Planning
- Project Requirements Specification and Design
- Developing a fully functional web application
- Deliver a presentation of the final result

Built using:
- Java 11
- Spring Framework
- MySQL 8.0.26
 
