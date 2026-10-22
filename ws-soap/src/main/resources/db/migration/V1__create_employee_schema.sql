CREATE TABLE IF NOT EXISTS role (
    id BIGINT NOT NULL AUTO_INCREMENT,
    rol VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_rol (rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS type_document (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(3) NOT NULL,
    document VARCHAR(40) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_type_document_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS person (
    id BIGINT NOT NULL AUTO_INCREMENT,
    names VARCHAR(40),
    last_names VARCHAR(40),
    dni VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    type_document_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_person_dni (dni),
    CONSTRAINT fk_person_type_document FOREIGN KEY (type_document_id) REFERENCES type_document (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS employee (
    id BIGINT NOT NULL AUTO_INCREMENT,
    person_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    salary DOUBLE,
    date_vinculation DATE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_employee_person (person_id),
    CONSTRAINT fk_employee_person FOREIGN KEY (person_id) REFERENCES person (id),
    CONSTRAINT fk_employee_role FOREIGN KEY (role_id) REFERENCES role (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
