package com.connection.api.dto;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
  private String userName;
  private String customerName;
  private String tranxId;
  private String mobileNo;
  private String accountNo;
  private String cusName;
  private Integer amount;
  private String status;
  private String rescode;
  private String bankCode;
  private String tranxNote;
  private String tranxDate;
  private String tipAndFee;
  private String type;
  private List<Item> item;
  private String qrInfo;
  private String orderCode;
  private String payType;
  private String quantity;
  private String addtionalData;
  private String merchantName;
  private String checkSum;
  private String qrVersion;
  private String mobile;
  private String respCode;
  private String respDesc;
  private String traceTransfer;
  private String messageType;
  private String debitAmount;
  private String payDate;
  private String realAmount;
  private String promotionCode;
  private String url;
  private String mobileId;
  private String clientId;
  private String device;
  private String ipAddress;
  private String imei;
  private String totalAmount;
  private String feeAmount;
  private String pcTime;
  private String tellerId;
  private String tellerBranch;
  private String hostDate;
  private String typeSource;
  private String bankCard;

  public Data(String userName, String customerName, String tranxId, String mobileNo, String accountNo, String cusName,
              Integer amount, String status, String rescode, String bankCode, String tranxNote, String tranxDate,
              String tipAndFee, String type, List<Item> item, String qrInfo, String orderCode, String payType,
              String quantity, String addtionalData, String merchantName, String checkSum, String qrVersion,
              String mobile, String respCode, String respDesc, String traceTransfer, String messageType,
              String debitAmount, String payDate, String realAmount, String promotionCode, String url,
              String mobileId, String clientId, String device, String ipAddress, String imei, String totalAmount,
              String feeAmount, String pcTime, String tellerId, String tellerBranch, String hostDate,
              String typeSource, String bankCard) {
    this.userName = userName;
    this.customerName = customerName;
    this.tranxId = tranxId;
    this.mobileNo = mobileNo;
    this.accountNo = accountNo;
    this.cusName = cusName;
    this.amount = amount;
    this.status = status;
    this.rescode = rescode;
    this.bankCode = bankCode;
    this.tranxNote = tranxNote;
    this.tranxDate = tranxDate;
    this.tipAndFee = tipAndFee;
    this.type = type;
    this.item = item;
    this.qrInfo = qrInfo;
    this.orderCode = orderCode;
    this.payType = payType;
    this.quantity = quantity;
    this.addtionalData = addtionalData;
    this.merchantName = merchantName;
    this.checkSum = checkSum;
    this.qrVersion = qrVersion;
    this.mobile = mobile;
    this.respCode = respCode;
    this.respDesc = respDesc;
    this.traceTransfer = traceTransfer;
    this.messageType = messageType;
    this.debitAmount = debitAmount;
    this.payDate = payDate;
    this.realAmount = realAmount;
    this.promotionCode = promotionCode;
    this.url = url;
    this.mobileId = mobileId;
    this.clientId = clientId;
    this.device = device;
    this.ipAddress = ipAddress;
    this.imei = imei;
    this.totalAmount = totalAmount;
    this.feeAmount = feeAmount;
    this.pcTime = pcTime;
    this.tellerId = tellerId;
    this.tellerBranch = tellerBranch;
    this.hostDate = hostDate;
    this.typeSource = typeSource;
    this.bankCard = bankCard;
  }

  public Data() {
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getTranxId() {
    return tranxId;
  }

  public void setTranxId(String tranxId) {
    this.tranxId = tranxId;
  }

  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public String getCusName() {
    return cusName;
  }

  public void setCusName(String cusName) {
    this.cusName = cusName;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRescode() {
    return rescode;
  }

  public void setRescode(String rescode) {
    this.rescode = rescode;
  }

  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public String getTranxNote() {
    return tranxNote;
  }

  public void setTranxNote(String tranxNote) {
    this.tranxNote = tranxNote;
  }

  public String getTranxDate() {
    return tranxDate;
  }

  public void setTranxDate(String tranxDate) {
    this.tranxDate = tranxDate;
  }

  public String getTipAndFee() {
    return tipAndFee;
  }

  public void setTipAndFee(String tipAndFee) {
    this.tipAndFee = tipAndFee;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<Item> getItem() {
    return item;
  }

  public void setItem(List<Item> item) {
    this.item = item;
  }

  public String getQrInfo() {
    return qrInfo;
  }

  public void setQrInfo(String qrInfo) {
    this.qrInfo = qrInfo;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getPayType() {
    return payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public String getAddtionalData() {
    return addtionalData;
  }

  public void setAddtionalData(String addtionalData) {
    this.addtionalData = addtionalData;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getCheckSum() {
    return checkSum;
  }

  public void setCheckSum(String checkSum) {
    this.checkSum = checkSum;
  }

  public String getQrVersion() {
    return qrVersion;
  }

  public void setQrVersion(String qrVersion) {
    this.qrVersion = qrVersion;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getRespCode() {
    return respCode;
  }

  public void setRespCode(String respCode) {
    this.respCode = respCode;
  }

  public String getRespDesc() {
    return respDesc;
  }

  public void setRespDesc(String respDesc) {
    this.respDesc = respDesc;
  }

  public String getTraceTransfer() {
    return traceTransfer;
  }

  public void setTraceTransfer(String traceTransfer) {
    this.traceTransfer = traceTransfer;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public String getDebitAmount() {
    return debitAmount;
  }

  public void setDebitAmount(String debitAmount) {
    this.debitAmount = debitAmount;
  }

  public String getPayDate() {
    return payDate;
  }

  public void setPayDate(String payDate) {
    this.payDate = payDate;
  }

  public String getRealAmount() {
    return realAmount;
  }

  public void setRealAmount(String realAmount) {
    this.realAmount = realAmount;
  }

  public String getPromotionCode() {
    return promotionCode;
  }

  public void setPromotionCode(String promotionCode) {
    this.promotionCode = promotionCode;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMobileId() {
    return mobileId;
  }

  public void setMobileId(String mobileId) {
    this.mobileId = mobileId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public String getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getFeeAmount() {
    return feeAmount;
  }

  public void setFeeAmount(String feeAmount) {
    this.feeAmount = feeAmount;
  }

  public String getPcTime() {
    return pcTime;
  }

  public void setPcTime(String pcTime) {
    this.pcTime = pcTime;
  }

  public String getTellerId() {
    return tellerId;
  }

  public void setTellerId(String tellerId) {
    this.tellerId = tellerId;
  }

  public String getTellerBranch() {
    return tellerBranch;
  }

  public void setTellerBranch(String tellerBranch) {
    this.tellerBranch = tellerBranch;
  }

  public String getHostDate() {
    return hostDate;
  }

  public void setHostDate(String hostDate) {
    this.hostDate = hostDate;
  }

  public String getTypeSource() {
    return typeSource;
  }

  public void setTypeSource(String typeSource) {
    this.typeSource = typeSource;
  }

  public String getBankCard() {
    return bankCard;
  }

  public void setBankCard(String bankCard) {
    this.bankCard = bankCard;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}