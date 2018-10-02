SET search_path TO usermanagement;

DELETE FROM usermanagement.email_link WHERE user_id BETWEEN -1010 and -1000;
DELETE FROM usermanagement.users WHERE id BETWEEN -1010 and -1000;
