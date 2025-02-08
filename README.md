# Internet Room Management System

## Table of Contents
1. [Introduction](#sparkles-introduction)
2. [Demo](#tv-demo)
3. [Key Features](#gear-key-features)
4. [System Architecture](#wrench-system-architecture)
5. [Technologies Used](#rocket-technologies-used) 
6. [Installation Guide](#electric_plug-installation-guide) 
7. [Usage Guide](#bookmark_tabs-usage-guide)
8. [Future Development](#dart-future-development)
9. [Contact](#email-contact)
10. [Thank You](#heart-thank-you)
## :sparkles: Introduction
This project aims to provide an effective solution for managing internet rooms, ensuring efficient monitoring and control of computers and user accounts. The application is designed following the MVC (Model-View-Controller) architecture, enabling clear separation of concerns for easy maintenance and future development.
## :tv: Demo

- The application provides both server and client interfaces:

  * Server Interface: Manage machine status, messaging, and billing functions.

  * Client Interface: Use the machine and communicate via messages.
![Demo](./Demo/demo_gif.gif)
- Watch the full demo video here: https://youtu.be/xFiZ1M9Q2JM
## :gear: Key Features

### :computer: Management Functions
- Net Room Management: Powering on computers, logging out, monitoring, closing applications, billing, shutting down, restarting, and - tracking computer status.
- User Management: Creating accounts, topping up balances, updating user information, locking/unlocking/deleting accounts, and searching for accounts.
- Usage Time Management: Setting usage costs and time warnings.
- Revenue Management: Tracking and calculating revenue from customers.
### :speech_balloon: Communication Functions
-  Messaging: Connects the server and client machines.
-  Monitoring and Control: Observes the status and activities of client machines and allows remote control when necessary.
-  Application Closure: The server can remotely close any application running on client machines.
### :wrench: System Architecture

- Model: Manages data related to computers, user accounts, and transactions.

- View: Displays information and provides user interfaces.

- Controller: Handles business logic and updates the user interface.

### :rocket: Technologies Used

- Programming Language: Java

- User Interface: Java Swing

- Database: MySQL

- Development Model: MVC (Model-View-Controller)

### :electric_plug: Installation Guide

- Clone the repository: git clone <repository-url>

- Import the project into your preferred IDE (Apache Netbeans, IntelliJ IDEA, Eclipse, etc.).

- **Database Setup:**  
  * Use the [`csmdatabase.sql`](csmdatabase.sql) file to set up the MySQL database.  
  * Execute this file in your preferred MySQL management tool, such as phpMyAdmin or MySQL Workbench.  

- Run the project.

### :bookmark_tabs: Usage Guide
- Admin:
  * Manage client stations.
  * Manage user accounts.
  * Track revenue.
- User:
  * Log in and use the computer.
  * Change account password.
  * Send messages.
## :dart: Future Development
- Performance optimization for larger environments.
- Enhanced user interface.
- Integration of online payment features.
- Detailed revenue reporting.
## :email: Contact
- Email: tttiuem2k3@gmail.com
- Linkedin: https://www.linkedin.com/in/thinh-tran-04122k3/
## :heart: Thank You
We sincerely appreciate your time and interest in our project. Your feedback and contributions are always welcome to help us improve and grow.
