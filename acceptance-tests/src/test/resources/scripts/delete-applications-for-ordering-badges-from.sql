SET search_path TO applicationmanagement;

DELETE FROM applicationmanagement.app_artifact
WHERE application_id IN ('1087ac26-491a-46f0-9006-36187dc40764'::uuid, '1087ac26-491a-46f0-9006-36187dc40767'::uuid);
DELETE FROM applicationmanagement.application
WHERE id IN ('1087ac26-491a-46f0-9006-36187dc40764'::uuid, '1087ac26-491a-46f0-9006-36187dc40767'::uuid);

