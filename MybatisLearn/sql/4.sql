USE d_mybiteslearn;

CREATE TABLE tb_idcard
(
    id   INT PRIMARY KEY AUTO_INCREMENT, -- 主键，自增 ID
    code VARCHAR(18)                     -- 身份证号码，长度为18
);

CREATE TABLE tb_person
(
    id      INT PRIMARY KEY AUTO_INCREMENT,         -- 主键，自增 ID
    name    VARCHAR(20),                            -- 姓名，长度为20
    age     INT,                                    -- 年龄
    sex     VARCHAR(10),                            -- 性别，长度为10
    card_id INT,                                    -- 外键，关联 tb_idcard 表的 id
    FOREIGN KEY (card_id) REFERENCES tb_idcard (id) -- 设置外键关联
);

CREATE TABLE tb_user
(
    id       int(32) PRIMARY KEY AUTO_INCREMENT,
    username varchar(32),
    address  varchar(256)
);

CREATE TABLE tb_orders
(
    id      INT PRIMARY KEY AUTO_INCREMENT,       -- 订单ID
    number  VARCHAR(50) NOT NULL,                 -- 订单编号
    user_id INT,                                  -- 用户外键，关联到 tb_users 表
    FOREIGN KEY (user_id) REFERENCES tb_user (id) -- 外键约束
);

CREATE TABLE tb_product
(
    id    INT(32) PRIMARY KEY AUTO_INCREMENT,
    NAME  VARCHAR(32),
    price DOUBLE
);

create Table tb_ordersitem
(
    id         int primary key auto_increment,
    orders_id  int,
    product_id int
);

INSERT INTO tb_user
VALUES ('1', '小明', '北京');
INSERT INTO tb_user
VALUES ('2', '小1明', '北2京');
INSERT INTO tb_user
VALUES ('3', '小3明', '北3京');

INSERT INTO tb_idcard (code)
VALUES ('510821196605224121'),
       ('510821200502118521');

INSERT INTO tb_person (name, age, sex, card_id)
VALUES ('zhangsan', 18, 'male', 1), -- card_id 关联 tb_idcard 表中的第 1 条记录
       ('lisi', 19, 'female', 2); -- card_id 关联 tb_idcard 表中的第 2 条记录

INSERT INTO tb_orders (number, user_id)
VALUES ('ORD001', 1),
       ('ORD002', 1),
       ('ORD003', 2),
       ('ORD004', 2),
       ('ORD005', 2),
       ('ORD006', 3);

INSERT INTO tb_product
VALUES ('1', 'Java基础入门', 44.5),
       ('2', 'JavaEE', 43.5),
       ('3', 'Java基础入门2', 44.5);

INSERT INTO tb_ordersitem (orders_id, product_id)
VALUES ('1', '1'),
       ('1', '2'),
       ('2', '1'),
       ('2', '2');



