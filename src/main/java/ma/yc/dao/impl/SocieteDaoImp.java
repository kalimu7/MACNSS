package ma.yc.dao.impl;

import ma.yc.core.Print;
import ma.yc.dao.SocieteDao;
import ma.yc.database.DatabaseConnection;
import ma.yc.enums.statusRetraitment;
import ma.yc.model.Patient;
import ma.yc.model.Societe;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class SocieteDaoImp implements SocieteDao {
    private Connection connection;
    private boolean authstate;
    public SocieteDaoImp(){
        try {
            this.connection =  DatabaseConnection.getInstance().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    @Override
    public boolean ajouteSociete(Societe societe) {
        try {
            String Query = "INSERT INTO societe (`id_societe`,`password`,`nom`,`description`) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setString(1,societe.getId());
            preparedStatement.setString(2,societe.getPassword());
            preparedStatement.setString(3,societe.getNom());
            preparedStatement.setString(4,societe.getDescription());
            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean ajouteEmployee(Patient patient) {
        try{
            Print.log("the salary " + patient.getSalaire());
            String Query = "INSERT INTO `patients`(`matricule`, `societeID`, `DateDeNaissance`, `Salaire`, `cotisationS`, `PensionV`, `Numberwd`, `statusRetraitment`, `SalaireRetrait`) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setString(1,patient.getMatricule());
            preparedStatement.setString(2,patient.getIdSociete());
            Date date = Date.valueOf(patient.getDatenaissance());

            preparedStatement.setDate(3,date);
            preparedStatement.setFloat(4,patient.getSalaire());
            preparedStatement.setInt(5,patient.getCotisationSalaire());
            preparedStatement.setInt(6,patient.getPensionVeillesse());
            preparedStatement.setInt(7,patient.getNombreJourtravaille());
            preparedStatement.setString(8,patient.getStatusretrait());
            preparedStatement.setFloat(9,patient.getSalaireRetrait());
            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void augmenterNombreJourTravaille(String matricule, int NJT) {
            try {

                String Querry = " UPDATE patients set Numberwd  = Numberwd + ? where matricule = ? ";
                PreparedStatement preparedStatement = this.connection.prepareStatement(Querry);
                preparedStatement.setInt(1,NJT);
                preparedStatement.setString(2,matricule);
                int rows =  preparedStatement.executeUpdate();
                if(rows > 0){
                    LocalDate localDate = LocalDate.now();
                    Patient patient = this.fetchPatientBYMatricule(matricule);
                    Period age = Period.between(patient.getDatenaissance(),localDate);
                    //Print.log("age of employee " + age.getYears());
                    //Print.log("print the pension veillesse " + patient.getPensionVeillesse());
                    if(patient.getPensionVeillesse() < 70  ){
                        this.changeStatus(matricule,patient.getNombreJourtravaille(), age.getYears());
                    }
                }else{
                    Print.log("there is a problem");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
    }

    public void changeStatus(String matricule,int NWD,int age){
        try {
            //Print.log("here inside the change status method WC " + matricule + "  " + " age " + age + " nwd " + NWD);
            if (NWD > 1320 && NWD < 3240 && age >= 55) {
                String Query = " UPDATE `patients` SET `PensionV` = ? ,`statusRetraitment` = ? where matricule = ? ";
                int PV = 50 ;
                PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
                preparedStatement.setInt(1,PV);
                preparedStatement.setString(2,statusRetraitment.peut_bénéficier.toString());
                preparedStatement.setString(3,matricule);
                preparedStatement.executeUpdate();
            }else if(NWD > 1320 && NWD < 3240 ){
                //Print.log("here inside of NWD > 1320 && NWD < 3240 ");
                String Query = "update patients set PensionV = ?  where matricule = ?";
                int PV = 50 ;
                PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
                preparedStatement.setInt(1,PV);
                preparedStatement.setString(2,matricule);
                preparedStatement.executeUpdate();
            }
            if (NWD >= 3240 && age >= 55) {
                int plus = NWD - 3240;
                plus = plus / 216;
                int PV = 50 + plus;
                String Query = "update patients set PensionV = ? , statusRetraitment = ? where matricule = ? ";
                PreparedStatement preparedStatement2 = this.connection.prepareStatement(Query);
                preparedStatement2.setInt(1,PV);
                preparedStatement2.setString(2, statusRetraitment.peut_bénéficier.toString());
                preparedStatement2.setString(3,matricule);
                preparedStatement2.executeUpdate();
            }else if(NWD >= 3240 ){
                int plus = NWD - 3240;
                plus = plus / 216;
                int PV = 50 + plus;
                String Query = "update patients set PensionV = ? where matricule = ?";
                PreparedStatement preparedStatement2 = this.connection.prepareStatement(Query);
                preparedStatement2.setInt(1,PV);
                preparedStatement2.setString(2,matricule);
                preparedStatement2.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public Patient fetchPatientBYMatricule(String matricule){
        try {
            PreparedStatement preparedStatement1 = this.connection.prepareStatement("SELECT * from patients where matricule = ?");
            preparedStatement1.setString(1,matricule);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if(resultSet.next()){
                Patient patient = new Patient();
                patient.setNombreJourtravaille(resultSet.getInt("Numberwd"));
                patient.setPensionVeillesse(resultSet.getInt("PensionV"));
                patient.setDatenaissance(resultSet.getDate("DateDeNaissance").toLocalDate());
                return patient;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean accederSocieteDashboard(String societeid, String password) {
        this.authstate = false;
        try{
            String Query = "select * from societe where `id_societe` = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setString(1,societeid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String password1 = resultSet.getString("password");
                if(BCrypt.checkpw(password,password1)){
                    this.authstate = true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return this.authstate;
    }



    @Override
    public boolean checkIfuserAlreadyexist(String matricule){
        try{
            String Query = "Select * from `patients` where `matricule` = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setString(1,matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean ChangePatienCompany(String matricule, String Societeid){
        try{
            String Querry = "update patients set `societeID`= ?  Where `matricule` = ? ";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Querry);
            preparedStatement.setString(1,Societeid);
            preparedStatement.setString(2,matricule);
            int rows =  preparedStatement.executeUpdate();
            if(rows > 0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Patient> SelectAllYourEmployee(String societeid) {
        return null;
    }


    public List<Patient> selectEligible(){
        try {
            List<Patient> patients = new ArrayList<Patient>();
            String Query = "SELECT *FROM patients WHERE patients.statusRetraitment =  'peut_bénéficier'";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Patient patient = new Patient();
                patient.setMatricule(resultSet.getString("matricule"));
                patient.setPensionVeillesse(resultSet.getInt("PensionV"));
                patients.add(patient);
            }
            return patients;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Patient> selectEligible1(){
        try {
            List<Patient> patients = new ArrayList<Patient>();
            String Query = "SELECT *FROM patients ";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Patient patient = new Patient();
                patient.setMatricule(resultSet.getString("matricule"));
                patient.setPensionVeillesse(resultSet.getInt("PensionV"));
                patient.setStatusretrait(resultSet.getString("statusRetraitment"));
                patient.setSalaire(resultSet.getFloat("Salaire"));
                patients.add(patient);
            }
            return patients;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public float calculateRetriatSalary(String matricule, int PV,float Salary){
        try{

            String Querry = "SELECT SUM(salaire) FROM (SELECT salaire FROM salaire WHERE matricule = ? ORDER BY id DESC LIMIT 96 ) AS subquery;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Querry);
            preparedStatement.setString(1,matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                float salaire =  resultSet.getFloat("SUM(salaire)");

                String Query = "SELECT count(*) FROM salaire where matricule = ? ";
                PreparedStatement preparedStatement1 = this.connection.prepareStatement(Query);
                preparedStatement1.setString(1,matricule);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                if(resultSet1.next()){
                    int NumberOFMonthlySalary =  resultSet1.getInt("count(*)");
                    float TauxPV = (float) PV / 100;
                    if(PV == 50){

                        if((float) Salary /2 > 6000){
                            Salary = 6000;
                        } else if ((float) Salary /2 <1000) {
                            Salary = 1000;
                        }
                        this.InsertRetirementSalary(matricule,(float) Salary/2 );
                        return Salary;
                    }
                    if(NumberOFMonthlySalary == 1){
                        float RetirementSalay = (float) Salary * TauxPV;
                        if(RetirementSalay > 6000){
                            RetirementSalay = 6000;
                        } else if (RetirementSalay <1000) {
                            RetirementSalay = 1000;
                        }
                        //Print.log("matricule " + matricule + "Retirement Salary -----" + RetirementSalay);
                        this.InsertRetirementSalary(matricule,RetirementSalay);
                        return RetirementSalay;
                    }else if(NumberOFMonthlySalary > 96){
                        NumberOFMonthlySalary = 96;
                    }
                    float SalaireMoyenne = (float) salaire/NumberOFMonthlySalary;
                    float RetirementSalary = SalaireMoyenne * TauxPV;
                    if(RetirementSalary > 6000){
                        RetirementSalary = 6000;
                    } else if (RetirementSalary <1000) {
                        RetirementSalary = 1000;
                    }
                    //Print.log("matricule " + matricule + " Retirement Salary " + RetirementSalary);
                    this.InsertRetirementSalary(matricule,RetirementSalary);
                    return RetirementSalary;
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public void InsertRetirementSalary(String matricule,float salaireRetrait){
        try {
            String Query = "update patients SET SalaireRetrait = ? where matricule  = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setFloat(1,salaireRetrait);
            preparedStatement.setString(2,matricule);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Patient CheckYourRetiremntSalaire(String matricule){

        try {

            String Query = "SELECT *FROM patients where matricule = ? ";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setString(1,matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Patient patient = new Patient();
                patient.setMatricule(resultSet.getString("matricule"));
                patient.setSalaire(resultSet.getFloat("Salaire"));
                patient.setPensionVeillesse(resultSet.getInt("PensionV"));
                patient.setNombreJourtravaille(resultSet.getInt("Numberwd"));
                patient.setStatusretrait( resultSet.getString("statusRetraitment"));
                patient.setSalaireRetrait( resultSet.getFloat("SalaireRetrait"));
                return patient;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }



}
