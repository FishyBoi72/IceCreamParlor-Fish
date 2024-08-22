package org.example.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseInitializer {

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    public static void initialize() {
        // Step 1: Delete the existing database file
        deleteExistingDatabase();

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            // Step 2: Create necessary tables in the right order based on their dependencies
            createCategoriesTable(stmt);  // Create Categories first as it is referenced by MenuItems
            createRolesTable(stmt);       // Create Roles before Users
            createUsersTable(stmt);
            createMenuItemsTable(stmt);
            createOrdersTable(stmt);
            createTablesTable(stmt);
            createInventoryTable(stmt);
            createSalesTable(stmt);

            LOGGER.info("Database has been initialized.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during database initialization", e);
        }
    }

    private static void deleteExistingDatabase() {
        File dbFile = new File("restaurant.db");
        if (dbFile.exists()) {
            if (dbFile.delete()) {
                LOGGER.info("Existing database deleted successfully.");
            } else {
                LOGGER.warning("Failed to delete existing database.");
            }
        } else {
            LOGGER.info("No existing database found to delete.");
        }
    }

    private static void createCategoriesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoryName TEXT NOT NULL UNIQUE)";  // Ensure column is named 'categoryName'
        stmt.execute(sql);
        LOGGER.info("Categories table created successfully.");
    }

    private static void createRolesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Roles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "roleName TEXT NOT NULL UNIQUE)";
        stmt.execute(sql);
        LOGGER.info("Roles table created successfully.");
    }

    private static void createUsersTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "passwordHash TEXT NOT NULL," +
                "roleId INTEGER NOT NULL," +  // Make sure roleId is added and NOT NULL
                "FOREIGN KEY (roleId) REFERENCES Roles(id))";
        stmt.execute(sql);
        LOGGER.info("Users table created successfully.");
    }

    private static void createMenuItemsTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS MenuItems (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT," +
                "preparationTime INTEGER CHECK(preparationTime > 0)," +
                "price REAL NOT NULL CHECK(price >= 0)," +
                "ingredients TEXT," +
                "categoryId INTEGER NOT NULL," +
                "FOREIGN KEY (categoryId) REFERENCES Categories(id))";  // Reference Categories table
        stmt.execute(sql);
        LOGGER.info("MenuItems table created successfully.");
    }

    private static void createOrdersTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER NOT NULL," +
                "items TEXT NOT NULL," +
                "totalPrice REAL NOT NULL," +
                "status TEXT NOT NULL," +
                "FOREIGN KEY (userId) REFERENCES Users(id))";
        stmt.execute(sql);
        LOGGER.info("Orders table created successfully.");
    }

    private static void createTablesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Tables (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "size INTEGER NOT NULL," +
                "status TEXT NOT NULL)";
        stmt.execute(sql);
        LOGGER.info("Tables table created successfully.");
    }

    private static void createInventoryTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Inventory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ingredientName TEXT NOT NULL," +
                "quantity INTEGER NOT NULL)";
        stmt.execute(sql);
        LOGGER.info("Inventory table created successfully.");
    }

    private static void createSalesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Sales (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "orderId INTEGER NOT NULL," +
                "revenue REAL NOT NULL," +
                "date TEXT NOT NULL," +
                "FOREIGN KEY (orderId) REFERENCES Orders(id))";
        stmt.execute(sql);
        LOGGER.info("Sales table created successfully.");
    }
}