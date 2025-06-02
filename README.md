# 🍽️ Restaurant Order Management System (Java + MongoDB)

A desktop Java Swing application for restaurant managers to manage client orders and customer information. The app connects to a **MongoDB Atlas** cloud database for persistent storage.

---
## 🚀 Features

- 🔐 Role-based login system (Gerant, Client)
- 🛒 Manage client orders: create, track, and update
- 🧾 Gerant dashboard: view, filter, update, or delete any command
- ☁️ MongoDB Atlas integration

---

## 📁 Project Structure

```
project-root/
│
├── interfaces/
│   ├── Gerant/
│   └── components/
|   └── client/
│
├── models/
│   └── Commande.java, User.java , Produit.java
│
├── services/
│   ├── CommandeService.java
│   ├── ClientService.java
│   └── GerantService.java
│
│── ConfigLoader.java          # 🔹 MongoDB Atlas connection
│
└── Main.java                         # 🔹 Entry point of the application
```

---

## 🚀 How to Run the Project

### 🧰 Requirements
- Java 8+ (cuz of the mongidb-driver)
- IDE (IntelliJ, Eclipse, etc.)
- Internet connection
- MongoDB Atlas account

### 🟢 Steps

1. **Clone or download the project.**

2. ** MongoDB Atlas Configuration**

This project connects to a MongoDB Atlas cluster using a `config.properties` file.

#### 🔧 Step 1: Create the `config.properties` file

Ensure there is a file named `config.properties` at the root of your project with the following content:

```properties
mongo.uri=mongodb+srv://<username>:<password>@<cluster-url>/?retryWrites=true&w=majority


   Replace:
   - `<username>` – your Atlas DB user
   - `<password>` – your password
   - `<cluster-url>` – your cluster URI

 This file is automatically read in ConfigLoader.java
   ```

4. ✅ Done! The Swing GUI will open.

---

## 🌐 MongoDB Atlas Configuration

The app uses:
- `commandes` collection for order data
- `clients` collection for user information
- `produits` collection for items in menu

Make sure:
- Your Atlas cluster is **running**
- Your **IP is whitelisted**
- The **database user has read/write access**

---

## 🧩 Features

- View and filter orders by status:
  - `NON_TRAITEE`, `EN_PREPARATION`, `PRETE`, `ANNULE`
- Color-coded status labels
- Change status with a context menu
- Real-time statistics (number of orders by status)

---

## 📝 Notes

- Add new features easily via `services/` and `interfaces/`

---

## 🔗 Related Technologies

- Java Swing
- MongoDB Atlas
- MongoDB Java Driver
- Object-Oriented Programming

---

