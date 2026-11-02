-- ============================================================================

-- ORDEM: entidades fortes (independentes) -> entidades fracas (dependentes)

-- Nível 0: não dependem de ninguém (PK própria, sem FK)

-- Nível 1: dependem só do Nível 0

-- Nível 2: dependem do Nível 0 e/ou 1

-- Nível 3+: dependem em cadeia (dependem de quem já depende de outro)

-- ============================================================================



-- ============================================================================

-- NÍVEL 0 — ENTIDADES FORTES (sem FK, existem por si só)

-- ============================================================================



-- ----------------------------------------------------------------------------

-- 1. T_CLYVOJ_RESPONSAVEL

-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_RESPONSAVEL (nm_responsavel, cpf_responsavel, nr_telefone_responsavel, ds_email) VALUES ('Marina Duarte', '321.654.987-11', '(41) 98888-1234', 'marina.duarte@email.com');

INSERT INTO T_CLYVOJ_RESPONSAVEL (nm_responsavel, cpf_responsavel, nr_telefone_responsavel, ds_email) VALUES ('Rafael Nunes', '654.321.098-22', '(51) 97777-2345', 'rafael.nunes@email.com');

INSERT INTO T_CLYVOJ_RESPONSAVEL (nm_responsavel, cpf_responsavel, nr_telefone_responsavel, ds_email) VALUES ('Larissa Prado', '789.123.456-33', '(61) 96666-3456', 'larissa.prado@email.com');



-- ----------------------------------------------------------------------------
-- 2. T_CLYVOJ_VET
-- Todo veterinário é vinculado a pelo menos uma clínica
-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_VET (nm_vet, cpf_vet, crmv_vet, email, senha)
VALUES ('Dra. Camila Torres', '222.333.444-55', 'PR11223', 'camila@email.com', 'senhaForte1');

INSERT INTO T_CLYVOJ_VET (nm_vet, cpf_vet, crmv_vet, email, senha)
VALUES ('Dr. Bruno Farias', '333.444.555-66', 'RS44556', 'bruno@email.com', 'senhaForte2');

INSERT INTO T_CLYVOJ_VET (nm_vet, cpf_vet, crmv_vet, email, senha)
VALUES ('Dra. Patricia Gomes', '444.555.666-77', 'SC77889', 'patricia@email.com', 'senhaForte3');


-- ----------------------------------------------------------------------------
-- 3. T_CLYVOJ_CLINICA
-- Clínicas possuem login próprio
-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_CLINICA (cnpj_clinica, nm_clinica, email, senha, st_contrato)
VALUES ('23.456.789/0001-01', 'Clinica Amigo Fiel', 'contato@amigofiel.com', 'senhaForte4', 'ATIVO');

INSERT INTO T_CLYVOJ_CLINICA (cnpj_clinica, nm_clinica, email, senha, st_contrato)
VALUES ('34.567.890/0001-12', 'PetSaude 24 Horas', 'contato@petsaude24h.com', 'senhaForte5', 'ATIVO');


-- ============================================================================
-- USUARIO
-- Login das clínicas
-- ============================================================================

INSERT INTO T_CLYVOJ_USUARIO (username, senha, tipo, id_clinica, id_veterinario)
VALUES (
    'contato@amigofiel.com',
    '$2b$10$.0Ww5rDfJKhz2Ge5ab8VTOkgwuMGfRGtpQda5HtRVIo4dtyTSP3X2',
    'CLINICA',
    1,
    NULL
);

INSERT INTO T_CLYVOJ_USUARIO (username, senha, tipo, id_clinica, id_veterinario)
VALUES (
    'contato@petsaude24h.com',
    '$2b$10$.0Ww5rDfJKhz2Ge5ab8VTOkgwuMGfRGtpQda5HtRVIo4dtyTSP3X2',
    'CLINICA',
    2,
    NULL
);


-- ============================================================================
-- USUARIO
-- Login dos veterinários
-- ============================================================================

INSERT INTO T_CLYVOJ_USUARIO (username, senha, tipo, id_clinica, id_veterinario)
VALUES (
    'camila@email.com',
    '$2b$10$.0Ww5rDfJKhz2Ge5ab8VTOkgwuMGfRGtpQda5HtRVIo4dtyTSP3X2',
    'VETERINARIO',
    NULL,
    1
);

