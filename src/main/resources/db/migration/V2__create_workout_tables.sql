CREATE TABLE IF NOT EXISTS exercises (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    category VARCHAR(30) NOT NULL,
    description VARCHAR(255) NULL,
    created_by UUID NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS  workout_plans (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS workout_exercises (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workout_plan_id UUID NOT NULL REFERENCES workout_plans(id),
    exercise_id UUID NOT NULL REFERENCES exercises(id),
    sets INTEGER NOT NULL,
    reps INTEGER NOT NULL,
    weight DECIMAL(6,2) NULL,
    rpe DECIMAL(3,1) NULL,
    order_index INTEGER NOT NULL,
    notes VARCHAR(255) NULL
);

CREATE TABLE IF NOT EXISTS workout_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    workout_plan_id UUID NOT NULL REFERENCES workout_plans(id),
    logged_at TIMESTAMP NOT NULL DEFAULT NOW(),
    notes TEXT NULL
);

CREATE TABLE IF NOT EXISTS workout_set_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workout_log_id UUID NOT NULL REFERENCES workout_logs(id),
    workout_exercise_id UUID NOT NULL REFERENCES workout_exercises(id),
    set_number INTEGER NOT NULL,
    reps_done INTEGER NOT NULL,
    weight_done DECIMAL(6,2) NULL,
    rpe DECIMAL(3,1) NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE
);