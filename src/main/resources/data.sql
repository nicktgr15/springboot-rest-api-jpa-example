INSERT INTO version (version, enabled) VALUES
  ('1.0.0', 0),
  ('1.1.0', 1),
  ('1.2.0', 1),
  ('1.3.0', 1);

INSERT INTO feature (feature, enabled) VALUES
  ('credit', 1),
  ('notifications', 1),
  ('newfeature', 0);

INSERT INTO feature_by_version (version, feature, enabled) VALUES
  ('1.0.0', 'credit', 1),
  ('1.0.0', 'notifications', 0),
  ('1.1.0', 'newfeature', 1),
  ('1.2.0', 'notifications', 0);

INSERT INTO user (username) VALUES
  ('userA'),
  ('userB');

INSERT INTO feature_by_version_by_user (version, feature, username, enabled) VALUES
  ('1.1.0', 'credit', 'userA', 0);