INSERT INTO T_CLYVOJ_USUARIO (username, senha, tipo, id_clinica, id_veterinario)
VALUES (
    'bruno@email.com',
    '$2b$10$.0Ww5rDfJKhz2Ge5ab8VTOkgwuMGfRGtpQda5HtRVIo4dtyTSP3X2',
    'VETERINARIO',
    NULL,
    2
);

INSERT INTO T_CLYVOJ_USUARIO (username, senha, tipo, id_clinica, id_veterinario)
VALUES (
    'patricia@email.com',
    '$2b$10$.0Ww5rDfJKhz2Ge5ab8VTOkgwuMGfRGtpQda5HtRVIo4dtyTSP3X2',
    'VETERINARIO',
    NULL,
    3
);
-- ============================================================================

-- NÍVEL 1 — dependem de UMA entidade forte (1 FK)

-- ============================================================================



-- ----------------------------------------------------------------------------

-- 4. T_CLYVOJ_ANIMAL (depende de RESPONSAVEL)

-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_ANIMAL (nm_animal, dt_nascimento_animal, peso_animal, especie_animal, raca_animal, rg_animal, nr_microchip_animal, id_responsavel) VALUES ('Nina', DATE '2021-02-10', 9.40, 'Canina', 'Poodle', 'RG-334455', 'MC-1029384756', 1);

INSERT INTO T_CLYVOJ_ANIMAL (nm_animal, dt_nascimento_animal, peso_animal, especie_animal, raca_animal, rg_animal, nr_microchip_animal, id_responsavel) VALUES ('Fred', DATE '2023-09-05', 4.10, 'Felina', 'Persa', NULL, NULL, 1);

INSERT INTO T_CLYVOJ_ANIMAL (nm_animal, dt_nascimento_animal, peso_animal, especie_animal, raca_animal, rg_animal, nr_microchip_animal, id_responsavel) VALUES ('Trovao', DATE '2017-06-22', 850.75, 'Equina', 'Quarto de Milha', 'RG-887766', NULL, 2);

INSERT INTO T_CLYVOJ_ANIMAL (nm_animal, dt_nascimento_animal, peso_animal, especie_animal, raca_animal, rg_animal, nr_microchip_animal, id_responsavel) VALUES ('Amarelinho', DATE '2024-03-01', 0.08, 'Ave', 'Canario Belga', NULL, 'MC-6677889900', 3);



-- ----------------------------------------------------------------------------

-- 5. T_CLYVOJ_ENDERECO_RESPONSAVEL (depende de RESPONSAVEL, relação 1:1)

-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_ENDERECO_RESPONSAVEL (pais, estado, cidade, bairro, logradouro, nr_rua, complemento, cep, id_responsavel) VALUES ('Brasil', 'PR', 'Curitiba', 'Batel', 'Rua Comendador Araujo', '500', 'Apto 21', '80420-000', 1);

INSERT INTO T_CLYVOJ_ENDERECO_RESPONSAVEL (pais, estado, cidade, bairro, logradouro, nr_rua, complemento, cep, id_responsavel) VALUES ('Brasil', 'RS', 'Porto Alegre', 'Moinhos de Vento', 'Rua Padre Chagas', '88', 'Casa', '90570-080', 2);

-- Responsável 3 fica propositalmente sem endereço, simulando cadastro incompleto



-- ----------------------------------------------------------------------------

-- 6. T_CLYVOJ_ENDERECO_CLINICA (depende de CLINICA, relação 1:N)

-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_ENDERECO_CLINICA (pais, estado, cidade, bairro, logradouro, nr_rua, complemento, cep, id_clinica) VALUES ('Brasil', 'PR', 'Curitiba', 'Centro', 'Rua XV de Novembro', '700', 'Loja 3', '80020-310', 1);

-- Filial da MESMA Clínica 1 -- demonstra a cardinalidade 1:N

INSERT INTO T_CLYVOJ_ENDERECO_CLINICA (pais, estado, cidade, bairro, logradouro, nr_rua, complemento, cep, id_clinica) VALUES ('Brasil', 'PR', 'Sao Jose dos Pinhais', 'Centro', 'Av. Rui Barbosa', '1200', 'Sala 5', '83005-100', 1);

