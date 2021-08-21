CREATE OR REPLACE PROCEDURE insertTest
(in_id IN VN_TEST.TEST_ID%TYPE,
 in_testName IN VN_TEST.TEST_NAME%TYPE,
 in_description IN VN_TEST.DESCRIPTION%TYPE,
 in_roll_name IN VN_TEST.ROLL_NAME%TYPE,
 out_result OUT VARCHAR2)
AS
BEGIN
    INSERT INTO VN_TEST (TEST_ID, TEST_NAME, DESCRIPTION, ROLL_NAME)
    VALUES (in_id, in_testName, in_description, in_roll_name);
    commit;

    out_result := 'TRUE';
EXCEPTION
    WHEN OTHERS THEN
    out_result := 'FALSE';
    ROLLBACK;
end;