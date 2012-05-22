USE doudou;
DROP TABLE IF EXISTS DeviceToken;
CREATE TABLE DeviceToken
(
    deviceTokenId		 VARCHAR(255) NOT NULL,
    userId               INT UNSIGNED NOT NULL, 
    
    PRIMARY KEY (deviceTokenId),
    index idx_DeviceToken(userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
