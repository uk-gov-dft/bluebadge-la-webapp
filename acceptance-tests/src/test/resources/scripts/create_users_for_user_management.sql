SET search_path TO usermanagement;

INSERT INTO usermanagement.users (id, name, email_address, local_authority_short_code, role_id, password, user_uuid, is_active, login_fail_count)
VALUES
  (-2010, 'user to update', 'usertoupdate@dft.gov.uk', 'ABERD', 3, '$2a$11$l8Y6fw6mOmj39naJfQtgvu1KITnSBDURsP7kCHWsJXthM.atfzNWC', 'ff4a0dcb-c07b-4928-99e1-f64c23cbe84f'::UUID, TRUE, 0)
;
