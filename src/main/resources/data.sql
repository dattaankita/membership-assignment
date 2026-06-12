INSERT INTO membership_plan(name,duration,price)
VALUES ('Monthly','MONTHLY',199);

INSERT INTO membership_plan(name,duration,price)
VALUES ('Quarterly','QUARTERLY',499);

INSERT INTO membership_plan(name,duration,price)
VALUES ('Yearly','YEARLY',1499);

INSERT INTO membership_tier
(name,priority,min_order_count,min_order_value,cohort)
VALUES
('Silver',1,0,0,'ALL');

INSERT INTO membership_tier
(name,priority,min_order_count,min_order_value,cohort)
VALUES
('Gold',2,10,5000,'PREMIUM');

INSERT INTO membership_tier
(name,priority,min_order_count,min_order_value,cohort)
VALUES
('Platinum',3,20,10000,'VIP');

INSERT INTO tier_benefit
(benefit_name,benefit_value,tier_id)
VALUES
('FREE_DELIVERY','true',1);

INSERT INTO tier_benefit
(benefit_name,benefit_value,tier_id)
VALUES
('DISCOUNT_PERCENT','5',1);

INSERT INTO tier_benefit
(benefit_name,benefit_value,tier_id)
VALUES
('DISCOUNT_PERCENT','10',2);

INSERT INTO tier_benefit
(benefit_name,benefit_value,tier_id)
VALUES
('EARLY_ACCESS','true',2);

INSERT INTO tier_benefit
(benefit_name,benefit_value,tier_id)
VALUES
('PRIORITY_SUPPORT','true',3);