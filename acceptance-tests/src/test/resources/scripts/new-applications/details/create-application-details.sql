SET search_path TO applicationmanagement;


INSERT INTO applicationmanagement.application(id, local_authority_code, app_type_code, is_payment_taken, submission_datetime, party_code, contact_name, contact_building_street, contact_town_city, contact_postcode, holder_name, existing_badge_no, contact_line2, primary_phone_no, secondary_phone_no, contact_email_address, org_is_charity, org_charity_no, no_of_badges, nino, dob, gender_code, holder_name_at_birth, eligibility_code, eligibility_conditions, benefit_is_indefinite, benefit_expiry_date, walk_other_desc, walk_length_code, walk_speed_code, arms_driving_freq, arms_is_adapted_vehicle, arms_adapted_veh_desc, blind_registered_at_la_code, bulky_equipment_type_code
 ) 
VALUES 
(
 '1087ac26-491a-46f0-9006-36187dc40764'::uuid, 
 'ABERD', 
 'NEW', 
 false, 
 '2011-01-01 03:00:00'::TIMESTAMP , 
 'PERSON', 
 'Contact Name', 
 'Contact Building Street', 
 'Contact Town City', 
 'SW11AA', 
 'John The First', 
 null, 
 null, 
 '0502345678', 
 null, 
 'test@mail.com', 
 false, 
 null, 
 1, 
 'AA234567B', 
 '1970-05-29'::DATE, 
 'MALE', 
 'Holder Name At Birth', 
 'WALKD', 
 null, 
 null, 
 null, 
 null, 
 'LESSMIN', 
 'SLOW', 
 null, 
 null,
 null, 
 null, 
 null
 );