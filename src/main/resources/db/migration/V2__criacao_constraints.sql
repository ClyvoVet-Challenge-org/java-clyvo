    alter table T_CLYVOJ_ANIMAL 
       add constraint UK_CJ_ANIMAL_NR_MCHIP_ANIMAL unique (nr_microchip_animal);
 
    alter table T_CLYVOJ_ANIMAL 
       add constraint UK_CJ_ANIMAL_RG_ANIMAL unique (rg_animal);
 
    alter table T_CLYVOJ_CLINICA 
       add constraint UK_CJ_CLINICA_CNPJ_CLINICA unique (cnpj_clinica);
 
    alter table T_CLYVOJ_CLINICA 
       add constraint UK_CJ_CLINICA_EMAIL unique (email);
 
    alter table T_CLYVOJ_ENDERECO_ANIMAL 
       add constraint UK_CJ_END_ANIMAL_ID_ANIMAL unique (id_animal);
 
    alter table T_CLYVOJ_ENDERECO_RESPONSAVEL 
       add constraint UK_CJ_END_RESP_ID_RESP unique (id_responsavel);
 
    alter table T_CLYVOJ_PRESCRICAO 
       add constraint UK_CJ_PRESC_ID_CONSULTA unique (id_consulta);
 
    alter table T_CLYVOJ_RESPONSAVEL 
       add constraint UK_CJ_RESP_CPF_RESP unique (cpf_responsavel);
 
    alter table T_CLYVOJ_VET 
       add constraint UK_CJ_VET_CPF_VET unique (cpf_vet);
 
    alter table T_CLYVOJ_VET 
       add constraint UK_CJ_VET_CRMV_VET unique (crmv_vet);
 
    alter table T_CLYVOJ_VET 
       add constraint UK_CJ_VET_EMAIL unique (email);
 
    alter table T_CLYVOJ_USUARIO 
       add constraint UK_CJ_USUARIO_ID_CLINICA unique (id_clinica);
 
    alter table T_CLYVOJ_USUARIO 
       add constraint UK_CJ_USUARIO_ID_VET unique (id_veterinario);
 
    alter table T_CLYVOJ_ANIMAL 
       add constraint FK_CJ_ANIMAL_ID_RESP 
       foreign key (id_responsavel) 
       references T_CLYVOJ_RESPONSAVEL (id_responsavel);
 
    alter table T_CLYVOJ_CLINICA_VET 
       add constraint FK_CJ_CLINICA_VET_ID_CLINICA 
       foreign key (id_clinica) 
       references T_CLYVOJ_CLINICA (id_clinica);
 
    alter table T_CLYVOJ_CLINICA_VET 
       add constraint FK_CJ_CLINICA_VET_ID_VET 
       foreign key (id_veterinario) 
       references T_CLYVOJ_VET (id_vet);
 
    alter table T_CLYVOJ_CONSULTA 
       add constraint FK_CJ_CONSULTA_ID_ANIMAL 
       foreign key (id_animal) 
       references T_CLYVOJ_ANIMAL (id_animal);
 
    alter table T_CLYVOJ_CONSULTA 
       add constraint FK_CJ_CONSULTA_ID_VET_CLI 
       foreign key (id_vet_cli) 
       references T_CLYVOJ_CLINICA_VET (id_clinica_vet);
 
    alter table T_CLYVOJ_ENDERECO_ANIMAL 
       add constraint FK_CJ_END_ANIMAL_ID_ANIMAL 
       foreign key (id_animal) 
       references T_CLYVOJ_ANIMAL (id_animal);
 
    alter table T_CLYVOJ_ENDERECO_CLINICA 
       add constraint FK_CJ_END_CLINICA_ID_CLINICA 
       foreign key (id_clinica) 
       references T_CLYVOJ_CLINICA (id_clinica);
 
    alter table T_CLYVOJ_ENDERECO_RESPONSAVEL 
       add constraint FK_CJ_END_RESP_ID_RESP 
       foreign key (id_responsavel) 
       references T_CLYVOJ_RESPONSAVEL (id_responsavel);
 
    alter table T_CLYVOJ_MEDICAMENTO 
       add constraint FK_CJ_MEDICAMENTO_ID_PRESC 
       foreign key (id_prescricao) 
       references T_CLYVOJ_PRESCRICAO (id_prescricao);
 
    alter table T_CLYVOJ_PRESCRICAO 
       add constraint FK_CJ_PRESC_ID_CONSULTA 
       foreign key (id_consulta) 
       references T_CLYVOJ_CONSULTA (id_consulta);
 
    alter table T_CLYVOJ_VACINACAO 
       add constraint FK_CJ_VACINACAO_ID_ANIMAL 
       foreign key (id_animal) 
       references T_CLYVOJ_ANIMAL (id_animal);
 
    alter table T_CLYVOJ_USUARIO 
       add constraint FK_CJ_USUARIO_ID_CLINICA 
       foreign key (id_clinica) 
       references T_CLYVOJ_CLINICA (id_clinica);
 
    alter table T_CLYVOJ_USUARIO 
       add constraint FK_CJ_USUARIO_ID_VET 
       foreign key (id_veterinario) 
       references T_CLYVOJ_VET (id_vet);
