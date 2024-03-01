# User Management Application (CRUD)
![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![image](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![image](https://img.shields.io/badge/Vue%20js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D)
![image](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![image](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white) 
![image](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![image](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)

## Description 
This project is a basic CRUD user management web application with (Rest API), enabling to easily create, edit, and delete user information.
Built with the following technologies:

- **Frontend**: Vue3, HTML, CSS, Vue Validation
- **Backend**: Spring Boot version 3.1.4  
- **Build**: Gradle
- **Database**: MySQL
- **Container**: Docker

All components of the application (frontend, backend, and database) run in Docker containers.<br>

## Features
- User Search with criteria
- User Delete
- User Edit
- Create User

## Getting started
**Prerequisites**: Docker Desktop <br>
If you do not have Docker Desktop installed, install it from the following URL: <br> https://www.docker.com/products/docker-desktop/

#### 1. Start Docker:
- Start Docker<br>
 Move to {projectRoot}/docker/ run following command<br> 
```docker-compose up -d --build```<br>
- Verify that Docker is running: <br>
```docker compose ps```<br>

#### 2. Start Spring Boot at Backend
- Log in to the backend Docker container:<br>
```docker exec -it backend bash```<br>
- Start Spring Boot:<br>
```./gradlew bootRun```<br>

#### 3. Start Vue Server at Frontend
- Log in to the frontend Docker container:<br>
```docker exec -it frontend bash```<br>
- Start Vue server:<br>
```npm run serve```<br>

#### 4. Access to Application
- Now you can access to the application from your blowser :) <br>
```http://localhost:9000/search```

## API Specifications
Basic API Specifications for User Management Application API
|Description|Http Method|Path|Request|Example|
| :--- | :--- | :--- | :--- | :--- |
| Search User <br> With Criteria | Get | /search |  <b>URI Parameters</b><br>id: number &nbsp;&nbsp;  Example: 1<br>name: string &nbsp;&nbsp; Example: "Galireo"</br>address: string &nbsp;&nbsp; Example: "Firenze"<br>tel: string  &nbsp;&nbsp; Example: "345"<br>countryCode: string &nbsp;&nbsp; Example: "ITA"| /search/?id=1&name="Galireo" |
|Search User| Get | /edit/{id} |<b>URI Parameters</b><br> id: number (required) &nbsp;&nbsp; Example: 1 | /edit/1|
|Update User| Patch | /update | <b>With Body </b><br>id: number (required) &nbsp;&nbsp; Example: 1 <br> name: string (required) &nbsp;&nbsp; Example: "Galireo"<br>address: string (required) &nbsp;&nbsp; Example: "Firenze"<br>tel: string (required) &nbsp;&nbsp; Example: "345"<br>country.code: string (required) &nbsp;&nbsp; Example: "ITA"|{<br>&nbsp;&nbsp; id: { <br>&nbsp;&nbsp;&nbsp;&nbsp; value : 15 <br>&nbsp;&nbsp;} ,<br>&nbsp;&nbsp;name: { <br>&nbsp;&nbsp;&nbsp;&nbsp; value : "Galileo Galilei" <br>&nbsp;&nbsp;} ,<br>&nbsp;&nbsp; address: { <br>&nbsp;&nbsp;&nbsp;&nbsp;value : "Piazza Navona, 00186 Roma" <br>&nbsp;&nbsp;} ,<br>&nbsp;&nbsp; tel: { <br>&nbsp;&nbsp;&nbsp;&nbsp;  value : "2324232432" <br>&nbsp;&nbsp;} ,<br> &nbsp;&nbsp; country : {<br>&nbsp;&nbsp;&nbsp;&nbsp; name: "",<br>&nbsp;&nbsp;&nbsp;&nbsp; code: "ITA"<br>&nbsp;&nbsp; }<br>}| 
|Create User|Post|/add|<b>With Body</b><br>name: string (required) &nbsp;&nbsp; Example: "Galireo"<br>address: string (required) &nbsp;&nbsp; Example: "Firenze"<br>tel: string (required) &nbsp;&nbsp; Example: "345"<br>country.code: string (required) &nbsp;&nbsp; Example: "ITA"|{<br>&nbsp;&nbsp; name: { <br>&nbsp;&nbsp;&nbsp;&nbsp; value : "Galileo Galilei"<br>&nbsp;&nbsp;} ,<br> &nbsp;&nbsp; address: { <br>&nbsp;&nbsp;&nbsp;&nbsp;  value : "Piazza Navona, 00186 Roma" <br>&nbsp;&nbsp;} ,<br>&nbsp;&nbsp; tel: { <br>&nbsp;&nbsp;&nbsp;&nbsp;value : "2324232432" <br>&nbsp;&nbsp;} ,<br>&nbsp;&nbsp; country : {<br>&nbsp;&nbsp;&nbsp;&nbsp; name: "",<br>&nbsp;&nbsp;&nbsp;&nbsp; code: "ITA"<br>&nbsp;&nbsp; }<br>}|
|Delete user|Delete|/delete|<b>With Body</b><br>value: integer (required) &nbsp;&nbsp; Exsample: 1|<b>With Body </b><br>{ value : 1 } 

