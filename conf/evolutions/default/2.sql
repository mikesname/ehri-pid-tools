#--- !Ups

INSERT INTO pids (ptype, value, target, client)
VALUES ('DOI', '10.14454/fxws-0523', 'https://example.com/pid-test-1', 'system');

# --- !Downs

DELETE FROM pids;