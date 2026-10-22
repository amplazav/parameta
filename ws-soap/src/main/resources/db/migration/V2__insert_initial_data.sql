INSERT IGNORE INTO role (id, rol) VALUES
    (1, 'Desarrollador'),
    (2, 'Analista'),
    (3, 'Tester'),
    (4, 'Scrum Master'),
    (5, 'DevOps');

INSERT IGNORE INTO type_document (id, code, document) VALUES
    (1, 'CC', 'Cédula de Ciudadanía'),
    (2, 'TI', 'Tarjeta de Identidad'),
    (3, 'CE', 'Cédula de Extranjería');

INSERT IGNORE INTO person (id, names, last_names, type_document_id, dni, date_of_birth) VALUES
    (1, 'Juan', 'Pérez', 1, '100000001', '1990-01-01'),
    (2, 'María', 'González', 1, '100000002', '1988-03-15'),
    (3, 'Carlos', 'Ramírez', 2, '100000003', '1995-07-22'),
    (4, 'Luisa', 'Martínez', 3, '100000004', '1992-11-10'),
    (5, 'Andrés', 'López', 1, '100000005', '1985-06-05');
