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
                    Print.log("age of employee " + age.getYears());
                    Print.log("print the pension veillesse " + patient.getPensionVeillesse());
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
            Print.log("here inside the change status method WC " + matricule + "  " + " age " + age + " nwd " + NWD);
            if (NWD > 1320 && NWD < 3240 && age >= 55) {
                String Query = " UPDATE `patients` SET `PensionV` = ? ,`statusRetraitment` = ? where matricule = ? ";
                int PV = 50 ;
                PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
                preparedStatement.setInt(1,PV);
                preparedStatement.setString(2,statusRetraitment.peut_bénéficier.toString());
                preparedStatement.setString(3,matricule);
                preparedStatement.executeUpdate();
            }else if(NWD > 1320 && NWD < 3240 ){
                Print.log("here inside of NWD > 1320 && NWD < 3240 ");
                String Query = "update patients set PensionV = ?  where matricule = ?";
                int PV = 50 ;
                PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
                preparedStatement.setInt(1,PV);
                preparedStatement.setString(2,matricule);
                preparedStatement.executeUpdate();
            }
            if (NWD > 3240 && age >= 55) {
                int plus = NWD - 3240;
                plus = plus / 216;
                int PV = 50 + plus;
                String Query = "update patients set PensionV = ?  statusRetraitment = ? where matricule = ? ";
                PreparedStatement preparedStatement2 = this.connection.prepareStatement(Query);
                preparedStatement2.setInt(1,PV);
                preparedStatement2.setString(2, statusRetraitment.peut_bénéficier.toString());
                preparedStatement2.setString(3,matricule);
                preparedStatement2.executeUpdate();
            }else if(NWD > 3240 ){
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
    @Override
    public void calculateRetriatSalary(String matricule){
        try{
            String Querry = "SELECT SUM(salaire) FROM (SELECT salaire FROM salaire WHERE matricule = ? ORDER BY id DESC LIMIT 96 ) AS subquery;\n";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Querry);
            preparedStatement.setString(1,matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                float salaire =  resultSet.getFloat("SUM(salaire)");
                float SalaireMoyen = salaire/96;
                //todo : salaire de retrait cest SalaireMoyen X PV/100;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
