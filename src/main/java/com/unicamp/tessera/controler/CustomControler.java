package com.unicamp.tessera.controler;

import com.unicamp.tessera.repository.CustomRepositoryImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/custom")
public class CustomControler {
    private final CustomRepositoryImpl customRepository;

    public CustomControler(CustomRepositoryImpl customRepository) {
        this.customRepository = customRepository;
    }

    @GetMapping("/paciente/medicamentos")
    public List<Object[]> pacientesEmedicamentos(@RequestParam(value = "id") String id){
        return customRepository.getPacienteWithMedicamentos(id);
    }

    @GetMapping("/paciente/medicamentos/atuais")
    public List<Object[]> pacientesEmedicamentosAtuais(@RequestParam(value = "id") String id){
        return customRepository.getPacienteWithMedicamentosAtuais(id);
    }

    @GetMapping("/paciente/comorbidades")
    public List<Object[]> pacientesEcomorbidades(@RequestParam(value = "id") String id){
        return customRepository.getPacientesWithComorbidades(id);
    }

    @GetMapping("/paciente/comorbidades/atuais")
    public List<Object[]> pacientesEcomorbidadesAtuais(@RequestParam(value = "id") String id){
        return customRepository.getPacientesWithComorbidadesAtuais(id);
    }

    @GetMapping("/medico/especializacao")
    public List<Object[]> medicoEespecializacao(@RequestParam(value = "id") String id){
        return customRepository.getMedicoWithEspecializacoes(id);
    }

    @GetMapping("/consulta/paciente")
    public List<Object[]> consultaAgendadaPorPaciente(@RequestParam(value = "id") String id){
        return customRepository.getConsultasOfPacienteWhereStatusIsAgendada(id);
    }

    @GetMapping("/consulta/medico")
    public List<Object[]> consultaAgendadaPorMedico(@RequestParam(value = "id") String id){
        return customRepository.getConsultasOfMedicoWhereStatusIsAgendada(id);
    }

    @GetMapping("/clinica/endereco/all")
    public List<Object[]> clinicasComEndereco(){
        return customRepository.getClinicasWithEnderecos();
    }

    @GetMapping("/disponibilidade/local&data")
    public List<Object[]> disponibilidadePorLocalEData(@RequestParam(value = "cidade") String cidade,
                                                       @RequestParam(value = "estado") String estado,
                                                       @RequestParam(value = "inicio") String inicio,
                                                       @RequestParam(value = "fim") String fim) {
        return customRepository.getDisponibilidadesBetweenData(cidade, estado, inicio, fim);
    }

    @GetMapping("/disponibilidade/local&data/medico")
    public List<Object[]> disponibilidadePorMedicoELocalEData(@RequestParam(value = "cidade") String cidade,
                                                       @RequestParam(value = "estado") String estado,
                                                       @RequestParam(value = "inicio") String inicio,
                                                       @RequestParam(value = "fim") String fim,
                                                       @RequestParam(value = "id") String id) {
        return customRepository.getDisponibilidadeMedicoBetweenDates(id, cidade, estado, inicio, fim);
    }

    @GetMapping("disponibilidade/local&data/especializacao")
    public List<Object[]> disponibilidadePorEspecializacaoELocalEData(@RequestParam(value = "cidade") String cidade,
                                                       @RequestParam(value = "estado") String estado,
                                                       @RequestParam(value = "inicio") String inicio,
                                                       @RequestParam(value = "fim") String fim,
                                                       @RequestParam(value = "id") String id) {
        return customRepository.getDisponibilidadeMedicosEspecializaBetweenDates(id, cidade, estado, inicio, fim);
    }
}
