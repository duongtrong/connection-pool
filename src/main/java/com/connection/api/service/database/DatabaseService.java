package com.connection.api.service.database;

import com.connection.api.dto.Data;
import com.connection.api.exception.ExceptionCentral;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        data.setTraceTransfer(resultSet.getString("TRACETRANSFER"));
        data.setMessageType(resultSet.getString("MESSAGETYPE"));
        data.setDebitAmount(resultSet.getString("DEBITAMOUNT"));
        data.setPayType(resultSet.getString("PAYDATE"));
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
    String builder = "INSERT INTO (VN_DATA (USERNAME, CUSTOMERNAME, TRANXID, MOBILENO, ACCOUNTNO, CUSNAME, AMOUNT," +
        "STATUS, RESCODE, BANKCODE, TRANXNOTE, TRANXDATE, TIPANDFEE, TYPE, QRINFO, ORDERCODE, PAYTYPE, QUANTITY," +
        "ADDTIONALDATA, MERCHANTNAME, CHECKSUM, QRVERSION, MOBILE, RESPCODE, TRACETRANSFER, MESSAGETYPE, DEBITAMOUNT, PAYDATE," +
        "REALAMOUNT, PROMOTIONCODE, URL, MOBILEID, CLIENTID, DEVICE, IPADDRESS, IMEI, TOTALAMOUNT, FEEAMOUNT, PCTIME," +
        "TELLERID, TELLERBRANCH, HOSTDATE, TYPESOURCE, BANKCARD) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    CallableStatement callableStatement = null;
    try {
      connection = databaseConnection.getConnection();
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
      preparedStatement.setString(25, data.getTraceTransfer());
      preparedStatement.setString(26, data.getMessageType());
      preparedStatement.setString(27, data.getDebitAmount());
      preparedStatement.setString(28, data.getPayDate());
      preparedStatement.setString(29, data.getRealAmount());
      preparedStatement.setString(30, data.getPromotionCode());
      preparedStatement.setString(31, data.getUrl());
      preparedStatement.setString(32, data.getMobileId());
      preparedStatement.setString(33, data.getClientId());
      preparedStatement.setString(34, data.getDevice());
      preparedStatement.setString(35, data.getIpAddress());
      preparedStatement.setString(36, data.getImei());
      preparedStatement.setString(37, data.getTotalAmount());
      preparedStatement.setString(38, data.getFeeAmount());
      preparedStatement.setString(39, data.getPcTime());
      preparedStatement.setString(40, data.getTellerId());
      preparedStatement.setString(41, data.getTellerBranch());
      preparedStatement.setString(42, data.getHostDate());
      preparedStatement.setString(43, data.getTypeSource());
      preparedStatement.setString(44, data.getBankCard());
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
