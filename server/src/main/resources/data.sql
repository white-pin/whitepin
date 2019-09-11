------ INSERT admin
INSERT INTO wp_user ( ci
                    , di
                    , email
                    , name
                    , password
                    , user_token
                    , phone_number
                    , use_yn)
VALUES ( 'ci'
       , 'di'
       , 'admin'
       , 'admin'
       , '$2a$10$Q8cgIoqLvufVIkYZLzfi7O6rRi9eNn2/18APAmzRbW9rsA921MJuG'
       , 'aa'
       , '000-0000-0000'
       , 'Y')
ON DUPLICATE KEY
UPDATE
    wp_user.ci = wp_user.ci
    , wp_user.di = wp_user.di
    , wp_user.email = wp_user.email
    , wp_user.name = wp_user.name
    , wp_user.password = wp_user.password
    , wp_user.user_token = wp_user.user_token
    , wp_user.phone_number = wp_user.phone_number
    , wp_user.use_yn = wp_user.use_yn;

------ INSERT user
INSERT INTO wp_user ( ci
                    , di
                    , email
                    , name
                    , password
                    , user_token
                    , phone_number
                    , use_yn)
VALUES ( 'ci'
       , 'di'
       , 'user'
       , 'user'
       , '$2a$10$xaRYL0HbgsM8AH.f1lYnEuUmuqDHHJCc9fpC/F/.W8qHCmnXnd.Bq'
       , 'bb'
       , '000-0000-0000'
       , 'Y')
ON DUPLICATE KEY
UPDATE
    wp_user.ci = wp_user.ci
    , wp_user.di = wp_user.di
    , wp_user.email = wp_user.email
    , wp_user.name = wp_user.name
    , wp_user.password = wp_user.password
    , wp_user.user_token = wp_user.user_token
    , wp_user.phone_number = wp_user.phone_number
    , wp_user.use_yn = wp_user.use_yn;

------ INSERT role-admin/user
INSERT INTO wp_role ( role ) VALUES ( 'admin' )
ON DUPLICATE KEY
UPDATE wp_role.role = wp_role.role
;

INSERT INTO wp_role ( role ) VALUES ( 'user' )
ON DUPLICATE KEY
UPDATE wp_role.role = wp_role.role
;


------ INSERT wp_user_role admin-admin/user-user
INSERT INTO wp_user_role ( user_id
                         , role_id)
VALUES (
        (SELECT wp_user.user_id FROM wp_user WHERE email = 'admin')
       , (SELECT wp_role.role_id FROM wp_role WHERE role = 'admin')
       )
ON DUPLICATE KEY
UPDATE
    wp_user_role.user_id = wp_user_role.user_id
    , wp_user_role.role_id = wp_user_role.role_id;

INSERT INTO wp_user_role ( user_id
                         , role_id)
VALUES (
        (SELECT wp_user.user_id FROM wp_user WHERE email = 'user')
       , (SELECT wp_role.role_id FROM wp_role WHERE role = 'user')
       )
ON DUPLICATE KEY
UPDATE
    wp_user_role.user_id = wp_user_role.user_id
    , wp_user_role.role_id = wp_user_role.role_id;


------ INSERT wp_partner
INSERT INTO wp_partner VALUE ('PTN0001', '에어비앤비')
ON DUPLICATE KEY
UPDATE
    wp_partner.partner_code = wp_partner.partner_code
    , wp_partner.partner_name = wp_partner.partner_name;

INSERT INTO wp_partner VALUE ('PTN0002', '번개장터')
ON DUPLICATE KEY
UPDATE
    wp_partner.partner_code = wp_partner.partner_code
    , wp_partner.partner_name = wp_partner.partner_name;

INSERT INTO wp_partner VALUE ('PTN0003', '쏘카')
ON DUPLICATE KEY
UPDATE
    wp_partner.partner_code = wp_partner.partner_code
    , wp_partner.partner_name = wp_partner.partner_name;

INSERT INTO wp_partner VALUE ('PTN0004', '당근마켓')
ON DUPLICATE KEY
UPDATE
    wp_partner.partner_code = wp_partner.partner_code
    , wp_partner.partner_name = wp_partner.partner_name;