# Connections - README

Welcome to **Connections**, a project designed to practice **Spring Boot**, **WebFlux**, and integration of related entities such as artists (Creators), events, posts, comments, and venues. This application provides a REST API to manage these resources, as described below.

---

## Table of Contents

1. [Overview](#overview)
2. [Core Entities](#core-entities)
3. [Endpoints](#endpoints)
4. [Installation and Running the Project](#installation-and-running-the-project)
5. [API Usage](#api-usage)
6. [Data Models (DTOs)](#data-models-dtos)
7. [Contribution](#contribution)
8. [License](#license)

---

## Overview

**Connections** is a system that links **artists/creators** with **venues** (event locations). Creators can make posts, comment on posts or events, and RSVP to events. Meanwhile, venue owners can create and manage their venues and events, allowing creators to join.

### Motivation

1. **Practice** building RESTful APIs with **Spring Boot** and **reactive programming** (Spring WebFlux).
2. **Explore** entity relationships among `Creator`, `Event`, `Venue`, `Post`, `Comment`, and `Rsvp`.
3. **Demonstrate** how to document endpoints using **OpenAPI/Swagger**.

---

## Core Entities

- **Creator**: Represents an artist, with name, pronouns, and bio.
- **Venue**: A physical location for events, containing name, location, capacity, etc.
- **Event**: Created in a `Venue`, with date/time, title, and description.
- **Rsvp**: Confirms a `Creator`’s attendance to an `Event`.
- **Post**: Created by a `Creator` and can have multiple comments.
- **Comment**: A comment on a `Post` or an `Event`.
- **Link**: (Optional) Represents a link or reference associated with a creator (e.g., social media).

---

## Endpoints

All endpoints follow a consistent pattern:
- `POST /{resource}` to create
- `GET /{resource}/all` to list
- `GET /{resource}/id/{id}` to retrieve by ID
- `DELETE /{resource}/id/{id}` to delete by ID

Additionally, certain resources have specialized endpoints, like `/post/id/{id}/comments` to retrieve a post with its comments.

Examples:

| Entity      | Main Endpoints                                                                                                                       |
|-------------|---------------------------------------------------------------------------------------------------------------------------------------|
| **Venue**   | `POST /venue` <br> `GET /venue/all` <br> `GET /venue/id/{id}` <br> `DELETE /venue/id/{id}`                                           |
| **Rsvp**    | `POST /rsvp` <br> `GET /rsvp/all` <br> `GET /rsvp/id/{id}` <br> `DELETE /rsvp/id/{id}`                                               |
| **Post**    | `POST /post` <br> `GET /post/all` <br> `GET /post/id/{id}` <br> `DELETE /post/id/{id}` <br> `GET /post/id/{id}/comments`             |
| **Link**    | `POST /link` <br> `GET /link/all` <br> `GET /link/id/{id}` <br> `DELETE /link/id/{id}`                                               |
| **Event**   | `POST /event` <br> `GET /event/all` <br> `GET /event/id/{id}` <br> `DELETE /event/id/{id}`                                           |
| **Creator** | `POST /creator` <br> `GET /creator/all` <br> `GET /creator/id/{id}` <br> `DELETE /creator/id/{id}`                                   |
| **Comment** | `POST /comment` <br> `GET /comment/all` <br> `GET /comment/id/{id}` <br> `DELETE /comment/id/{id}`                                   |

For detailed information (parameters, request examples, etc.), refer to the [OpenAPI documentation](#api-usage).

---

## Installation and Running the Project

1. **Requirements**
    - Java 17+
    - (Optional) Gradle 7+ installed globally (otherwise, use the provided `gradlew` script).

2. **Clone the repository**
   ```bash
   git clone https://github.com/diegon23/connections.git
   cd connections
   ```

3. **Build the project**
   ```bash
   ./gradlew clean build
   ```

4. **Run**
   ```bash
   ./gradlew bootRun
   ```
   By default, the application starts on `http://localhost:8084`.

---

## API Usage

Once the application is running:

- **Swagger UI**:
  ```
  http://localhost:8084/swagger-ui.html
  ```
  or
  ```
  http://localhost:8084/swagger-ui/index.html
  ```
  to explore and test the endpoints via a visual interface.

- **OpenAPI JSON**:
  ```
  http://localhost:8084/v3/api-docs
  ```
  to view the OpenAPI spec in JSON format.

> **Note**: If you are using a different port, check your `application.properties` or logs.

---

## Data Models (DTOs)

Each endpoint sends/receives a **DTO** (Data Transfer Object).  
Some examples:

- **VenueDTO**:
  ```json
  {
    "id": 0,
    "name": "string",
    "location": "string",
    "capacity": 100
  }
  ```
- **CreatorDTO**:
  ```json
  {
    "id": 0,
    "name": "string",
    "pronouns": "string",
    "bio": "string"
  }
  ```
- **EventDTO**:
  ```json
  {
    "id": 0,
    "title": "string",
    "description": "string",
    "dateTime": "2025-01-17T12:00:00Z",
    "venueId": 1
  }
  ```
- **PostDTO**:
  ```json
  {
    "id": 0,
    "content": "string",
    "createdAt": "2025-01-17T12:00:00Z",
    "createdBy": "string",
    "comments": [ /* CommentDTO objects */ ]
  }
  ```
- **CommentDTO**:
  ```json
  {
    "id": 0,
    "content": "string",
    "createdAt": "2025-01-17T12:00:00Z",
    "createdBy": "string",
    "eventId": 2,
    "postId": 5
  }
  ```
- **RsvpDTO**:
  ```json
  {
    "id": 0,
    "eventId": 3,
    "creatorId": 4,
    "status": "string",
    "lastUpdated": "2025-01-17T12:00:00Z"
  }
  ```
- **LinkDTO**:
  ```json
  {
    "id": 0,
    "creatorId": 1,
    "description": "Social media or reference link"
  }
  ```

For the full list of properties and details, see the [OpenAPI documentation](#api-usage).

---

## Contribution

1. **Fork** this repository and create a new branch:
   ```bash
   git checkout -b feature/my-new-feature
   ```
2. **Make your changes** and commit them with clear messages.
3. **Open a Pull Request** so we can review and merge your work.

Feedback, issues, and new ideas are welcome!

---

**Thank you for checking out Connections!**  
Feel free to test, send feedback, and enhance this project as you see fit. Enjoy!