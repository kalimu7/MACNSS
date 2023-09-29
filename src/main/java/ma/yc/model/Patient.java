package ma.yc.model;

import java.util.List;

public class Patient {
    private String matricule;

    private String nom;
    private List<Dossier> dossiers;

    private float CotisationSalaire;

    private float Salaire;

    private SalaireRetrait salaireRetrait;

    private Societe societe;
    public float getCotisationSalaire() {
        return CotisationSalaire;
    }

    public void setCotisationSalaire(float cotisationSalaire) {
        CotisationSalaire = cotisationSalaire;
    }

    public float getSalaire() {
        return Salaire;
    }

    public void setSalaire(float salaire) {
        Salaire = salaire;
    }





    public String getMatricule() {
        return matricule;
    }

    public List<Dossier> getDossiers() {
        return dossiers;
    }

    public void setDossiers(List<Dossier> dossiers) {
        this.dossiers = dossiers;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
