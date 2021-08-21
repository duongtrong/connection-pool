package com.connection.api.constants;

public final class ExecuteQuery {

  private ExecuteQuery() {
  }

  public static final String INSERT_TEST = "CREATE OR REPLACE PROCEDURE insertTest\n" +
      "(in_id IN VN_TEST.TEST_ID%TYPE,\n" +
      " in_testName IN VN_TEST.TEST_NAME%TYPE,\n" +
      " in_description IN VN_TEST.DESCRIPTION%TYPE,\n" +
      " in_roll_name IN VN_TEST.ROLL_NAME%TYPE,\n" +
      " out_result OUT VARCHAR2)\n" +
      "AS\n" +
      "BEGIN\n" +
      "    INSERT INTO VN_TEST (TEST_ID, TEST_NAME, DESCRIPTION, ROLL_NAME)\n" +
      "    VALUES (in_id, in_testName, in_description, in_roll_name);\n" +
      "    commit;\n" +
      "\n" +
      "    out_result := 'TRUE';\n" +
      "EXCEPTION\n" +
      "    WHEN OTHERS THEN\n" +
      "        out_result := 'FALSE';\n" +
      "        ROLLBACK;\n" +
      "end;";

  public static final String INSERT_DATA = "CREATE OR REPLACE PROCEDURE insertData\n" +
      "    (\n" +
      "    IN_DATA_ID IN VN_DATA.DATA_ID%TYPE,\n" +
      "    IN_USERNAME IN VN_DATA.USERNAME%TYPE,\n" +
      "    IN_CUSTOMERNAME IN VN_DATA.CUSTOMERNAME%TYPE,\n" +
      "    IN_TRANXID IN VN_DATA.TRANXID%TYPE,\n" +
      "    IN_MOBILENO IN VN_DATA.MOBILENO%TYPE,\n" +
      "    IN_CUSNAME IN VN_DATA.CUSNAME%TYPE,\n" +
      "    IN_AMOUNT IN VN_DATA.AMOUNT%TYPE,\n" +
      "    IN_STATUS IN VN_DATA.STATUS%TYPE,\n" +
      "    IN_RESCODE IN VN_DATA.RESCODE%TYPE,\n" +
      "    IN_BANKCODE IN VN_DATA.BANKCODE%TYPE,\n" +
      "    IN_TRANXNOTE IN VN_DATA.TRANXNOTE%TYPE,\n" +
      "    IN_TRANXDATE IN VN_DATA.TRANXDATE%TYPE,\n" +
      "    IN_TIPANDFEE IN VN_DATA.TIPANDFEE%TYPE,\n" +
      "    IN_TYPE IN VN_DATA.TYPE%TYPE,\n" +
      "    IN_QRINFO IN VN_DATA.QRINFO%TYPE,\n" +
      "    IN_ORDERCODE IN VN_DATA.ORDERCODE%TYPE,\n" +
      "    IN_PAYTYPE IN VN_DATA.PAYTYPE%TYPE,\n" +
      "    IN_QUANTITY IN VN_DATA.QUANTITY%TYPE,\n" +
      "    IN_ADDTIONALDATA IN VN_DATA.ADDTIONALDATA%TYPE,\n" +
      "    IN_MERCHANTNAME IN VN_DATA.MERCHANTNAME%TYPE,\n" +
      "    IN_CHECKSUM IN VN_DATA.CHECKSUM%TYPE,\n" +
      "    IN_QRVERSION IN VN_DATA.QRVERSION%TYPE,\n" +
      "    IN_MOBILE IN VN_DATA.MOBILE%TYPE,\n" +
      "    IN_RESPCODE IN VN_DATA.RESPCODE%TYPE,\n" +
      "    IN_RESPDESC IN VN_DATA.RESPDESC%TYPE,\n" +
      "    IN_TRACETRANSFER IN VN_DATA.TRACETRANSFER%TYPE,\n" +
      "    IN_MESSAGETYPE IN VN_DATA.MESSAGETYPE%TYPE,\n" +
      "    IN_DEBITAMOUNT IN VN_DATA.DEBITAMOUNT%TYPE,\n" +
      "    IN_PAYDATE IN VN_DATA.PAYDATE%TYPE,\n" +
      "    IN_REALAMOUNT IN VN_DATA.REALAMOUNT%TYPE,\n" +
      "    IN_PROMOTIONCODE IN VN_DATA.PROMOTIONCODE%TYPE,\n" +
      "    IN_URL IN VN_DATA.URL%TYPE,\n" +
      "    IN_MOBILEID IN VN_DATA.MOBILEID%TYPE,\n" +
      "    IN_CLIENTID IN VN_DATA.CLIENTID%TYPE,\n" +
      "    IN_DEVICE IN VN_DATA.DEVICE%TYPE,\n" +
      "    IN_IPADDRESS IN VN_DATA.IPADDRESS%TYPE,\n" +
      "    IN_IMEI IN VN_DATA.IMEI%TYPE,\n" +
      "    IN_TOTALAMOUNT IN VN_DATA.TOTALAMOUNT%TYPE,\n" +
      "    IN_FEEAMOUNT IN VN_DATA.FEEAMOUNT%TYPE,\n" +
      "    IN_PCTIME IN VN_DATA.PCTIME%TYPE,\n" +
      "    IN_TELLERID IN VN_DATA.TELLERID%TYPE,\n" +
      "    IN_TELLERBRANCH IN VN_DATA.TELLERBRANCH%TYPE,\n" +
      "    IN_HOSTDATE IN VN_DATA.HOSTDATE%TYPE,\n" +
      "    IN_TYPESOURCE IN VN_DATA.TYPESOURCE%TYPE,\n" +
      "    IN_BANKCARD IN VN_DATA.BANKCARD%TYPE,\n" +
      "    out_result OUT VARCHAR2)\n" +
      "AS BEGIN\n" +
      "    INSERT INTO VN_DATA (DATA_ID, USERNAME, CUSTOMERNAME, TRANXID, MOBILENO, CUSNAME, AMOUNT,\n" +
      "                         STATUS, RESCODE, BANKCODE, TRANXNOTE, TRANXDATE, TIPANDFEE, \"TYPE\",\n" +
      "                         QRINFO, ORDERCODE, PAYTYPE, QUANTITY, ADDTIONALDATA, MERCHANTNAME,\n" +
      "                         CHECKSUM, QRVERSION, MOBILE, RESPCODE, RESPDESC, TRACETRANSFER, MESSAGETYPE,\n" +
      "                         DEBITAMOUNT, PAYDATE, REALAMOUNT, PROMOTIONCODE, URL, MOBILEID, CLIENTID, DEVICE,\n" +
      "                         IPADDRESS, IMEI, TOTALAMOUNT, FEEAMOUNT, PCTIME, TELLERID, TELLERBRANCH, HOSTDATE,\n" +
      "                         TYPESOURCE, BANKCARD)\n" +
      "                         VALUES (IN_DATA_ID, IN_USERNAME, IN_CUSTOMERNAME, IN_TRANXID,\n" +
      "                                 IN_MOBILENO, IN_CUSNAME, IN_AMOUNT, IN_STATUS,\n" +
      "                                 IN_RESCODE, IN_BANKCODE, IN_TRANXNOTE, IN_TRANXDATE,\n" +
      "                                 IN_TIPANDFEE, IN_TYPE, IN_QRINFO, IN_ORDERCODE,\n" +
      "                                 IN_PAYTYPE, IN_QUANTITY, IN_ADDTIONALDATA, IN_MERCHANTNAME,\n" +
      "                                 IN_CHECKSUM, IN_QRVERSION, IN_MOBILE, IN_RESPCODE,\n" +
      "                                 IN_RESPDESC,IN_TRACETRANSFER, IN_MESSAGETYPE, IN_DEBITAMOUNT,\n" +
      "                                 IN_PAYDATE, IN_REALAMOUNT, IN_PROMOTIONCODE, IN_URL,\n" +
      "                                 IN_MOBILEID, IN_CLIENTID, IN_DEVICE, IN_IPADDRESS,\n" +
      "                                 IN_IMEI, IN_TOTALAMOUNT, IN_FEEAMOUNT, IN_PCTIME,\n" +
      "                                 IN_TELLERID, IN_TELLERBRANCH, IN_HOSTDATE, IN_TYPESOURCE,\n" +
      "                                 IN_BANKCARD);\n" +
      "    out_result := 'TRUE';\n" +
      "EXCEPTION\n" +
      "    WHEN OTHERS THEN\n" +
      "    out_result := 'FALSE';\n" +
      "    ROLLBACK;\n" +
      "end;";
}
