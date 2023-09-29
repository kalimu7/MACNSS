package ma.yc.model;

public class SalaireRetrait {
    private Patient patient;
    private int nombreJourTarivaille;

    private Boolean possibilteBenifice;

    private float salaireRetraite;



    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getNombreJourTarivaille() {
        return nombreJourTarivaille;
    }

    public void setNombreJourTarivaille(int nombreJourTarivaille) {
        this.nombreJourTarivaille = nombreJourTarivaille;
    }

    public boolean getPossibilteBenifice() {
        return possibilteBenifice;
    }

    public void setPossibilteBenifice(Boolean possibilteBenifice) {
        this.possibilteBenifice = possibilteBenifice;
    }

    public float getSalaireRetraite() {
        return salaireRetraite;
    }

    public void setSalaireRetraite(float salaireRetraite) {
        this.salaireRetraite = salaireRetraite;
    }
}
