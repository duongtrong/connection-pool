CREATE OR REPLACE PROCEDURE insertData
(IN_DATA_ID IN VN_DATA.DATA_ID%TYPE,
 IN_USERNAME IN VN_DATA.USERNAME%TYPE,
 IN_CUSTOMERNAME IN VN_DATA.CUSTOMERNAME%TYPE,
 IN_TRANXID IN VN_DATA.TRANXID%TYPE,
 IN_MOBILENO IN VN_DATA.MOBILENO%TYPE,
 IN_ACCOUNTNO IN VN_DATA.ACCOUNTNO%TYPE,
 IN_CUSNAME IN VN_DATA.CUSNAME%TYPE,
 IN_AMOUNT IN VN_DATA.AMOUNT%TYPE,
 IN_STATUS IN VN_DATA.STATUS%TYPE,
 IN_RESCODE IN VN_DATA.RESCODE%TYPE,
 IN_BANKCODE IN VN_DATA.BANKCODE%TYPE,
 IN_TRANXNOTE IN VN_DATA.TRANXNOTE%TYPE,
 IN_TRANXDATE IN VN_DATA.TRANXDATE%TYPE,
 IN_TIPANDFEE IN VN_DATA.TIPANDFEE%TYPE,
 IN_TYPE IN VN_DATA.TYPE%TYPE,
 IN_QRINFO IN VN_DATA.QRINFO%TYPE,
 IN_ORDERCODE IN VN_DATA.ORDERCODE%TYPE,
 IN_PAYTYPE IN VN_DATA.PAYTYPE%TYPE,
 IN_QUANTITY IN VN_DATA.QUANTITY%TYPE,
 IN_ADDTIONALDATA IN VN_DATA.ADDTIONALDATA%TYPE,
 IN_MERCHANTNAME IN VN_DATA.MERCHANTNAME%TYPE,
 IN_CHECKSUM IN VN_DATA.CHECKSUM%TYPE,
 IN_QRVERSION IN VN_DATA.QRVERSION%TYPE,
 IN_MOBILE IN VN_DATA.MOBILE%TYPE,
 IN_RESPCODE IN VN_DATA.RESPCODE%TYPE,
 IN_RESPDESC IN VN_DATA.RESPDESC%TYPE,
 IN_TRACETRANSFER IN VN_DATA.TRACETRANSFER%TYPE,
 IN_MESSAGETYPE IN VN_DATA.MESSAGETYPE%TYPE,
 IN_DEBITAMOUNT IN VN_DATA.DEBITAMOUNT%TYPE,
 IN_PAYDATE IN VN_DATA.PAYDATE%TYPE,
 IN_REALAMOUNT IN VN_DATA.REALAMOUNT%TYPE,
 IN_PROMOTIONCODE IN VN_DATA.PROMOTIONCODE%TYPE,
 IN_URL IN VN_DATA.URL%TYPE,
 IN_MOBILEID IN VN_DATA.MOBILEID%TYPE,
 IN_CLIENTID IN VN_DATA.CLIENTID%TYPE,
 IN_DEVICE IN VN_DATA.DEVICE%TYPE,
 IN_IPADDRESS IN VN_DATA.IPADDRESS%TYPE,
 IN_IMEI IN VN_DATA.IMEI%TYPE,
 IN_TOTALAMOUNT IN VN_DATA.TOTALAMOUNT%TYPE,
 IN_FEEAMOUNT IN VN_DATA.FEEAMOUNT%TYPE,
 IN_PCTIME IN VN_DATA.PCTIME%TYPE,
 IN_TELLERID IN VN_DATA.TELLERID%TYPE,
 IN_TELLERBRANCH IN VN_DATA.TELLERBRANCH%TYPE,
 IN_HOSTDATE IN VN_DATA.HOSTDATE%TYPE,
 IN_TYPESOURCE IN VN_DATA.TYPESOURCE%TYPE,
 IN_BANKCARD IN VN_DATA.BANKCARD%TYPE,
 out_result OUT VARCHAR2)
AS BEGIN
    INSERT INTO VN_DATA (DATA_ID, USERNAME, CUSTOMERNAME, TRANXID, MOBILENO, ACCOUNTNO, CUSNAME, AMOUNT,
                         STATUS, RESCODE, BANKCODE, TRANXNOTE, TRANXDATE, TIPANDFEE, "TYPE",
                         QRINFO, ORDERCODE, PAYTYPE, QUANTITY, ADDTIONALDATA, MERCHANTNAME,
                         CHECKSUM, QRVERSION, MOBILE, RESPCODE, RESPDESC, TRACETRANSFER, MESSAGETYPE,
                         DEBITAMOUNT, PAYDATE, REALAMOUNT, PROMOTIONCODE, URL, MOBILEID, CLIENTID, DEVICE,
                         IPADDRESS, IMEI, TOTALAMOUNT, FEEAMOUNT, PCTIME, TELLERID, TELLERBRANCH, HOSTDATE,
                         TYPESOURCE, BANKCARD)
    VALUES (IN_DATA_ID, IN_USERNAME, IN_CUSTOMERNAME, IN_TRANXID,
            IN_MOBILENO, IN_ACCOUNTNO, IN_CUSNAME, IN_AMOUNT, IN_STATUS,
            IN_RESCODE, IN_BANKCODE, IN_TRANXNOTE, IN_TRANXDATE,
            IN_TIPANDFEE, IN_TYPE, IN_QRINFO, IN_ORDERCODE,
            IN_PAYTYPE, IN_QUANTITY, IN_ADDTIONALDATA, IN_MERCHANTNAME,
            IN_CHECKSUM, IN_QRVERSION, IN_MOBILE, IN_RESPCODE,
            IN_RESPDESC,IN_TRACETRANSFER, IN_MESSAGETYPE, IN_DEBITAMOUNT,
            IN_PAYDATE, IN_REALAMOUNT, IN_PROMOTIONCODE, IN_URL,
            IN_MOBILEID, IN_CLIENTID, IN_DEVICE, IN_IPADDRESS,
            IN_IMEI, IN_TOTALAMOUNT, IN_FEEAMOUNT, IN_PCTIME,
            IN_TELLERID, IN_TELLERBRANCH, IN_HOSTDATE, IN_TYPESOURCE,
            IN_BANKCARD);
    out_result := 'TRUE';
EXCEPTION
    WHEN OTHERS THEN
        out_result := 'FALSE';
        ROLLBACK;
end;