package ma.yc.dao.impl;

import ma.yc.dao.SocieteDao;
import ma.yc.database.DatabaseConnection;
import ma.yc.model.Patient;
import ma.yc.model.Societe;
import org.mindrot.jbcrypt.BCrypt;

import javax.management.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            String Query = "INSERT INTO `patients`(`matricule`, `societeID`, `Salaire`, `cotisationS`, `PensionV`, `Numberwd`, `statusRetraitment`, `SalaireRetrait`) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setString(1,patient.getMatricule());
            preparedStatement.setString(2,patient.getIdSociete());
            preparedStatement.setFloat(3,patient.getSalaire());
            preparedStatement.setInt(4,patient.getCotisationSalaire());
            preparedStatement.setInt(5,patient.getPensionVeillesse());
            preparedStatement.setInt(6,patient.getNombreJourtravaille());
            preparedStatement.setString(7,patient.getStatusretrait());
            preparedStatement.setFloat(8,patient.getSalareRetrait());
            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void entreNobmreJourTraivaille(int NJT) {

    }

    @Override
    public void augmenterNombreJourTravaille(int NJT) {

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

}
