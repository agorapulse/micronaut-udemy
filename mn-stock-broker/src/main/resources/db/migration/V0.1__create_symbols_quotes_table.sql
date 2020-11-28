CREATE TABLE symbols
(
    value VARCHAR(255) NOT NULL,
    PRIMARY KEY (value)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE quotes
(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    symbol VARCHAR(255) NOT NULL,
    ask FLOAT NOT NULL,
    bid FLOAT NOT NULL,
    last_price FLOAT NOT NULL,
    volume FLOAT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_symbols_symbol_id FOREIGN KEY (symbol) REFERENCES symbols (value),
    CONSTRAINT last_price_is_positive CHECK (last_price > 0),
    CONSTRAINT volume_is_positive CHECK (volume > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
