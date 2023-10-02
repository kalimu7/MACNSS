package ma.yc.model;

import java.util.List;

public class Patient {
    private String matricule;

    private String nom;
    private List<Dossier> dossiers;
    private int CotisationSalaire;

    private int pensionVeillesse;
    private float Salaire;

    private int NombreJourtravaille;

    private String idSociete;

    private String statusretrait;

    private float salareRetrait;

    public int getPensionVeillesse() {
        return pensionVeillesse;
    }

    public void setPensionVeillesse(int pensionVeillesse) {
        this.pensionVeillesse = pensionVeillesse;
    }

    public int getNombreJourtravaille() {
        return NombreJourtravaille;
    }

    public void setNombreJourtravaille(int nombreJourtravaille) {
        NombreJourtravaille = nombreJourtravaille;
    }

    public String getIdSociete() {
        return idSociete;
    }

    public void setIdSociete(String idSociete) {
        this.idSociete = idSociete;
    }

    public String getStatusretrait() {
        return statusretrait;
    }

    public void setStatusretrait(String statusretrait) {
        this.statusretrait = statusretrait;
    }

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }





    private Societe societe;
    public int getCotisationSalaire() {
        return CotisationSalaire;
    }

    public void setCotisationSalaire(int cotisationSalaire) {
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

    public float getSalareRetrait() {
        return salareRetrait;
    }

    public void setSalareRetrait(float salareRetrait) {
        this.salareRetrait = salareRetrait;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
