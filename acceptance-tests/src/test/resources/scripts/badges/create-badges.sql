SET search_path = badgemanagement;

insert into badge
(badge_no, badge_status, party_code,
        local_authority_short_code, local_authority_ref,
        app_channel_code, start_date, expiry_date,
        eligibility_code, image_link, deliver_to_code,
        deliver_option_code, holder_name, nino,
        dob, gender_code, contact_name,
        contact_building_street, contact_line2, contact_town_city,
        contact_postcode, primary_phone_no, secondary_phone_no,
        contact_email_address, holder_name_upper, order_date, app_date)
values
('DEL1', 'ISSUED', 'PERSON',
 'ABERD', 'to cancel ISSUED',
 'ONLINE','2025-05-01', '2028-05-01',
 'PIP', '', 'HOME',
 'STAND', 'Test Data Michael', '',
 '1953-09-12', 'MALE', 'contact name',
 'building and street', '', 'Town or city',
 'S637FU', '020 7014 0800', null,
 'test101@mailinator.com', 'TEST DATA MICHAEL', '2018-07-24', '2018-07-24');

insert into badge
(badge_no, badge_status, party_code,
        local_authority_short_code, local_authority_ref,
        app_channel_code, start_date, expiry_date,
        eligibility_code, image_link, deliver_to_code,
        deliver_option_code, holder_name, nino,
        dob, gender_code, contact_name,
        contact_building_street, contact_line2, contact_town_city,
        contact_postcode, primary_phone_no, secondary_phone_no,
        contact_email_address, holder_name_upper, order_date, app_date)
values
('DEL2', 'ISSUED', 'PERSON',
 'ABERD', 'to replace ISSUED',
 'ONLINE','2025-05-01', '2028-05-01',
 'PIP', '', 'HOME',
 'STAND', 'Test Data John', '',
 '1953-09-12', 'MALE', 'contact name',
 'building and street', '', 'Town or city',
 'S637FU', '020 7014 0800', null,
 'test101@mailinator.com', 'TEST DATA JOHN', '2018-07-24', '2018-07-24');

insert into badge
(badge_no, badge_status, party_code,
        local_authority_short_code, local_authority_ref,
        app_channel_code, start_date, expiry_date,
        eligibility_code, image_link, deliver_to_code,
        deliver_option_code, holder_name, nino,
        dob, gender_code, contact_name,
        contact_building_street, contact_line2, contact_town_city,
        contact_postcode, primary_phone_no, secondary_phone_no,
        contact_email_address, holder_name_upper, order_date, app_date)
values
('DEL3', 'ISSUED', 'PERSON',
 'ABERD', 'to replace ISSUED',
 'ONLINE','2005-05-01', '2008-05-01',
 'PIP', '', 'HOME',
 'STAND', 'Test Data John', '',
 '1953-09-12', 'MALE', 'contact name',
 'building and street', '', 'Town or city',
 'S637FU', '020 7014 0800', null,
 'test101@mailinator.com', 'TEST DATA JOHN', '2018-07-24', '2018-12-24');

 insert into badge
(badge_no, badge_status, party_code,
        local_authority_short_code, local_authority_ref,
        app_channel_code, start_date, expiry_date,
        eligibility_code, image_link, deliver_to_code,
        deliver_option_code, holder_name, nino,
        dob, gender_code, contact_name,
        contact_building_street, contact_line2, contact_town_city,
        contact_postcode, primary_phone_no, secondary_phone_no,
        contact_email_address, holder_name_upper, order_date, app_date)
values
('DEL4', 'ISSUED', 'PERSON',
 'ABERD', 'to cancel ISSUED',
 'ONLINE','2025-05-01', '2028-05-01',
 'PIP', '', 'HOME',
 'STAND', 'Test Data Rob', '',
 '1953-09-12', 'MALE', 'contact name',
 'building and street', '', 'Town or city',
 'AB333CC', '020 7014 0800', null,
 'test101@mailinator.com', 'TEST DATA ROB', '2018-07-24', '2018-07-24');
 