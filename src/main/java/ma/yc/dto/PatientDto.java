package ma.yc.dto;

import ma.yc.model.Dossier;
import ma.yc.model.SalaireRetrait;
import ma.yc.model.Societe;

import java.util.List;

public class PatientDto {
    public String matricule;
    public String nom;
    public float CotisationSalaire;

    public float Salaire;
    public SalaireRetraitDto salaireRetraitDto;

    public SocieteDto societeDto;


}
