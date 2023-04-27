# simple shop cart 後台API

提供使用者登入,管理商品,訂單管理等功能API

## 系統特色(Feature)

- CRUD API 的 HTTP 方法包括 POST、GET、PATCH 和 DELETE。
- 使用 Spring Boot 框架來建立後端 REST API 專案。
- 使用 Spring Security + JWT 實作系統認證與授權
- 使用 `@PreAuthorize` 管控使用者權限
- 使用 Spring Data JPA 來映射實體類別。
- 使用 H2 Database 存放資料
- 使用 `@restcontrolleradvice` 來處理所有拋出的 `Exception`，避免內部錯誤堆疊跟蹤訊息外洩。
- 使用 `@restcontrolleradvice` 返回 JSON 訊息和資料。
- 使用 Lombok 減少樣板程式碼，使開發流程更輕鬆、更快速、更舒適。
- 使用悲觀鎖定`(Pessimistic Locking)`來防止資料不一致。
- 使用 Swagger UI 方便使用者測試API

## 使用以下技術(Tools/Framework/Library)

- Java 18
- Spring Boot 2
- Spring Security
- Spring Data JPA
- JSON Web Tokens
- Jakarta Bean Validation
- H2 Database
- Swagger UI
- Lombok
- Git
- Maven
- IntelliJ IDEA
- Postman

## Swagger UI

http://localhost:8080/api/swagger-ui.html

## H2 Database UI

http://localhost:8080/api/h2-ui

## 測試 帳號/密碼/權限:

- admin / 1234 / ADMIN
- user1 / 1234 / USER
- user2 / 1234 / USER
