SET search_path TO usermanagement;

INSERT INTO usermanagement.users (id, name, email_address, local_authority_short_code, role_id, password, user_uuid, is_active)
VALUES
  (-1000, 'aberd dft admin user', 'aberddftadmin@dft.gov.uk', 'ABERD', 1, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413ea'::UUID, TRUE)
  ,(-1001, 'aberd la admin user', 'aberdlaadmin@dft.gov.uk', 'ABERD', 2, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413eb'::UUID, TRUE)
  ,(-1002, 'aberd la editor user', 'aberdlaeditor@dft.gov.uk', 'ABERD', 3, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413ec'::UUID, TRUE)
  ,(-1003, 'aberd la read only user', 'aberdlareadonly@dft.gov.uk', 'ABERD', 4, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413ed'::UUID, TRUE)
  ,(-1004, 'kentc dft admin user', 'kentcdftadmin@dft.gov.uk', 'KENTC', 1, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413da'::UUID, TRUE)
  ,(-1005, 'kentc la admin user', 'kentclaadminuser@dft.gov.uk', 'KENTC', 2, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413db'::UUID, TRUE)
  ,(-1006, 'kentc la editor user', 'kentclaeditor@dft.gov.uk', 'KENTC', 3, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413dc'::UUID, TRUE)
  ,(-1007, 'kentc la read only user', 'kentclareadonly@dft.gov.uk', 'KENTC', 4, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413dd'::UUID, TRUE)
;
