package ma.yc.GUI;

import ma.yc.core.Print;
import ma.yc.dto.PatientDto;
import ma.yc.dto.SalaireRetraitDto;
import ma.yc.dto.SocieteDto;
import ma.yc.model.SalaireRetrait;
import ma.yc.service.SocieteService;
import ma.yc.service.impl.SocieteServiceImp;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocieteGUI implements DisplayGUI{

    private SocieteService societeService;
    public SocieteGUI(){
        this.societeService = new SocieteServiceImp();
    }
    @Override
    public int displayMainOptions(Scanner scanner) {
        Print.log("Bienvenue dans l'application de gestion des patients");
        Print.log("1- Add your Own Company");
        Print.log("2- Get access To your Company Dahsboard");
        /*
        Print.log("2- Add new Employee");
        Print.log("3- Update Number of Working Day");//including increasing and decreasing
        Print.log("4-Calculate retiring Salary");
        Print.log("5-Change the Retiring Pension");
        Print.log("6-Check if you can benifice of Retiring");
        */

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                Print.log("add your Own Societe");
                Print.log("Enter the id of your society");
                String id = scanner.nextLine();

                Print.log("Enter password to acceess your company next time");
                String password = scanner.nextLine();
                password = BCrypt.hashpw(password,BCrypt.gensalt());

                Print.log("Entrer le nom de votre societe");
                String nom = scanner.nextLine();

                Print.log("entrer la description de votre entreprise");
                String description = scanner.nextLine();

                // todo : implement the logic

                SocieteDto societeDto = new SocieteDto();
                societeDto.id = id;
                societeDto.description = description;
                societeDto.password = password;
                societeDto.nom = nom;

                boolean added = this.societeService.ajouteSociete(societeDto);
                if(added){
                    Print.log("your societe Added successfully");
                    Print.log("Add Employees y/n");
                    String Choi = scanner.nextLine();
                    if(Choi.equals("y")){
                        List<PatientDto> patientDtos = new ArrayList<PatientDto>();

                        String choicee = "y";
                        while(choicee.equals("y")){
                            Print.log("Enter matricule Number of your employee");
                            String matricule = scanner.nextLine();
                            String societeid = id;
                            Print.log("enter the salary");
                            float salaire = scanner.nextFloat();
                            Print.log("enter cotisatio de salaire");
                            int cotisationSalaire = scanner.nextInt();
                            Print.log("enteer nombre of working days ");
                            int numberwd = scanner.nextInt();
                            PatientDto patientDto = new PatientDto();
                            patientDto.matricule = matricule;
                            patientDto.idsociete = id;
                            patientDto.numberWorkingdays = numberwd;
                            patientDto.salaire = salaire;
                            patientDto.cotisationSalaire = cotisationSalaire;
                            patientDtos.add(patientDto);

                            scanner.nextLine();
                            Print.log("enter new employee y/n");

                            choicee = scanner.nextLine();

                        }
                        this.societeService.ajouteEmployee(patientDtos,id);

                    }else{
                        Print.log("there is a problem");
                    }

                }else{
                    Print.log("something went wrong try again later");
                }



                break;
            case 2:
                Print.log("Enter the id of your Company");
                String idSociete = scanner.nextLine();
                Print.log("Enter the Password ");
                String Password = scanner.nextLine();
                //todo : check if the societe exist;
                if(this.societeService.accederDashboardSociete(idSociete,Password)){
                        this.SocieteDashboard(scanner);
                }else{
                    System.out.println("something went wrong");
                }
                break;
            default:
                Print.log("Enter a valid choice");
        }
        return 0;
    }
    public void SocieteDashboard(Scanner scanner){
        Print.log("1- Add new Employee");
        Print.log("2- Increase Number of working days");
        Print.log("3- Descrease Number of workinf days");
        /*
        Print.log("3- Calculate retiring Salary");
        Print.log("4- Change the Retiring Pension");
        Print.log("5- Check if you can benifice of Retiring");
        Print.log("6- Check all your employee");
        */

        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                //todo: need to implemnt logic here
                String idSociete = "";
                //PatientDto patientDtos = null;
                //this.societeService.ajouteEmployee(patientDtos,idSociete);
                break;
            case 2:
                //todo : need to implement logic here
                break;



        }
    }
}
