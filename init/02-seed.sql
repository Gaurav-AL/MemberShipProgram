INSERT INTO plans(plan_type, plan_tier, plan_price) VALUES
('MONTHLY', 'BASIC' , 50),('QUARTERLY', 'BASIC' , 190),('YEARLY', 'BASIC' , 540) , ('MONTHLY', 'PREMIUM', 100),('QUARTERLY', 'PREMIUM',380),('YEARLY', 'PREMIUM' , 1100);

INSERT INTO plan_benefits(plan_id, item_name, discount_percent) VALUES
(1, 'Electronics', 5),
(1, 'Fashion', 10),
(2, 'Groceries', 8),
(3, 'All Items', 15);

INSERT INTO tier_benefits(member_tier, benefit) VALUES
('SILVER', 'Free Delivery'),
('GOLD', '5% Extra Discount'),
('PLATINUM', '10% Extra Discount'),
('PLATINUM', 'Priority Support');

INSERT INTO user_membership_info(id, user_id, member_status, member_tier, plan_tier,plan_type ,expiry_date,last_active)
VALUES (gen_random_uuid(), 1, 'NEW_SUBSCRIBER', 'SILVER', 'FREE' ,'MONTHLY',CURRENT_DATE , CURRENT_TIMESTAMP);
