package ma.yc.dao.impl;

import ma.yc.dao.SalaireDao;
import ma.yc.database.DatabaseConnection;
import ma.yc.model.Patient;
import ma.yc.model.Salaire;

import javax.management.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaireDaoImp implements SalaireDao {

    private boolean AddState;
    private Connection connection;
    public SalaireDaoImp(){
        try {
            this.connection =   DatabaseConnection.getInstance().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    @Override
    public Boolean addSalaire(Salaire salaire) {
        this.AddState = false;
            try {
                String Querry = "INSERT INTO `salaire`(`matricule`, `societeID`, `salaire`) VALUES (?,?,?)";
                PreparedStatement preparedStatement = this.connection.prepareStatement(Querry);
                preparedStatement.setString(1,salaire.getPatient().getMatricule());
                preparedStatement.setString(2,salaire.getSociete().getId());
                preparedStatement.setFloat(3,salaire.getSalaire());
                int rows = preparedStatement.executeUpdate();
                if(rows > 0){
                    this.AddState = true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return this.AddState;
    }


    @Override
    public float calculateAverageSalary(String matricule) {
        return 0;
    }
}
