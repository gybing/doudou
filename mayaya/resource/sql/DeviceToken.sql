USE mayaya;
DROP TABLE IF EXISTS DeviceToken;
CREATE TABLE DeviceToken
(
    deviceTokenId		 VARCHAR(255) NOT NULL,
    userId               INT UNSIGNED NOT NULL, 
    active				 BOOL NOT NULL DEFAULT TRUE, 
    
    PRIMARY KEY (deviceTokenId),
    index idx_DeviceToken(userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
