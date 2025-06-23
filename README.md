# 🏘️ Neighbrly — Community Hotel Booking Platform

Welcome to **Neighbrly**, a full-stack hotel booking system designed to manage everything from hotel listings to room inventory and real-time guest bookings. Built with Java & Spring Boot, it’s a production-ready backend for any travel or hospitality app. ☀️🏨

---

## 🚀 Features at a Glance

- 🧑‍💼 **Authentication & Authorization**
  - Secure login/signup with JWT
  - Role-based access for Admins and Users

- 🏨 **Hotel & Room Management (Admin Panel)**
  - Add, edit, delete hotels and rooms
  - Set room capacity, base price, and upload photos
  - Toggle hotel activation status

- 📦 **Inventory Control**
  - Track reserved, booked, and available room counts per date
  - Dynamic pricing with `surgeFactor`
  - Mark rooms as closed/unavailable on specific dates

- 📅 **Booking Flow**
  - Initiate bookings with check-in/check-out dates
  - Add guest details per booking
  - Stripe-style payment webhook handling
  - Cancel and view bookings

- 🔍 **Search & Browse**
  - Filter hotels by city and date
  - View hotel details with available rooms and prices

- 📊 **Admin Reports**
  - Revenue and booking stats for each hotel
  - Filter reports by date

- 👤 **User Profile**
  - View/update profile
  - Maintain personal guest list
  - View past bookings

---

## 🧠 What Makes It Special?

- 💡 **Real-life inspired flow**: Guests must be added before confirming a booking
- ⚙️ **Payment webhook listener**: Simulates Stripe-style payment success handling
- 🔐 **Secure by design**: JWT tokens, refresh flow, and role restrictions
- 📈 **Inventory-aware**: Prevents overbooking with live stock updates

---

## 🛠️ Tech Stack

| Layer        | Tech                       |
|--------------|----------------------------|
| Backend      | Spring Boot (Java), JPA    |
| DB           | MySQL / MongoDB (optional) |
| Security     | Spring Security, JWT       |
| Dev Tools    | Swagger (OpenAPI), Postman |
| Payment      | Stripe-style webhook       |
| Storage      | Cloudinary (for photos)    |

---
## 📊 Below is the database schema diagram illustrating the key entities. 🔗

![Screenshot 2025-06-23 165758](https://github.com/user-attachments/assets/2113082b-3f47-4e37-ba0d-469a277dde8a)






## 📬 API Overview

### 🔐 **Authentication** — `/auth`

| Method | Endpoint        | Description                  |
|--------|-----------------|------------------------------|
| POST   | `/auth/signup`  | Create a new user account    |
| POST   | `/auth/login`   | Login with email & password  |
| POST   | `/auth/refresh` | Get new JWT using refresh token |

---

### 👤 **User Profile** — `/users`

| Method | Endpoint          | Description                  |
|--------|-------------------|------------------------------|
| GET    | `/users/profile`  | Get logged-in user's profile |
| PATCH  | `/users/profile`  | Update user profile          |

---

### 👥 **Guests** — `/users/guests`

| Method | Endpoint                   | Description                      |
|--------|----------------------------|----------------------------------|
| GET    | `/users/guests`            | Get all saved guests             |
| POST   | `/users/guests`            | Add a new guest                  |
| PUT    | `/users/guests/{guestId}`  | Update an existing guest         |
| DELETE | `/users/guests/{guestId}`  | Remove a guest                   |

---

### 📅 **Booking Flow** — `/booking`

| Method | Endpoint                          | Description                          |
|--------|-----------------------------------|--------------------------------------|
| POST   | `/booking/init`                   | Start a new booking                  |
| POST   | `/booking/{bookingId}/addGuests`  | Add guests to booking                |
| POST   | `/booking/{bookingId}/payment`    | Initiate payment flow                |
| POST   | `/booking/{bookingId}/cancel`     | Cancel a booking                     |
| GET    | `/users/myBookings`              | View all user bookings               |

---

### 🏨 **Hotel Management (Admin)** — `/admin/hotels`

| Method | Endpoint                          | Description                      |
|--------|-----------------------------------|----------------------------------|
| GET    | `/admin/hotels`                   | Get all hotels owned by admin    |
| POST   | `/admin/hotels`                   | Create a new hotel               |
| GET    | `/admin/hotels/{hotelId}`         | Get hotel by ID                  |
| PUT    | `/admin/hotels/{hotelId}`         | Update hotel                     |
| PATCH  | `/admin/hotels/{hotelId}`         | Activate hotel                   |
| DELETE | `/admin/hotels/{hotelId}`         | Delete hotel                     |
| GET    | `/admin/hotels/{hotelId}/reports` | View hotel booking report        |
| GET    | `/admin/hotels/{hotelId}/bookings`| View all bookings of a hotel     |

---

### 🛏️ **Room Management (Admin)** — `/admin/hotels/{hotelId}/rooms`

| Method | Endpoint                                   | Description            |
|--------|--------------------------------------------|------------------------|
| GET    | `/admin/hotels/{hotelId}/rooms`            | Get all rooms in hotel |
| POST   | `/admin/hotels/{hotelId}/rooms`            | Create new room        |
| GET    | `/admin/hotels/{hotelId}/rooms/{roomId}`   | Get room by ID         |
| PUT    | `/admin/hotels/{hotelId}/rooms/{roomId}`   | Update room info       |
| DELETE | `/admin/hotels/{hotelId}/rooms/{roomId}`   | Delete room            |

---

### 📦 **Inventory Management (Admin)** — `/admin/inventory`

| Method | Endpoint                              | Description                    |
|--------|---------------------------------------|--------------------------------|
| GET    | `/admin/inventory/rooms/{roomId}`     | View inventory of a room       |
| PATCH  | `/admin/inventory/rooms/{roomId}`     | Update inventory (dates, surge etc.) |

---

### 🔎 **Hotel Search & Browse**

| Method | Endpoint                 | Description                    |
|--------|--------------------------|--------------------------------|
| GET    | `/hotels/search`         | Search hotels by city & date   |
| GET    | `/hotels/{hotelId}/info` | Get hotel details with rooms   |

---

### 💳 **Payment Webhook**

| Method | Endpoint             | Description                    |
|--------|----------------------|--------------------------------|
| POST   | `/webhook/payment`   | Capture Stripe-style payment   |

---

### 📘 Swagger UI

Explore all APIs interactively here:  
**`http://localhost:8080/api/v1/swagger-ui/index.html`**

---

## Prerequisites

- Java  
- PostgreSQL Database
---


## 🧰 Getting Started

```bash
git clone https://github.com/your-username/neighbrly.git
cd neighbrly

# Application Name
spring.application.name=Neighbrly

# DB Configuration
spring.datasource.url=<DB URL>  # e.g., jdbc:postgresql://localhost:5432/yourDatabase
spring.datasource.username=<DB Username>
spring.datasource.password=<DB Password>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.servlet.context-path=/api/v1

# JWT Authentication Configuration
jwt.secretKey=<JWT Secret Key>


# Stripe Configuration
stripe.secret.key=${STRIPE_SECRET_KEY}
stripe.webhook.secret=${STRIPE_WEBHOOK_SECRET}

## Finally run
mvn clean install
mvn spring-boot:run

```
## The application will be available at http://localhost:8080/.


**Connect with me on [LinkedIn](www.linkedin.com/in/shrivatsakc/) for any queries or collaboration!**





