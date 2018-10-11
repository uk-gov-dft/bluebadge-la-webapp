SET search_path TO usermanagement;

INSERT INTO usermanagement.users (id, name, email_address, local_authority_short_code, role_id, password, user_uuid, is_active, login_fail_count)
VALUES
  (-1000, 'aberd dft admin user', 'aberddftadmin@dft.gov.uk', 'ABERD', 1, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413ea'::UUID, TRUE, 0)
  ,(-1001, 'aberd la admin user', 'aberdlaadmin@dft.gov.uk', 'ABERD', 2, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413eb'::UUID, TRUE, 0)
  ,(-1002, 'aberd la editor user', 'aberdlaeditor@dft.gov.uk', 'ABERD', 3, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413ec'::UUID, TRUE, 0)
  ,(-1003, 'aberd la read only user', 'aberdlareadonly@dft.gov.uk', 'ABERD', 4, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413ed'::UUID, TRUE, 0)
  ,(-1004, 'kentc dft admin user', 'kentcdftadmin@dft.gov.uk', 'KENTC', 1, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413da'::UUID, TRUE, 0)
  ,(-1005, 'kentc la admin user', 'kentclaadminuser@dft.gov.uk', 'KENTC', 2, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413db'::UUID, TRUE, 0)
  ,(-1006, 'kentc la editor user', 'kentclaeditor@dft.gov.uk', 'KENTC', 3, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413dc'::UUID, TRUE, 0)
  ,(-1007, 'kentc la read only user', 'kentclareadonly@dft.gov.uk', 'KENTC', 4, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'd5e2f9c5-74e8-4445-bc5d-f75a6b3413dd'::UUID, TRUE, 0)
  ,(-1008, 'nearly locked out', 'lawa_nearlylocked@dft.gov.uk', 'KENTC', 4, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'fce563dc-2ddf-4e4c-b9d1-7915be3edb83'::UUID, TRUE, 9)
  ,(-1009, 'locked out', 'lawa_locked@dft.gov.uk', 'KENTC', 4, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'ff4a0dcb-c07b-4928-99e1-f64c23cbe84f'::UUID, TRUE, 10)
;

INSERT INTO usermanagement.email_link(user_id, uuid, is_active, created_on) VALUES
  (-1009, '06c8f2fd-7064-4abb-bba9-b3839acd19b7', true, now())
  ,(-1009, '032da369-8877-4722-bde2-7ae01408b06d', false, now())
  ,(-1009, '8fac089e-b925-4b6e-8470-7ab0a1c6a301', true, now() - INTERVAL '25 HOUR')
;
