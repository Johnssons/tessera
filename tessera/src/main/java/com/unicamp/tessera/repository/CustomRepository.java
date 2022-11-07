package com.unicamp.tessera.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomRepository {

    List<Object[]> getPacienteWithMedicamentos(String id);

    List<Object[]> getPacienteWithMedicamentosAtuais(String id);

    List<Object[]> getPacientesWithComorbidades(String id);

    List<Object[]> getPacientesWithComorbidadesAtuais(String id);

    List<Object[]> getMedicoWithEspecializacoes(String id);

    List<Object[]> getConsultasOfPacienteWhereStatusIsAgendada(String id);

    List<Object[]> getConsultasOfMedicoWhereStatusIsAgendada(String id);

    List<Object[]> getClinicasWithEnderecos();

    List<Object[]> getDisponibilidadeMedicoBetweenDates(String id, String nomeCidade,
                                                        String ufEstado, String dataInicio,
                                                        String dataFim);

    List<Object[]> getDisponibilidadeMedicosEspecializaBetweenDates(String id, String nomeCidade,
                                                                    String ufEstado,String dataInicio,
                                                                    String dataFim);

    List<Object[]> getDisponibilidadesBetweenData(String nomeCidade, String ufEstado,
                                                  String dataInicio, String dataFim);
}
