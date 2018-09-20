SET search_path TO usermanagement;

-- Use an invalid postcode and name to delete test data
DELETE FROM usermanagement.users WHERE id IN ('-1000', '-1001', '-1002', '-1003', '-1004', '-1005', '-1006', '-1007');