INSERT INTO T_CLYVOJ_ENDERECO_CLINICA (pais, estado, cidade, bairro, logradouro, nr_rua, complemento, cep, id_clinica) VALUES ('Brasil', 'RS', 'Porto Alegre', 'Cidade Baixa', 'Av. Joao Pessoa', '250', 'Terreo', '90040-000', 2);



-- ============================================================================

-- NÍVEL 2 — dependem de DUAS entidades fortes, ou de uma entidade de Nível 1

-- ============================================================================



-- ----------------------------------------------------------------------------

-- 7. T_CLYVOJ_CLINICA_VET (depende de VET e CLINICA — vínculo agora sempre obrigatório)

-- ----------------------------------------------------------------------------

-- Clínica 1 (Amigo Fiel) possui 2 veterinários

INSERT INTO T_CLYVOJ_CLINICA_VET (id_veterinario, id_clinica) VALUES (1, 1); -- id 1: Camila @ Amigo Fiel

INSERT INTO T_CLYVOJ_CLINICA_VET (id_veterinario, id_clinica) VALUES (2, 1); -- id 2: Bruno @ Amigo Fiel



-- Clínica 2 (PetSaude 24h) possui 2 veterinários

INSERT INTO T_CLYVOJ_CLINICA_VET (id_veterinario, id_clinica) VALUES (1, 2); -- id 3: Camila @ PetSaude 24h (atua nas duas)

INSERT INTO T_CLYVOJ_CLINICA_VET (id_veterinario, id_clinica) VALUES (3, 2); -- id 4: Patricia @ PetSaude 24h



-- ----------------------------------------------------------------------------

-- 8. T_CLYVOJ_ENDERECO_ANIMAL (depende de ANIMAL, relação 1:1 opcional)

-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_ENDERECO_ANIMAL (pais, estado, cidade, bairro, logradouro, nr_rua, complemento, cep, id_animal) VALUES ('Brasil', 'PR', 'Curitiba', 'Batel', 'Rua Comendador Araujo', '500', 'Apto 21', '80420-000', 1);

INSERT INTO T_CLYVOJ_ENDERECO_ANIMAL (pais, estado, cidade, bairro, logradouro, nr_rua, complemento, cep, id_animal) VALUES ('Brasil', 'GO', 'Rio Verde', 'Zona Rural', 'Estrada da Fazenda Sao Jose', 'S/N KM 8', 'Sitio', '75901-970', 3);



-- ----------------------------------------------------------------------------

-- 9. T_CLYVOJ_VACINACAO (depende de ANIMAL)

-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_VACINACAO (nm_vacina, dt_vacinacao_prevista, dt_vacinacao_efetuada, st_vacinacao, id_animal) VALUES ('Raiva', DATE '2026-02-01', DATE '2026-02-01', 'EFETUADA', 1);

INSERT INTO T_CLYVOJ_VACINACAO (nm_vacina, dt_vacinacao_prevista, dt_vacinacao_efetuada, st_vacinacao, id_animal) VALUES ('V8', DATE '2026-10-15', NULL, 'PENDENTE', 1);

INSERT INTO T_CLYVOJ_VACINACAO (nm_vacina, dt_vacinacao_prevista, dt_vacinacao_efetuada, st_vacinacao, id_animal) VALUES ('Giardia', DATE '2026-05-01', NULL, 'ATRASADA', 2);

INSERT INTO T_CLYVOJ_VACINACAO (nm_vacina, dt_vacinacao_prevista, dt_vacinacao_efetuada, st_vacinacao, id_animal) VALUES ('Influenza Equina', DATE '2026-01-20', DATE '2026-01-22', 'EFETUADA', 3);

INSERT INTO T_CLYVOJ_VACINACAO (nm_vacina, dt_vacinacao_prevista, dt_vacinacao_efetuada, st_vacinacao, id_animal) VALUES ('Poliomavirus Aviario', DATE '2026-04-05', NULL, 'CANCELADA', 4);



-- ----------------------------------------------------------------------------

-- 10. T_CLYVOJ_CONSULTA (depende de CLINICA_VET e ANIMAL)

