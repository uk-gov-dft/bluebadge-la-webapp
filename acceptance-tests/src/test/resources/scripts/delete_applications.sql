SET search_path TO applicationmanagement;

-- Use an invalid postcode and name to delete test data
DELETE FROM applicationmanagement.application WHERE id IN ('1087ac26-491a-46f0-9006-36187dc40764'::uuid,
														 '1087ac26-491a-46f0-9006-36187dc40765'::uuid,
														 '1087ac26-491a-46f0-9006-36187dc40766'::uuid,
														 '1087ac26-491a-46f0-9006-36187dc40767'::uuid);
