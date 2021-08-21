package com.connection.api.service.database;

import com.connection.api.constants.ExecuteQuery;
import com.connection.api.dto.Data;
import com.connection.api.dto.Test;
import com.connection.api.exception.ExceptionCentral;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import static com.connection.api.util.HandleUtil.handleException;

@Log4j2
public class DatabaseService {

  private final DatabaseConnection databaseConnection = new DatabaseConnection();

  public void getListObject(HttpServletResponse response) {
    log.info("Begin get connection prepare statement get list object");
    String build = "SELECT * FROM VN_DATA ";
    Connection connection = null;
    PreparedStatement statement = null;
    Data data = null;
    try {
      connection = databaseConnection.getConnection();
      statement = connection.prepareStatement(build);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        data = new Data();
        data.setUserName(resultSet.getString("USERNAME"));
        data.setCustomerName(resultSet.getString("CUSTOMERNAME"));
        data.setTranxId(resultSet.getString("TRANXID"));
        data.setMobileNo(resultSet.getString("MOBILENO"));
        data.setAccountNo(resultSet.getString("ACCOUNTNO"));
        data.setCusName(resultSet.getString("CUSNAME"));
        data.setAmount(Integer.parseInt(resultSet.getString("AMOUNT")));
        data.setStatus(resultSet.getString("STATUS"));
        data.setRescode(resultSet.getString("RESCODE"));
        data.setBankCode(resultSet.getString("BANKCODE"));
        data.setTranxNote(resultSet.getString("TRANXNOTE"));
        data.setTranxDate(resultSet.getString("TRANXDATE"));
        data.setTipAndFee(resultSet.getString("TIPANDFEE"));
        data.setType(resultSet.getString("TYPE"));
        data.setQrInfo(resultSet.getString("QRINFO"));
        data.setOrderCode(resultSet.getString("ORDERCODE"));
        data.setPayType(resultSet.getString("PAYTYPE"));
        data.setQuantity(resultSet.getString("QUANTITY"));
        data.setAddtionalData(resultSet.getString("ADDTIONALDATA"));
        data.setMerchantName(resultSet.getString("MERCHANTNAME"));
        data.setCheckSum(resultSet.getString("CHECKSUM"));
        data.setQrVersion(resultSet.getString("QRVERSION"));
        data.setMobile(resultSet.getString("MOBILE"));
        data.setRespCode(resultSet.getString("RESPCODE"));
        data.setRespDesc(resultSet.getString("RESPDESC"));
        data.setTraceTransfer(resultSet.getString("TRACETRANSFER"));
        data.setMessageType(resultSet.getString("MESSAGETYPE"));
        data.setDebitAmount(resultSet.getString("DEBITAMOUNT"));
        data.setPayDate(resultSet.getString("PAYDATE"));
        data.setRealAmount(resultSet.getString("REALAMOUNT"));
        data.setPromotionCode(resultSet.getString("PROMOTIONCODE"));
        data.setUrl(resultSet.getString("URL"));
        data.setMobileId(resultSet.getString("MOBILEID"));
        data.setClientId(resultSet.getString("CLIENTID"));
        data.setDevice(resultSet.getString("DEVICE"));
        data.setIpAddress(resultSet.getString("IPADDRESS"));
        data.setImei(resultSet.getString("IMEI"));
        data.setTotalAmount(resultSet.getString("TOTALAMOUNT"));
        data.setFeeAmount(resultSet.getString("FEEAMOUNT"));
        data.setPcTime(resultSet.getString("PCTIME"));
        data.setTellerId(resultSet.getString("TELLERID"));
        data.setTellerBranch(resultSet.getString("TELLERBRANCH"));
        data.setHostDate(resultSet.getString("HOSTDATE"));
        data.setTypeSource(resultSet.getString("TYPESOURCE"));
        data.setBankCard(resultSet.getString("BANKCARD"));
      }
      response.getWriter().print(data);
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    } finally {
      closeConnection(response, connection);
      closeStatement(response, statement);
    }
    log.info("End get connection prepare statement get list object");
  }

  public void insertData(HttpServletResponse response, Data data) {
    log.info("Begin get connection prepare statement insert to database");
    String builder = "{ call insertData(vn_data_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
        " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    Statement statement = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = databaseConnection.getConnection();
      statement = connection.createStatement();
      statement.execute(ExecuteQuery.INSERT_DATA);

      preparedStatement = connection.prepareStatement(builder);
      preparedStatement.setString(1, data.getUserName());
      preparedStatement.setInt(2, data.getAmount());
      preparedStatement.setString(3, data.getCustomerName());
      preparedStatement.setString(4, data.getTranxId());
      preparedStatement.setString(5, data.getMobileNo());
      preparedStatement.setString(6, data.getAccountNo());
      preparedStatement.setString(7, data.getCusName());
      preparedStatement.setString(8, data.getStatus());
      preparedStatement.setString(9, data.getRescode());
      preparedStatement.setString(10, data.getBankCode());
      preparedStatement.setString(11, data.getTranxNote());
      preparedStatement.setString(12, data.getTranxDate());
      preparedStatement.setString(13, data.getTipAndFee());
      preparedStatement.setString(14, data.getType());
      preparedStatement.setString(15, data.getQrInfo());
      preparedStatement.setString(16, data.getOrderCode());
      preparedStatement.setString(17, data.getPayType());
      preparedStatement.setString(18, data.getQuantity());
      preparedStatement.setString(19, data.getAddtionalData());
      preparedStatement.setString(20, data.getMerchantName());
      preparedStatement.setString(21, data.getCheckSum());
      preparedStatement.setString(22, data.getQrVersion());
      preparedStatement.setString(23, data.getMobile());
      preparedStatement.setString(24, data.getRespCode());
      preparedStatement.setString(25, data.getRespDesc());
      preparedStatement.setString(26, data.getTraceTransfer());
      preparedStatement.setString(27, data.getMessageType());
      preparedStatement.setString(28, data.getDebitAmount());
      preparedStatement.setString(29, data.getPayDate());
      preparedStatement.setString(30, data.getRealAmount());
      preparedStatement.setString(31, data.getPromotionCode());
      preparedStatement.setString(32, data.getUrl());
      preparedStatement.setString(33, data.getMobileId());
      preparedStatement.setString(34, data.getClientId());
      preparedStatement.setString(35, data.getDevice());
      preparedStatement.setString(36, data.getIpAddress());
      preparedStatement.setString(37, data.getImei());
      preparedStatement.setString(38, data.getTotalAmount());
      preparedStatement.setString(39, data.getFeeAmount());
      preparedStatement.setString(40, data.getPcTime());
      preparedStatement.setString(41, data.getTellerId());
      preparedStatement.setString(42, data.getTellerBranch());
      preparedStatement.setString(43, data.getHostDate());
      preparedStatement.setString(44, data.getTypeSource());
      preparedStatement.setString(45, data.getBankCard());
      int execute = preparedStatement.executeUpdate();
      log.info("Execute update: {}", execute);
      if (execute != 0) {
        connection.commit();
      }
      response.getWriter().print(data);
    } catch (SQLException | IOException e) {
      log.error("Insert data to database has ex:", e);
      handleException(response, e);
      throw new ExceptionCentral(e);
    } finally {
      closeStatement(response, statement);
      closeConnection(response, connection);
      closeCallStatement(response, preparedStatement);
    }
    log.info("End get connection prepare statement insert to database");
  }

  public void insert(HttpServletResponse response, Test test) {
    log.info("Begin get connection prepare statement insert to database");

    String builder = "{ call insertTest(vn_test_seq.nextval, ?, ?, ?, ?) }";

    Connection connection = null;
    Statement statement = null;
    CallableStatement callableStatement = null;

    try {
      connection = databaseConnection.getConnection();

      statement = connection.createStatement();
      statement.execute(ExecuteQuery.INSERT_TEST);

      callableStatement = connection.prepareCall(builder);
      callableStatement.setString(1, test.getTestName());
      callableStatement.setString(2, test.getDescription());
      callableStatement.setString(3, test.getRollName());
      callableStatement.registerOutParameter(4, Types.VARCHAR);

      int execute = callableStatement.executeUpdate();
      log.info("Execute update: {}", execute);
      response.getWriter().print(test);
    } catch (SQLException | IOException e) {
      log.error("Insert test to database has ex:", e);
      handleException(response, e);
      throw new ExceptionCentral(e);
    } finally {
      closeConnection(response, connection);
      closeStatement(response, statement);
      closeCallStatement(response, callableStatement);
    }
    log.info("End get connection prepare statement insert to database");
  }

  private void closeCallStatement(HttpServletResponse response, PreparedStatement statement) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        log.error("Close prepare statement has ex:", e);
        handleException(response, e);
      }
    }
  }

  private void closeStatement(HttpServletResponse response, Statement statement) {
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
