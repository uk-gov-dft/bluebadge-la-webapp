SET search_path TO badgemanagement;

DELETE FROM badgemanagement.badge;

CREATE OR REPLACE FUNCTION badgemanagement.insert_data(p_rows integer, p_la varchar) RETURNS integer
AS '
DECLARE
counter INTEGER := 0 ;
BEGIN

 LOOP
 EXIT WHEN counter = p_rows ;
 counter := counter + 1 ;

 insert into badgemanagement.badge(
      badge_no,
      badge_status,
      party_code,
      app_channel_code,
      start_date,
      expiry_date,
      deliver_to_code,
      deliver_option_code,
      holder_name,
      holder_name_upper,
      contact_building_street,
      contact_town_city,
      contact_postcode,
      primary_phone_no,
      secondary_phone_no,
      order_date,
      local_authority_short_code)
VALUES (
  CONCAT(counter, ''KKK''),
  ''ISSUED'',
  ''PERSON'',
  ''ONLINE'',
  ''2025-05-01'',
  ''2027-05-01'',
  ''HOME'',
  ''STAND'',
  CONCAT(counter, ''Holder name pagination to delete''),
  CONCAT(counter, ''HOLDER NAME PAGINATION TO DELETE''),
  ''building and street'',
  ''Town or city'',
  ''S637FU'',
  ''020 7014 0800'',
  ''020 7014 0801'',
  ''2019-04-01'',
  p_la);

 END LOOP ;

RETURN 1;
END;
' LANGUAGE plpgsql;

-- Add 120 ABERD applications.
SELECT badgemanagement.insert_data(120, 'ABERD');

