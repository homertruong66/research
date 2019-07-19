package com.tech3s.jcoder.practice;

import com.tech3s.jcoder.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL      = "jdbc:mysql://localhost:3306/jcoder";
    
    // Database credentials
    private static final String USER = "root";
    private static final String PASS = "admin";
    
    public Product create(Product product) {
        Connection        connection = null;
        PreparedStatement pstmt      = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute a query
            String insertSQL
                = "INSERT INTO products (id, title, description, price, quantity) "
                + "VALUES (?, ?, ?, ?, ?) ";
            pstmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, product.getId());
            pstmt.setString(2, product.getTitle());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getQuantity());
            System.out.println("Executing INSERT query: " + insertSQL + " - " + product);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }
            
            // Clean-up environment
            pstmt.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return product;
    }
    
    public Product read(String id) {
        Product product = new Product();
        
        Connection        connection = null;
        PreparedStatement pstmt      = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute a query
            String querySQL
                = "SELECT id, title, description, price, quantity "
                + "FROM products "
                + "WHERE id = ? ";
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setString(1, id);
            System.out.println("Executing SELECT query: " + querySQL + " - " + id);
            ResultSet rs = pstmt.executeQuery();
            
            // Extract data from result set
            while (rs.next()) {
                // Retrieve data by column name
                product.setId(rs.getString("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
            }
            
            // Clean-up environment
            rs.close();
            pstmt.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return product;
    }
    
    public Product update(String id, Product product) {
        Connection        connection = null;
        PreparedStatement pstmt      = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute a query
            String updateSQL
                = "UPDATE products "
                + "SET title = ?, description = ?, price = ?, quantity = ? "
                + "WHERE id = ? ";
            pstmt = connection.prepareStatement(updateSQL);
            pstmt.setString(1, product.getTitle());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setString(5, id);
            System.out.println("Executing UPDATE query: " + updateSQL + " - " + product);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
            
            // Clean-up environment
            pstmt.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return product;
    }
    
    public boolean delete(String id) {
        Connection        connection = null;
        PreparedStatement pstmt      = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute a query
            String deleteSQL
                = "DELETE FROM products "
                + "WHERE id = ? ";
            pstmt = connection.prepareStatement(deleteSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id);
            System.out.println("Executing DELETE query: " + deleteSQL + " - id = " + id);
            int affectedRows = pstmt.executeUpdate();
            System.out.println(affectedRows + " row(s) deleted!");
            
            // Clean-up environment
            pstmt.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return true;
    }
    
    public List<Product> list() {
        List<Product> products = new ArrayList<>();
        
        Connection connection = null;
        Statement  stmt       = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute a query
            String querySQL
                = "SELECT  id, title, description, price, quantity "
                + "FROM products ";
            stmt = connection.createStatement();
            System.out.println("Executing query: " + querySQL);
            ResultSet rs = stmt.executeQuery(querySQL);
            
            // Extract data from result set
            while (rs.next()) {
                // Retrieve data by column name
                Product product = new Product();
                // Retrieve data by column name
                product.setId(rs.getString("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
            
            // Clean-up environment
            rs.close();
            stmt.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return products;
    }
}
