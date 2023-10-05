package ma.yc.Mapper.impl;

import ma.yc.Mapper.Mapper;
import ma.yc.dto.PatientDto;
import ma.yc.dto.SalaireDto;
import ma.yc.model.Patient;
import ma.yc.model.Salaire;
import ma.yc.model.Societe;
import org.springframework.security.core.parameters.P;

import java.sql.PreparedStatement;

public class SalaireMapper implements Mapper<SalaireDto, Salaire> {

    @Override
    public Salaire toEntity() {
        return null;
    }

    @Override
    public Salaire toEntity(SalaireDto o) {
        Salaire salaire = new Salaire();
        salaire.setSalaire(o.salaire);
        Patient patient = new Patient();
        patient.setMatricule(o.patientid);
        salaire.setPatient(patient);
        Societe societe = new Societe();
        societe.setId(o.societeid);
        salaire.setSociete(societe);
        return  salaire;
    }

    @Override
    public SalaireDto toDto() {
        return null;
    }

    @Override
    public SalaireDto toDto(Salaire o) {
        return null;
    }

    @Override
    public PreparedStatement toPreparedStatement(Salaire o, PreparedStatement preparedStatement) {
        return null;
    }
}
