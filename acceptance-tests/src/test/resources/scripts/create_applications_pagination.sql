SET search_path TO applicationmanagement;

DELETE FROM applicationmanagement.application;

CREATE OR REPLACE FUNCTION applicationmanagement.insert_data(p_rows integer, p_la varchar) RETURNS integer
AS '
DECLARE
counter INTEGER := 0 ;
BEGIN

 LOOP
 EXIT WHEN counter = p_rows ;
 counter := counter + 1 ;

 insert into applicationmanagement.application(
 id, local_authority_code, app_type_code, is_payment_taken, submission_datetime, party_code
 , contact_name, contact_building_street, contact_town_city, contact_postcode, holder_name, primary_phone_no
 ) VALUES (
 md5(random()::text || clock_timestamp()::text)::uuid, p_la, ''NEW'', true, current_timestamp, ''PERSON''
 , CONCAT(counter, ''Person''), CONCAT(counter, ''Street''), ''Atown'', CONCAT(''WV1'', counter, ''AW'')
 , CONCAT(counter, ''Holder pagination to delete''), ''A1234635981''
 );

 END LOOP ;

RETURN 1;
END;' LANGUAGE plpgsql;

-- Add 120 ABERD applications.
SELECT applicationmanagement.insert_data(120, 'ABERD');

