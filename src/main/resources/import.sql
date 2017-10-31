
INSERT INTO "PRODUCTS" (ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, CREATED_DATE) VALUES('prod123', 'Product1','Description1', TO_DATE('2017-02-11', 'yyyy-mm-dd'));
INSERT INTO "PRODUCTS" (ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, CREATED_DATE) VALUES('prod124', 'Product2','Description2', TO_DATE('2017-02-12', 'yyyy-mm-dd'));
INSERT INTO "PRODUCTS" (ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, CREATED_DATE) VALUES('prod125', 'Product3','Description3', TO_DATE('2017-02-13', 'yyyy-mm-dd'));

INSERT INTO "PRODUCT_EVENT_STORE" (ID, PRODUCT_ID, EVENT_TYPE) VALUES(1, 'prod123','INSERT');
INSERT INTO "PRODUCT_EVENT_STORE" (ID, PRODUCT_ID, EVENT_TYPE) VALUES(2, 'prod123','UPDATE');
INSERT INTO "PRODUCT_EVENT_STORE" (ID, PRODUCT_ID, EVENT_TYPE) VALUES(3, 'prod123','UPDATE');
INSERT INTO "PRODUCT_EVENT_STORE" (ID, PRODUCT_ID, EVENT_TYPE) VALUES(4, 'prod124','INSERT');
INSERT INTO "PRODUCT_EVENT_STORE" (ID, PRODUCT_ID, EVENT_TYPE) VALUES(5, 'prod124','UPDATE');
INSERT INTO "PRODUCT_EVENT_STORE" (ID, PRODUCT_ID, EVENT_TYPE) VALUES(6, 'prod124','DELETE');
INSERT INTO "PRODUCT_EVENT_STORE" (ID, PRODUCT_ID, EVENT_TYPE) VALUES(7, 'prod125','INSERT');
INSERT INTO "PRODUCT_EVENT_STORE" (ID, PRODUCT_ID, EVENT_TYPE) VALUES(8, 'prod125','UPDATE');