-- Import des données depuis l'ancienne BDD (backup.sql)
-- Objectif: remplacer le seed minimal (V1) par les instances réelles.

-- Nettoyage (ordre inverse des FK)
DELETE FROM game_attempts;
DELETE FROM save_games;
DELETE FROM puzzle_constraints;
DELETE FROM puzzles;
DELETE FROM ui_assets;
DELETE FROM users;
DELETE FROM levels;

-- LEVELS
INSERT INTO levels (id, code, display_name, difficulty_weight, description) VALUES
(1, 'EASY', 'Easy', 1, 'Niveau facile'),
(2, 'MEDIUM', 'Medium', 2, 'Niveau intermédiaire'),
(3, 'HARD', 'Hard', 3, 'Niveau difficile');

-- USERS
INSERT INTO users (id, username, password_hash, total_score, created_at, updated_at) VALUES
(1, 'rayan', '$2a$10$qqEtors.DPCcbBCsGBAJyeMofIgWS6Cd1fU2uTmXU7iEc6Qv.D5Cq', 0, TIMESTAMP '2026-04-30 13:09:47.645672', TIMESTAMP '2026-04-30 13:09:47.645672');

-- PUZZLES
INSERT INTO puzzles (id, level_id, name, status, created_at) VALUES
(1, 1, 'Puzzle Easy #1', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(2, 1, 'Puzzle Easy #2', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(3, 1, 'Puzzle Easy #3', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(4, 1, 'Puzzle Easy #4', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(5, 2, 'Puzzle Medium #1', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(6, 2, 'Puzzle Medium #2', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(7, 2, 'Puzzle Medium #3', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(8, 3, 'Puzzle Hard #1', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(9, 3, 'Puzzle Hard #2', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081'),
(10, 3, 'Puzzle Hard #3', 'ACTIVE', TIMESTAMP '2026-04-30 12:59:53.370081');

-- PUZZLE_CONSTRAINTS
INSERT INTO puzzle_constraints (
    id, puzzle_id,
    circle_sum_1, circle_sum_2, circle_sum_3, circle_sum_4,
    color_zone_map, circle_cell_map, color_zone_sum_map
) VALUES
(1, 1, 15, 18, 21, 20,
 '{"RED":[0,1],"BLUE":[2,3,4,6],"GREEN":[5,7,8]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":8,"BLUE":22,"GREEN":15}'
),
(2, 2, 23, 17, 24, 20,
 '{"RED":[4,7],"BLUE":[1,2,5],"GREEN":[0,3,6,8]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":15,"BLUE":9,"GREEN":21}'
),
(3, 3, 12, 17, 23, 18,
 '{"RED":[3,6,7],"BLUE":[4,5],"GREEN":[0,1,2,8]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":21,"BLUE":7,"GREEN":17}'
),
(4, 4, 25, 22, 14, 17,
 '{"RED":[2,4,5,6],"BLUE":[0,1,3],"GREEN":[7,8]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":15,"BLUE":22,"GREEN":8}'
),
(5, 5, 13, 21, 16, 21,
 '{"RED":[4,7],"BLUE":[2,5,6,8],"GREEN":[0,1,3]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":6,"BLUE":30,"GREEN":9}'
),
(6, 6, 22, 21, 25, 17,
 '{"RED":[4,5],"BLUE":[2,6,7,8],"GREEN":[0,1,3]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":10,"BLUE":20,"GREEN":15}'
),
(7, 7, 21, 11, 23, 20,
 '{"RED":[2,5],"BLUE":[4,6,7,8],"GREEN":[0,1,3]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":5,"BLUE":24,"GREEN":16}'
),
(8, 8, 14, 25, 17, 22,
 '{"RED":[1,2,4,7],"BLUE":[5,8],"GREEN":[0,3,6]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":19,"BLUE":13,"GREEN":13}'
),
(9, 9, 22, 19, 16, 19,
 '{"RED":[5,6,7,8],"BLUE":[0,2,3],"GREEN":[1,4]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":22,"BLUE":10,"GREEN":13}'
),
(10, 10, 15, 20, 24, 20,
 '{"RED":[6,7],"BLUE":[0,5,8],"GREEN":[1,2,3,4]}',
 '{"C1":[0,1,3,4],"C2":[1,2,4,5],"C3":[3,4,6,7],"C4":[4,5,7,8]}',
 '{"RED":15,"BLUE":8,"GREEN":22}'
);

-- Note: les `ui_assets` seront rechargés via `UiAssetBootstrap` depuis le dossier `images/`
-- si présent au runtime (background.png, hourglass.png, howtoicon.png).

