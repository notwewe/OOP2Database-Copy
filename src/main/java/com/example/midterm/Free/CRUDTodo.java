package com.example.midterm.Free;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDTodo {

    public static int getUserIdByUsername(String username) {
        int userId = -1;
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    userId = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public boolean insertTask(int userId, String title, String description, String date, String time) {
        boolean inserted = false;
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO todolist (userid, title, description, date, time) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS // Retrieve generated keys
             )) {
            statement.setInt(1, userId);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, date);
            statement.setString(5, time);
            int num = statement.executeUpdate();
            if (num != 0) {
                // Task inserted successfully
                inserted = true;
                System.out.println("Task inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Exception in insertTask");
            e.printStackTrace();
        }
        return inserted;
    }


    public List<Task> readTasks(int userId) {
        List<Task> tasks = new ArrayList<>();
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM todolist WHERE userid = ?"
             )) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");

                Task task = new Task(id, title, description, date, time);

                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println("Exception in readTasks");
            e.printStackTrace();
        }
        return tasks;
    }


    public boolean updateTask(int taskId, String title, String description, String date, String time) {
        boolean updated = false;
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE todolist SET title = ?, description = ?, date = ?, time = ? WHERE id = ?"
             )) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, date);
            statement.setString(4, time);
            statement.setInt(5, taskId);

            int num = statement.executeUpdate();
            if (num != 0) {
                updated = true;
                System.out.println("Task updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Exception in updateTask");
            e.printStackTrace();
        }
        return updated;
    }

    public static boolean deleteTask(int taskId) {
        boolean deleted = false;
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM todolist WHERE id = ?"
             )) {
            statement.setInt(1, taskId);
            int num = statement.executeUpdate();
            if (num != 0) {
                deleted = true;
                System.out.println("Task deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Exception in deleteTask");
            e.printStackTrace();
        }
        return deleted;
    }

}
