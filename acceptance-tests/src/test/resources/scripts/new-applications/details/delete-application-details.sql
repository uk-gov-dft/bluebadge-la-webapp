SET search_path TO applicationmanagement;

-- Use an invalid postcode and name to delete test data
DELETE FROM applicationmanagement.application WHERE id IN ('1087ac26-491a-46f0-9006-36187dc40764'::uuid,
  '1087ac26-491a-46f0-9006-33333dc33333'::uuid,
  '1087ac26-491a-46f0-9006-44444dc44444'::uuid,
  '1087ac26-491a-46f0-9006-55555dc55555'::uuid);
