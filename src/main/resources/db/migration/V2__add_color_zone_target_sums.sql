ALTER TABLE puzzle_constraints
    ADD COLUMN color_zone_sum_map CLOB;

UPDATE puzzle_constraints
SET color_zone_sum_map = '{"RED":6,"BLUE":13,"GREEN":17,"YELLOW":9}'
WHERE puzzle_id = (SELECT id FROM puzzles WHERE name = 'Puzzle Easy #1');

UPDATE puzzle_constraints
SET color_zone_sum_map = '{"RED":8,"BLUE":8,"GREEN":19,"YELLOW":5}'
WHERE puzzle_id = (SELECT id FROM puzzles WHERE name = 'Puzzle Medium #1');

UPDATE puzzle_constraints
SET color_zone_sum_map = '{"RED":15,"BLUE":12,"GREEN":18}'
WHERE puzzle_id = (SELECT id FROM puzzles WHERE name = 'Puzzle Hard #1');

ALTER TABLE puzzle_constraints
    ALTER COLUMN color_zone_sum_map SET NOT NULL;
