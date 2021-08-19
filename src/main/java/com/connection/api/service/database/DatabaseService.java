package com.connection.api.service.database;

import com.connection.api.dto.Test;
import com.connection.api.dto.TestDTO;
import com.connection.api.exception.ExceptionCentral;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.connection.api.util.HandleUtil.handleException;

@Log4j2
public class DatabaseService {

  private final DatabaseConnection databaseConnection = new DatabaseConnection();

  public void getObject(HttpServletResponse response, int id) {
    String query = "SELECT * FROM VN_TEST WHERE ID = ?";
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      log.info("Execute query get data to database");
      connection = databaseConnection.getConnection();
      statement = connection.prepareStatement(query);
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      TestDTO testDTO = null;
      while (resultSet.next()) {
        Test test = new Test();
        test.setId(resultSet.getInt("ID"));
        test.setName(resultSet.getString("NAME"));
        test.setDescription(resultSet.getString("DESCRIPTION"));
        test.setPrice(resultSet.getFloat("PRICE"));
        testDTO = TestDTO.builder()
            .name(test.getName())
            .description(test.getDescription())
            .price(test.getPrice())
            .build();
      }
      response.getWriter().print(testDTO);
    } catch (SQLException | IOException e) {
      log.error("Get data from database has ex:", e);
      handleException(response, e);
      throw new ExceptionCentral(e);
    } finally {
      closeConnection(response, connection);
      closeStatement(response, statement);
    }
  }

  public void getListObject(HttpServletResponse response) {
    log.info("Begin get connection prepare statement get list object");
    String build = "SELECT * FROM VN_TEST";
    List<TestDTO> list = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = databaseConnection.getConnection();
      statement = connection.prepareStatement(build);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        TestDTO testDTO = new TestDTO();
        testDTO.setName(resultSet.getString("NAME"));
        testDTO.setDescription(resultSet.getString("DESCRIPTION"));
        testDTO.setPrice(resultSet.getFloat("PRICE"));
        list.add(testDTO);
      }
      response.getWriter().print(list);
      connection.commit();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    } finally {
      closeConnection(response, connection);
      closeStatement(response, statement);
    }
    log.info("End get connection prepare statement get list object");
  }

  public void insert(HttpServletResponse response, Test test) {
    log.info("Begin get connection prepare statement insert to database");
    String builder = "INSERT INTO VN_TEST (ID, NAME, DESCRIPTION, PRICE) VALUES (?, ?, ?, ?)";
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = databaseConnection.getConnection();
      preparedStatement = connection.prepareStatement(builder);
      preparedStatement.setInt(1, test.getId());
      preparedStatement.setString(2, test.getName());
      preparedStatement.setString(3, test.getDescription());
      preparedStatement.setFloat(4, test.getPrice());
      int execute = preparedStatement.executeUpdate();
      log.info("Execute update: {}", execute);
      if (execute != 0) {
        connection.commit();
      }
      TestDTO testDTO = TestDTO.builder()
          .name(test.getName())
          .description(test.getDescription())
          .price(test.getPrice())
          .build();
      response.getWriter().print(testDTO);
    } catch (SQLException | IOException e) {
      log.error("Insert data to database has ex:", e);
      handleException(response, e);
      throw new ExceptionCentral(e);
    } finally {
      closeConnection(response, connection);
      closeStatement(response, preparedStatement);
    }
    log.info("End get connection prepare statement insert to database");
  }

  private void closeStatement(HttpServletResponse response, PreparedStatement statement) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        log.error("Close prepare statement has ex:", e);
        handleException(response, e);
      }
    }
  }

  private void closeConnection(HttpServletResponse response, Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        log.error("Close connection has ex:", e);
        handleException(response, e);
      }
    }
  }


}
