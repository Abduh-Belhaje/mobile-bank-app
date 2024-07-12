# Mobile Banking Application

## Introduction

This project involves the development of a mobile banking application with a microservices architecture using Flutter for the frontend and Spring Boot for the backend.


## Architecture

The application is designed with a microservices architecture to ensure scalability and maintainability. Each service operates independently and communicates via REST APIs.

![Project Architecture](/support/MSS.png)


## Features

- **Authentication Service**: Manages user registration, login, and session management.
- **Account Management Service**: Handles bank account information and management.
- **Transaction Service**: Processes transactions and provides transaction history.
- **Notifications Service**: Sends real-time alerts and notifications to users.


## Tech Stack

- **Frontend**: Flutter
- **Backend**: Spring Boot , Spring Cloud
- **Database**: PostgreSQL
- **Containerization**: Docker

## Setup and Installation

### Prerequisites

- [Install Docker](https://docs.docker.com/get-docker/)
- [Install Flutter](https://flutter.dev/docs/get-started/install)
- [Install Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)

### Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/Abduh-Belhaje/mobile-bank-app.git
   cd mobile-bank-app

2. Start the backend services using Docker:
   ```sh
   C:\*\Online banking system\microservices> docker-compose up -d


### Screens

<img src="/support/Home.png" width="300" />      <img src="/support/Withdraw.png" width="300" /> 

<img src="/support/history.png" width="300" />   <img src="/support/info.png" width="300" />
 
<img src="/support/signup.png" width="300" />    <img src="/support/login.png" width="300" /> 

<img src="/support/success.png" width="300" /> 
