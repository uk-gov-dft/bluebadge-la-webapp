SET search_path TO applicationmanagement;

-- Use an invalid postcode and name to delete test data
DELETE FROM applicationmanagement.application WHERE id = '1087ac26-491a-46f0-9006-36187dc40764'::uuid;
