package com.unicamp.tessera.repository;

import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Configuration
public class CustomRepositoryImpl implements CustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getPacienteWithMedicamentos(String id){
        Query query = entityManager.createNativeQuery("SELECT \n" +
                "    P.NOME as NOME_PACIENTE, M.NOME_COMERCIAL, M.PRINCIPIO_ATIVO, C.DATA_INICIO, C.DATA_FIM, C.DOSAGEM, C.PERIODICIDADE\n" +
                "FROM CONSOME C\n" +
                "INNER JOIN PACIENTE P\n" +
                "    ON P.ID = C.ID_PACIENTE\n" +
                "INNER JOIN MEDICAMENTO M\n" +
                "    ON M.ID = C.ID_MEDICAMENTO\n" +
                "WHERE P.ID = '" + id + "';");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getPacienteWithMedicamentosAtuais(String id) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    P.NOME as NOME_PACIENTE, M.NOME_COMERCIAL, M.PRINCIPIO_ATIVO, C.DATA_INICIO, C.DATA_FIM, C.DOSAGEM, C.PERIODICIDADE\n" +
                "FROM CONSOME C\n" +
                "INNER JOIN PACIENTE P\n" +
                "    ON P.ID = C.ID_PACIENTE\n" +
                "INNER JOIN MEDICAMENTO M\n" +
                "    ON M.ID = C.ID_MEDICAMENTO\n" +
                "WHERE P.ID = '"+ id + "' AND C.DATA_FIM IS NULL;");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getPacientesWithComorbidades(String id) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    P.NOME as NOME_PACIENTE, C.NOME as NOME_COMORBIDADE, PC.DATA_INICIO, PC.DATA_FIM\n" +
                "FROM POSSUI PC\n" +
                "INNER JOIN PACIENTE P\n" +
                "    ON P.ID = PC.ID_PACIENTE\n" +
                "INNER JOIN COMORBIDADE C\n" +
                "    ON C.ID = PC.ID_COMORBIDADE\n" +
                "WHERE P.ID = '" + id + "';");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getPacientesWithComorbidadesAtuais(String id) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    P.NOME as NOME_PACIENTE, C.NOME as NOME_COMORBIDADE, PC.DATA_INICIO, PC.DATA_FIM\n" +
                "FROM POSSUI PC\n" +
                "INNER JOIN PACIENTE P\n" +
                "    ON P.ID = PC.ID_PACIENTE\n" +
                "INNER JOIN COMORBIDADE C\n" +
                "    ON C.ID = PC.ID_COMORBIDADE\n" +
                "WHERE P.ID = '" + id + "' AND PC.DATA_FIM IS NULL ;");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getMedicoWithEspecializacoes(String id) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    M.NOME as NOME_MEDICO, E.NOME as NOME_ESPECIALIZACAO\n" +
                "FROM ESPECIALIZA EM\n" +
                "INNER JOIN MEDICO M\n" +
                "    ON M.ID = EM.ID_MEDICO\n" +
                "INNER JOIN ESPECIALIZACAO E\n" +
                "    ON E.ID = EM.ID_ESPECIALIZACAO\n" +
                "WHERE M.ID = '" + id + "';");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getConsultasOfPacienteWhereStatusIsAgendada(String id) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    P.NOME as NOME_PACIENTE, M.NOME as NOME_MEDICO, C.DATA_HORARIO, C.URL_VIDEO_CHAMADA, CONCAT(E.RUA, ' ', E.NUMERO, CASE WHEN E.BAIRRO IS NOT NULL THEN CONCAT(' - ', E.BAIRRO) ELSE '' END, ', ', E.CIDADE, ' - ', E.ESTADO) as ENDERECO\n" +
                "FROM CONSULTA C\n" +
                "INNER JOIN PACIENTE P\n" +
                "    ON P.ID = C.ID_PACIENTE\n" +
                "INNER JOIN MEDICO M\n" +
                "    ON M.ID = C.ID_MEDICO\n" +
                "LEFT JOIN ENDERECO E\n" +
                "    ON E.ID = C.ID_ENDERECO\n" +
                "WHERE P.ID = '" + id + "' AND C.STATUS = 'AGENDADO';");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getConsultasOfMedicoWhereStatusIsAgendada(String id) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    P.NOME as NOME_PACIENTE, M.NOME as NOME_MEDICO, C.DATA_HORARIO, C.URL_VIDEO_CHAMADA, CONCAT(E.RUA, ' ', E.NUMERO, CASE WHEN E.BAIRRO IS NOT NULL THEN CONCAT(' - ', E.BAIRRO) ELSE '' END, ', ', E.CIDADE, ' - ', E.ESTADO) as ENDERECO\n" +
                "FROM CONSULTA C\n" +
                "INNER JOIN PACIENTE P\n" +
                "    ON P.ID = C.ID_PACIENTE\n" +
                "INNER JOIN MEDICO M\n" +
                "    ON M.ID = C.ID_MEDICO\n" +
                "LEFT JOIN ENDERECO E\n" +
                "    ON E.ID = C.ID_ENDERECO\n" +
                "WHERE M.ID = '" + id + "' AND C.STATUS = 'AGENDADO';");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getClinicasWithEnderecos() {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    C.id as id_clinica, C.nome as nome_clinica, C.telefone, C.cnpj,\n" +
                "    E.id as id_endereco, E.cep, E.cidade, E.estado, E.bairro, E.rua, E.numero, E.complemento\n" +
                "FROM CLINICA C\n" +
                "INNER JOIN ENDERECO E\n" +
                "    ON E.ID = C.ID_ENDERECO;");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getDisponibilidadeMedicoBetweenDates(String id, String nomeCidade, String ufEstado, String dataInicio, String dataFim) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    M.NOME as MEDICO, D.DATA_INICIO, D.DATA_FIM, D.HORA_INICIO, D.HORA_FIM, D.REPETIR_TODA_SEMANA, CONCAT(E.RUA, ' ', E.NUMERO, CASE WHEN E.BAIRRO IS NOT NULL THEN CONCAT(' - ', E.BAIRRO) ELSE '' END, ', ', E.CIDADE, ' - ', E.ESTADO) as ENDERECO\n" +
                "FROM DISPONIBILIDADE D\n" +
                "INNER JOIN MEDICO M\n" +
                "    ON M.ID = '" + id +"'\n" +
                "INNER JOIN CLINICA C\n" +
                "    ON C.ID = D.ID_CLINICA\n" +
                "INNER JOIN ENDERECO E\n" +
                "    ON E.ID = C.ID_ENDERECO\n" +
                "WHERE \n" +
                "    E.CIDADE = '" + nomeCidade + "' \n" +
                "    AND E.ESTADO = '" + ufEstado + "' \n" +
                "    AND D.DATA_INICIO <= '" + dataInicio + "' \n" +
                "    AND ((D.DATA_FIM >= current_timestamp AND D.DATA_FIM <= '" + dataFim +
                "') OR (D.DATA_FIM IS NULL AND D.REPETIR_TODA_SEMANA = TRUE));\n");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getDisponibilidadeMedicosEspecializaBetweenDates(String id, String nomeCidade, String ufEstado, String dataInicio, String dataFim) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    M.NOME as MEDICO, D.DATA_INICIO, D.DATA_FIM, D.HORA_INICIO, D.HORA_FIM, D.REPETIR_TODA_SEMANA, CONCAT(E.RUA, ' ', E.NUMERO, CASE WHEN E.BAIRRO IS NOT NULL THEN CONCAT(' - ', E.BAIRRO) ELSE '' END, ', ', E.CIDADE, ' - ', E.ESTADO) as ENDERECO\n" +
                "FROM DISPONIBILIDADE D\n" +
                "INNER JOIN MEDICO M\n" +
                "    ON M.ID = D.ID_MEDICO\n" +
                "INNER JOIN CLINICA C\n" +
                "    ON C.ID = D.ID_CLINICA\n" +
                "INNER JOIN ENDERECO E\n" +
                "    ON E.ID = C.ID_ENDERECO\n" +
                "INNER JOIN ESPECIALIZA EM\n" +
                "    ON EM.ID_MEDICO = M.ID\n" +
                "INNER JOIN ESPECIALIZACAO ES\n" +
                "    ON ES.ID = '"+ id +"'\n" +
                "WHERE \n" +
                "    E.CIDADE = '" + nomeCidade + "' \n" +
                "    AND E.ESTADO = '" + ufEstado + "' \n" +
                "    AND D.DATA_INICIO <= '" + dataInicio + "' \n" +
                "    AND ((D.DATA_FIM >= current_timestamp AND D.DATA_FIM <= '" + dataFim +
                "') OR (D.DATA_FIM IS NULL AND D.REPETIR_TODA_SEMANA = TRUE));\n");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getDisponibilidadesBetweenData(String nomeCidade, String ufEstado, String dataInicio, String dataFim) {
        Query query = entityManager.createNativeQuery("SELECT\n" +
                "    M.NOME as MEDICO, D.DATA_INICIO, D.DATA_FIM, D.HORA_INICIO, D.HORA_FIM," +
                " D.REPETIR_TODA_SEMANA, CONCAT(E.RUA, ' ', E.NUMERO, CASE WHEN E.BAIRRO IS" +
                " NOT NULL THEN CONCAT(' - ', E.BAIRRO) ELSE '' END, ', ', E.CIDADE, ' - ', E.ESTADO) as ENDERECO\n" +
                "FROM DISPONIBILIDADE D\n" +
                "INNER JOIN MEDICO M    \n" +
                "    ON M.ID = D.ID_MEDICO\n" +
                "INNER JOIN CLINICA C\n" +
                "    ON C.ID = D.ID_CLINICA\n" +
                "INNER JOIN ENDERECO E\n" +
                "    ON E.ID = C.ID_ENDERECO\n" +
                "WHERE\n" +
                "    E.CIDADE = '" + nomeCidade + "' \n" +
                "    AND E.ESTADO = '" + ufEstado + "' \n" +
                "    AND D.DATA_INICIO <= '" + dataInicio + "' \n" +
                "    AND ((D.DATA_FIM >= current_timestamp AND D.DATA_FIM <= '" + dataFim +
                "') OR (D.DATA_FIM IS NULL AND D.REPETIR_TODA_SEMANA = TRUE));\n");
        return query.getResultList();
    }
}
