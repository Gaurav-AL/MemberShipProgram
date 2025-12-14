CREATE TABLE plans (
    id SERIAL PRIMARY KEY,
    plan_type VARCHAR(50) NOT NULL,
    plan_tier VARCHAR(50) NOT NULL,
    plan_price INT NOT NULL
);

CREATE TABLE plan_benefits (
    id SERIAL PRIMARY KEY,
    plan_id INT REFERENCES plans(id),
    item_name VARCHAR(255),
    discount_percent FLOAT
);

CREATE TABLE user_membership_info (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id BIGINT NOT NULL,

    member_status VARCHAR(50) NOT NULL,
    member_tier   VARCHAR(50) NOT NULL,
    plan_tier     VARCHAR(50) NOT NULL,
    plan_type     VARCHAR(50) NOT NULL,

    expiry_date   DATE,
    last_active   TIMESTAMP NOT NULL
);


CREATE TABLE membership_transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id BIGINT NOT NULL,
    from_tier VARCHAR(30) NOT NULL,
    to_tier   VARCHAR(30) NOT NULL,
    amount_paid INTEGER NOT NULL,
    plan_type VARCHAR(30) NOT NULL,
    transaction_reason VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE tier_benefits (
    id SERIAL PRIMARY KEY,
    member_tier VARCHAR(50),
    benefit VARCHAR(255)
);
