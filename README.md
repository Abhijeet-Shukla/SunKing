# 🚀 E-Commerce Order Management Microservice

## 📌 Overview
This is a **Spring Boot-based microservice** for managing **Orders and Inventory** in an **E-Commerce Platform**. The system is designed for **high scalability**, **fault tolerance**, and **real-world reliability** by leveraging **RabbitMQ** for asynchronous processing and a **retry mechanism** for failed orders.

---

## 📖 Features
### 1️⃣ Order Management
✅ **Place an Order**  
- Validates product availability before confirming an order.  
- Reduces inventory stock on successful order placement.  
- Handles negative quantity and out-of-stock scenarios.

✅ **Update Order Status**  
- Allows updating order status (e.g., **Pending → Shipped**).  

✅ **Get Order Details**  
- Fetches order details based on `orderId`.  

---

### 2️⃣ Inventory Management
✅ **Check Stock**  
- Fetches available stock for a product.  

✅ **Reduce Stock**  
- Updates inventory when an order is placed.  
- Prevents overselling in concurrent requests.  

✅ **Prevents Negative Stock Orders**  
- Rejects orders where **quantity <= 0**.  
- Rejects orders when **stock is insufficient**.  

---

### 3️⃣ Scalability & Fault Tolerance
✅ **RabbitMQ for Async Order Processing**  
- Orders are added to a queue for processing, ensuring system stability.  

✅ **Retry Mechanism for Failed Orders**  
- Failed order processing attempts are **retried** with exponential backoff.  

✅ **Rate Limiting (Future Improvement)**  
- Can be added to prevent overwhelming the system during peak traffic.  

---

## 🔧 Tech Stack
| Technology  | Description  |
|-------------|-------------|
| **Java 17** | Programming language |
| **Spring Boot** | Backend framework |
| **Spring Data JPA** | ORM for PostgreSQL |
| **PostgreSQL** | Relational database |
| **Docker** | Containerized DB instance |
| **RabbitMQ** | Queue-based order processing |
| **Spring Retry** | Retry mechanism for failed orders |
| **Lombok** | Boilerplate code reduction |

---

## Postman Collection 
Located in resources\Order Management System.postman_collection.json

---

## 📌 Scaling for Production

To ensure high scalability and fault tolerance, consider the following:

## 1️⃣ Database Optimization
- Use **indexes** on frequently queried fields.
- Implement **partitioning** for large tables.
- Use **read replicas** to distribute load.

## 2️⃣ Load Balancing
- Deploy behind an **API Gateway** (e.g., Nginx, AWS API Gateway).
- Use **round-robin or weighted routing** strategies.

## 3️⃣ Auto-Scaling
- Use **Kubernetes (K8s)** for containerized microservices.
- Implement **horizontal scaling** to handle increased traffic.

## 4️⃣ Asynchronous Processing
- Utilize **RabbitMQ** for **queue-based order processing**.
- Implement **delayed retries** to handle failures gracefully.

## 5️⃣ Monitoring & Logging
- **Prometheus + Grafana** → Monitor API performance.
- **ELK Stack (Elasticsearch, Logstash, Kibana)** → Centralized logging.

---
🚀 With these strategies, the system can handle **peak loads** and **traffic spikes efficiently**!
