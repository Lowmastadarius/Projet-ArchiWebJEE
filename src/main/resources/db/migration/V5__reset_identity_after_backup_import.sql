-- Après import explicite des IDs (V4), réaligner les colonnes IDENTITY
-- pour éviter les collisions lors des nouveaux inserts.

ALTER TABLE levels ALTER COLUMN id RESTART WITH 4;
ALTER TABLE users ALTER COLUMN id RESTART WITH 2;
ALTER TABLE puzzles ALTER COLUMN id RESTART WITH 11;
ALTER TABLE puzzle_constraints ALTER COLUMN id RESTART WITH 11;
ALTER TABLE save_games ALTER COLUMN id RESTART WITH 1;
ALTER TABLE game_attempts ALTER COLUMN id RESTART WITH 1;
ALTER TABLE ui_assets ALTER COLUMN id RESTART WITH 1;