-- Referencia id_vet_cli (ClinicaVeterinario), não id_vet diretamente

-- ClinicaVeterinario gerados acima: id=1 (Camila@AmigoFiel), id=2 (Bruno@AmigoFiel),

--                                   id=3 (Camila@PetSaude), id=4 (Patricia@PetSaude)

-- ----------------------------------------------------------------------------

-- Consulta já realizada para o Animal 1 (Nina), atendida pela Camila na Amigo Fiel

INSERT INTO T_CLYVOJ_CONSULTA (historico_consulta, st_consulta, dt_consulta, id_vet_cli, id_animal) VALUES ('Avaliacao de rotina, animal saudavel', 'PASSADA', DATE '2026-04-12', 1, 1);



-- Consulta em andamento para o Animal 3 (Trovao), atendida pelo Bruno na Amigo Fiel

INSERT INTO T_CLYVOJ_CONSULTA (historico_consulta, st_consulta, dt_consulta, id_vet_cli, id_animal) VALUES ('Avaliacao de claudicacao em membro dianteiro', 'EM_ANDAMENTO', DATE '2026-08-20', 2, 3);



-- Consulta futura agendada para o Animal 2 (Fred), atendida pela Camila na Amigo Fiel

INSERT INTO T_CLYVOJ_CONSULTA (historico_consulta, st_consulta, dt_consulta, id_vet_cli, id_animal) VALUES ('Retorno para avaliacao dermatologica', 'FUTURA', DATE '2026-09-10', 1, 2);



-- Consulta cancelada pelo tutor para o Animal 4 (Amarelinho), atendida pela Patricia na PetSaude 24h

INSERT INTO T_CLYVOJ_CONSULTA (historico_consulta, st_consulta, dt_consulta, id_vet_cli, id_animal) VALUES ('Consulta de rotina cancelada pelo tutor', 'CANCELADA', DATE '2026-07-15', 4, 4);



-- ============================================================================

-- NÍVEL 3 — dependem de uma entidade que já depende de outras (2º grau)

-- ============================================================================



-- ----------------------------------------------------------------------------

-- 11. T_CLYVOJ_PRESCRICAO (depende de CONSULTA)

-- Regra: dt_emissao é sempre hoje/passado, dt_expiracao é sempre futura

-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_PRESCRICAO (dt_emissao, dt_expiracao, observacoes_gerais, id_consulta) VALUES (DATE '2026-04-12', DATE '2026-09-26', 'Administrar apos as refeicoes, retornar em 14 dias', 1);

INSERT INTO T_CLYVOJ_PRESCRICAO (dt_emissao, dt_expiracao, observacoes_gerais, id_consulta) VALUES (DATE '2026-08-20', DATE '2026-09-30', NULL, 2);



-- ============================================================================

-- NÍVEL 4 — entidade mais fraca da cadeia (depende de PRESCRICAO)

-- ============================================================================



-- ----------------------------------------------------------------------------

-- 12. T_CLYVOJ_MEDICAMENTO (depende de PRESCRICAO)

-- ----------------------------------------------------------------------------

INSERT INTO T_CLYVOJ_MEDICAMENTO (nm_medicamento, qt_medicamento, qt_dias, vl_dose, un_dose, qt_frequencia, un_frequencia, forma_farmaceutica, id_prescricao) VALUES ('Amoxicilina', 20, 10, 250.00, 'mg', 12, 'horas', 'SOLIDA', 1);

INSERT INTO T_CLYVOJ_MEDICAMENTO (nm_medicamento, qt_medicamento, qt_dias, vl_dose, un_dose, qt_frequencia, un_frequencia, forma_farmaceutica, id_prescricao) VALUES ('Meloxicam Suspensao Oral', 1, 5, 0.10, 'mg/kg', 24, 'horas', 'LIQUIDA', 1);

INSERT INTO T_CLYVOJ_MEDICAMENTO (nm_medicamento, qt_medicamento, qt_dias, vl_dose, un_dose, qt_frequencia, un_frequencia, forma_farmaceutica, id_prescricao) VALUES ('Pomada Cicatrizante', 1, 7, 2.00, 'cm', 8, 'horas', 'SEMI_SOLIDA', 2);