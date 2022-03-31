
INSERT INTO USUARIO(nome, email, senha) VALUES('igor', 'igor.alves@email.com', '$2a$10$7kE0nvihXHh2rrLZDwsNL.OUApjk.cLeaU1zOkr/hoeNuqj.tZyF6');



INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 1, '2021-08-18');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 4, '2021-08-19');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 3, '2021-08-20');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 4, '2021-08-21');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('MOINHOS', 5, '2021-08-22');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 6, '2021-08-23');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 7, '2021-08-24');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 8, '2021-08-25');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 9, '2021-08-26');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('CARDIOLOGIA', 10, '2021-08-27');
INSERT INTO UNIDADE_SAUDE(nome, numero_pacientes, data) VALUES('MOINHOS', 11, '2021-08-10');


INSERT INTO ATENDIMENTO(descricao, sem_possibilidade_contagio, relacionado_com_pandemia, tempo_atendimento, data, id_unidade_saude) VALUES('ok', false, true, 10, '1993-08-18', 1);
INSERT INTO ATENDIMENTO(descricao, sem_possibilidade_contagio, relacionado_com_pandemia, tempo_atendimento, data, id_unidade_saude) VALUES('bom', false, true, 20, '1993-08-18', 1);
INSERT INTO ATENDIMENTO(descricao, sem_possibilidade_contagio, relacionado_com_pandemia, tempo_atendimento, data, id_unidade_saude) VALUES('ótimo', false, true, 13, '1993-08-18', 1);
INSERT INTO ATENDIMENTO(descricao, sem_possibilidade_contagio, relacionado_com_pandemia, tempo_atendimento, data, id_unidade_saude) VALUES('ok', true, true, 14, '1993-08-18', 1);
INSERT INTO ATENDIMENTO(descricao, sem_possibilidade_contagio, relacionado_com_pandemia, tempo_atendimento, data, id_unidade_saude) VALUES('ok', true, false, 15, '1993-08-18', 5);
INSERT INTO ATENDIMENTO(descricao, sem_possibilidade_contagio, relacionado_com_pandemia, tempo_atendimento, data, id_unidade_saude) VALUES('mais-ou-menos', false, false, 16, '1993-08-18', 6);


INSERT INTO ETAPA(numero, descricao, id_atendimento) VALUES(1, 'Concluída', 1);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(2, 'Saudável', 1);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(3, 'Não saudável', 1);

INSERT INTO ETAPA(numero, descricao, id_atendimento) VALUES(1, 'Concluída', 2);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(2, 'Saudável', 2);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(3, 'Não saudável', 2);

INSERT INTO ETAPA(numero, descricao, id_atendimento) VALUES(1, 'Concluída', 3);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(2, 'Saudável', 3);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(3, 'Não saudável', 3);

INSERT INTO ETAPA(numero, descricao, id_atendimento) VALUES(1, 'Concluída', 4);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(2, 'Saudável', 4);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(3, 'Não saudável', 4);

INSERT INTO ETAPA(numero, descricao, id_atendimento) VALUES(1, 'Concluída', 5);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(2, 'Saudável', 5);
INSERT INTO TESTE(numero, resultado, id_atendimento) VALUES(3, 'Não saudável', 5);