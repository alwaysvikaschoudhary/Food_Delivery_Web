# 🍽️ FoodFrenzy

FoodFrenzy is a comprehensive, full-stack food delivery and restaurant management web application. It is designed to provide a seamless ordering experience for customers while equipping administrators with a powerful dashboard to manage products, users, and orders. Built with modern Java technologies, the application boasts a beautiful UI, robust authentication, and dynamic cart functionalities.

---

## 🌟 Features

- **Dynamic Shopping Cart**: Instantly add items, update quantities with AJAX-powered `+` and `-` controls, and see price calculations in real-time without page reloads.
- **Role-Based Access Control**: Secure login system with distinct privileges for `USER` and `ADMIN` roles.
- **Admin Dashboard**: Complete CRUD (Create, Read, Update, Delete) capabilities for managing the menu, tracking user accounts, and monitoring all orders.
- **Cloudinary Image Integration**: Product images are securely uploaded and hosted on Cloudinary, ensuring fast and reliable media delivery.
- **Order Management & History**: Customers can place orders effortlessly and track their order history via their profile.
- **Responsive Modern UI**: Designed with an elegant, responsive interface using custom CSS, glassmorphism effects, and smooth animations.
- **Database Integration**: Powered by PostgreSQL for robust, scalable data persistence.

---

## 🛠️ Tech Stack

### Backend
- **Java 21**
- **Spring Boot 3.1** (Spring Web, Spring Data JPA)
- **Spring Security** (Authentication, Authorization, Password Encryption)
- **Hibernate / JPA** (ORM)
- **Cloudinary Java SDK** (Image Storage)

### Frontend
- **Thymeleaf** (Server-side rendering & templating)
- **HTML5 & Vanilla CSS3** (Custom Design System)
- **JavaScript (ES6+)** (AJAX Requests, DOM Manipulation)
- **FontAwesome** (Icons)

### Database & Environment
- **PostgreSQL**
- **Maven** (Dependency Management)
- **Spring Dotenv** (Environment Variable Management)

---

## 📸 Screenshots

### 1. Home Page
A welcoming landing page featuring our hero section, dynamic design, and quick access navigation links.
![Home Page](Screenshots/screenshot_1.png)

### 2. User Registration
The sign-up page where new customers can create an account securely before ordering.
![User Registration](Screenshots/screenshot_2.png)

### 3. Login Portal
A secure login screen for both customers and administrators to access their respective accounts.
![Login Portal](Screenshots/screenshot_3.png)

### 4. Delicious Menu
The primary browsing interface where users can view available food items, descriptions, and prices.
![Delicious Menu](Screenshots/screenshot_4.png)

### 5. Quick Add to Cart
The AJAX-powered "Add to Cart" functionality providing instant visual feedback without reloading the page.
![Quick Add to Cart](Screenshots/screenshot_5.png)

### 6. Shopping Cart Interface
A seamless cart layout where users can review their selected items, images, and prices.
![Shopping Cart Interface](Screenshots/screenshot_6.png)

### 7. Dynamic Quantity Controls
Users can easily update quantities using the dynamic `+` and `-` buttons, instantly recalculating the subtotal.
![Dynamic Quantity Controls](Screenshots/screenshot_7.png)

### 8. Order Placement & Checkout
The final order summary displaying item totals, delivery fees, and the main checkout action.
![Order Placement](Screenshots/screenshot_8.png)

### 9. Customer Order History
A dedicated profile section for customers to view a detailed history of their past orders and current delivery status.
![Customer Order History](Screenshots/screenshot_9.png)

### 10. Admin Dashboard - Overview
The main entry point for the Admin panel, giving quick access to all management tools and metrics.
![Admin Dashboard Overview](Screenshots/screenshot_10.png)

### 11. Admin Panel - User Management
An interface for administrators to view, update, or remove registered users on the platform.
![Admin Panel - User Management](Screenshots/screenshot_11.png)

### 12. Admin Panel - Inventory & Products
A powerful table view for administrators to track inventory, view product images, and manage prices.
![Admin Panel - Inventory & Products](Screenshots/screenshot_12.png)

### 13. Admin Panel - Product Uploads
The form where admins can add new menu items and upload product images directly to Cloudinary.
![Admin Panel - Product Uploads](Screenshots/screenshot_13.png)

---

## 🚀 Installation Instructions

Follow these step-by-step instructions to get a local copy up and running.

### Prerequisites
- **Java 21** installed on your machine.
- **PostgreSQL** server running locally or remotely.
- **Maven** installed.
- A **Cloudinary** account (free tier works perfectly).

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/Food-Delivery-App-main.git
cd Food-Delivery-App-main
```

### 2. Configure Environment Variables
Create a `.env` file in the root directory of the project and add the following properties. Replace the placeholder values with your actual credentials:

```env
# Database Credentials
DB_NAME=food
DB_URL=jdbc:postgresql://localhost:5432/food
DB_USERNAME=postgres
DB_PASSWORD=your_db_password
DB_DRIVER=org.postgresql.Driver

# Admin Configuration
ADMIN_EMAIL=admin@foodfrenzy.com
ADMIN_PASSWORD=admin123

# Cloudinary Configuration
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# Session
SESSION_TIMEOUT=30m
```

### 3. Setup the Database
Create a new PostgreSQL database named `food` (or whatever you specified in `DB_NAME`). Spring Boot will automatically generate the required tables upon startup using `ddl-auto=update`.

### 4. Build and Run the Application
You can run the application using Maven wrapper:
```bash
./mvnw clean install
./mvnw spring-boot:run
```
Alternatively, you can build the `.jar` file and run it:
```bash
./mvnw clean package
java -jar target/FoodFrenzy-0.0.1-SNAPSHOT.jar
```

---

## 📖 Usage Guide

1. **Access the App**: Open your web browser and navigate to `http://localhost:8080`.
2. **Customer Flow**:
   - Register a new account or log in.
   - Browse the menu and add delicious food to your cart.
   - Adjust quantities in the cart seamlessly.
   - Click "Place Order" to finalize your purchase.
   - View your purchases under the Profile/Order History section.
3. **Admin Flow**:
   - Log in using the admin credentials defined in your `.env` file.
   - Access the Admin Dashboard to add new products, update prices, or upload new images.
   - View a comprehensive list of all customer orders across the platform.

---

## 📂 Folder Structure

```text
Food-Delivery-App-main/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── config/          # Cloudinary & Security configurations
│   │   │   ├── controllers/     # Web & REST Controllers
│   │   │   ├── entities/        # JPA Entities (Models)
│   │   │   ├── repositories/    # Spring Data JPA Interfaces
│   │   │   └── services/        # Business Logic & Cloudinary Uploads
│   │   └── resources/
│   │       ├── static/          # CSS styles, JS files, and local Images
│   │       ├── templates/       # Thymeleaf HTML views
│   │       └── application.properties # Spring Boot configuration
├── screenshots/                 # Application preview images
├── .env                         # Environment variables (Ignored in Git)
├── pom.xml                      # Maven dependencies
└── README.md                    # Project documentation
```

---

## 🔮 Future Improvements

- **Payment Gateway Integration**: Implement Stripe or Razorpay for seamless online transactions.
- **Live Order Tracking**: Add real-time order status tracking (Preparing, Out for Delivery, Delivered).
- **Email Notifications**: Send automated order confirmation emails using Spring Boot Mail.
- **RESTful API Expansion**: Fully decouple the frontend by converting the app into a standalone REST API with a React/Next.js frontend.

---

## 🤝 Contribution Guidelines

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📜 License

Distributed under the MIT License. See `LICENSE` for more information.
