# AcademicRecordBook
This is a learning project for me to learn and use Java and Spring Framework to build a web application.

# Project Goals
* Create an application that stores students grades.
* There will be three roles: Administrator, Instructor, and Student.
* A student can view their grades.
* An instructor can add and modify a student's grade that is in their class.
* An administrator can create accounts for both instructors and students.
* An administrator can also do what an instructor can do.
* All roles will need to log in to their accounts.
* Data will be saved to a database (will use H2 Database).

# Build Environment
* Intellij IDEA Community Edition 2021.1
* OpenJDK 11
* Apache Maven 3.8.1

# Java Installation Steps
1. Download Java openjdk-11 archive and extract it to any directory. (E.g. c:\java\) (https://jdk.java.net/java-se-ri/11)
2. Append the file path of openjdk-11 to "Path" in "System Variables". (E.g. "c:\java\openjdk-11\bin" for Windows O.S.)
3. Add a new System Variable with the variable name "JAVA_HOME", and the variable value with the path of where openjdk-11 is stored. (E.g. "c:\java\openjdk-11" - exclude the bin.)
4. Open command prompt/terminal and run `java --version` to check if Java has been installed.

# Maven Installation Steps
1. Follow the provided link to install maven. (https://maven.apache.org/install.html)

# User Stories
ToDo:
* 005 - As an Administrator, I want to modify an existing account, so that I can update the account holders information.

InProgress:


Done:
* 001 - As an Administrator, I want to log into my account that has administrator privilege, so that I can have access administrator actions.
* 002 - As an Administrator, I want to log out of my account, so that I can end my session as administrator.
* 003 - As an Administrator, I want to create new account for another person with roles of Administrator, Instructor, or Student, so that the other person can access their accounts with their allowed actions based on their role.
* 004 - As an Administrator, I want to delete an existing account, so that I can remove that person from the Academic Record Book.
* 006 - As an Administrator, I want to view a list of all existing account, so that I can see all existing user accounts.


Sprint 3 Goal:

Planned:
* 003 - As an Administrator, I want to create new account for another person with roles of Administrator, Instructor, or Student, so that the other person can access their accounts with their allowed actions based on their role.

Pulled in:
* 004 - As an Administrator, I want to delete an existing account, so that I can remove that person from the Academic Record Book.
* 006 - As an Administrator, I want to view a list of all existing account, so that I can see all existing user accounts.
