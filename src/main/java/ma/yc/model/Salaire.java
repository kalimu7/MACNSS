package ma.yc.model;

public class Salaire {
    private Patient patient;
    private float salaire;

    private Societe societe;

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public String getMatriciule() {
        return matriciule;
    }

    public void setMatriciule(String matriciule) {
        this.matriciule = matriciule;
    }

    private String matriciule;




}
