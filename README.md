# eCommerce System

This is a Spring Boot-based eCommerce system that provides APIs for managing products and categories. The system supports operations such as adding, updating, deleting, and searching for products and categories. Additionally, it handles file uploads for product images.

## Features

- **Category Management**
    - View all categories with pagination.
    - View details of a specific category by ID.
    - Search categories by name.
    - Add new categories.
    - Update existing categories.
    - Delete categories.

- **Product Management**
    - View all products with pagination.
    - View details of a specific product by ID.
    - Search products by name, brand, description, or category.
    - Filter products by category ID.
    - Add new products with image uploads.
    - Update existing products with image uploads.
    - Delete products.

## Technologies Used

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Web**
- **Lombok**
- **Swagger/OpenAPI**
- **MapStruct** (for object mapping)
- **Mysql**
- **Maven**

## Project Structure

- **`controller/`**: Contains REST controllers for handling API requests.
- **`service/`**: Contains service classes with business logic.
- **`dto/`**: Data Transfer Objects for transferring data between layers.
- **`entity/`**: JPA entities representing database tables.
- **`repository/`**: Spring Data repositories for database access.
- **`mapper/`**: Contains mappers for converting between entities and DTOs.

## Access the API:

   The application will start on `http://localhost:8080`. You can access the Swagger UI to explore the API documentation at `http://localhost:8080/swagger-ui.html`.

## API Endpoints

 <div style="display: flex; justify-content: space-around;">
  <img src="https://github.com/user-attachments/assets/aebb303d-f692-406e-93dc-5b066d267aec" alt="Category API" style="width: 48%;">
  <img src="https://github.com/user-attachments/assets/8e1963f2-325f-43dd-a5cd-384478624e43" alt="Product API" style="width: 48%;">
</div>

