package ma.yc.GUI;

import ma.yc.core.Print;
import ma.yc.dao.impl.SocieteDaoImp;
import ma.yc.dto.PatientDto;
import ma.yc.dto.SalaireDto;
import ma.yc.dto.SocieteDto;
import ma.yc.model.Patient;
import ma.yc.model.Salaire;
import ma.yc.service.SalaireService;
import ma.yc.service.SocieteService;
import ma.yc.service.impl.SalaireServiceImp;
import ma.yc.service.impl.SocieteServiceImp;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SocieteGUI implements DisplayGUI{

    private SocieteService societeService;
    private SalaireService salaireService;

    public SocieteGUI(){
        this.societeService = new SocieteServiceImp();
        this.salaireService = new SalaireServiceImp();

    }
    @Override
    public int displayMainOptions(Scanner scanner) {
        this.societeService.calculateRetraiteSalary();
        Print.log("Bienvenue dans l'application de gestion des patients");
        Print.log("1- Add your Own Company");
        Print.log("2- Get access To your Company Dahsboard");
        Print.log("3- Check your Retirement Salary");


        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                Print.log("add your Own Societe");
                Print.log("Enter the id of your society");
                //todo : add a condition;
                String id = scanner.nextLine();

                Print.log("Enter password to acceess your company next time");
                String password = scanner.nextLine();
                password = BCrypt.hashpw(password,BCrypt.gensalt());

                Print.log("Entrer le nom de votre societe");
                String nom = scanner.nextLine();

                Print.log("entrer la description de votre entreprise");
                String description = scanner.nextLine();



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
                            if(this.societeService.checkifemployeealredyexist(matricule)){
                                Print.log("this employee already exist,now he is forwarded to this societe");
                                this.societeService.changesociete(matricule,societeid);
                                continue;
                            }
                            Print.log("enter the actual salary ");
                            float salaire = scanner.nextFloat();
                            Print.log("enter cotisatio de salaire");
                            int cotisationSalaire = scanner.nextInt();
                            scanner.nextLine();
                            Print.log("enter data of birth yyyy-mm-dd ");
                            String Dateofbirth = scanner.nextLine();
                            PatientDto patientDto1 = new PatientDto();
                            patientDto1.matricule = matricule;
                            patientDto1.idsociete = id;
                            patientDto1.datadeNaissance = Dateofbirth;
                            patientDto1.salaire = salaire;
                            patientDto1.cotisationSalaire = cotisationSalaire;
                            patientDtos.add(patientDto1);

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
                        this.SocieteDashboard(scanner,idSociete);
                }else{
                    System.out.println("something went wrong");
                }
                break;
            case 3:
                Print.log("entrer votre matricule ");
                String Matricule = scanner.nextLine();
                PatientDto patientDto = this.societeService.checkYourRetirementSalary(Matricule);




                System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("| %-15s | %-15s | %-15s | %-20s | %-17s | %-15s |\n",
                        "Matricule", "Salary", "Number Jour travaille", "StatusRetraitment", "Retirement Salary","Pension Veillesse");
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------");



                System.out.printf("| %-15s | %-15s | %-21d | %-21s | %-16f | %-16d \n",
                        patientDto.matricule, patientDto.salaire, patientDto.numberWorkingdays,
                        patientDto.statusRetrait, patientDto.salaireRetrait,patientDto.pensionVeillesse);

                System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

            default:
                Print.log("Enter a valid choice");
        }
        return 0;
    }
    public void SocieteDashboard(Scanner scanner,String Soieteid){

        scanner = new Scanner(System.in);
        Print.log("1- Add new Employee");
        Print.log("2- Increase Number of working days");

        String choiceStr = scanner.nextLine();
        int choice = Integer.parseInt(choiceStr);
        switch (choice){
            case 1:
                //todo: need to implemnt logic here

                    List<PatientDto> patientDtos = new ArrayList<PatientDto>();

                    String choicee = "y";
                    while (choicee.equals("y")) {
                        Print.log("Enter matricule Number of your employee");
                        String matricule = scanner.nextLine();
                        if(this.societeService.checkifemployeealredyexist(matricule)){
                            Print.log("this employee already exist,now he is forwarded to this societe");
                            this.societeService.changesociete(matricule,Soieteid);
                            continue;
                        }
                        String societeid = Soieteid;
                        //todo : implement a condition to check if its already exits
                        Print.log("enter the actual salary ");
                        float salaire = scanner.nextFloat();
                        scanner.nextLine();

                        Print.log("enter data of birth yyyy-mm-dd ");
                        String Dateofbirth = scanner.nextLine();
                        Print.log("enter cotisatio de salaire");
                        int cotisationSalaire = scanner.nextInt();
                        PatientDto patientDto1 = new PatientDto();
                        patientDto1.matricule = matricule;
                        patientDto1.idsociete = Soieteid;
                        patientDto1.salaire = salaire;
                        patientDto1.cotisationSalaire = cotisationSalaire;
                        patientDto1.datadeNaissance = Dateofbirth;
                        patientDtos.add(patientDto1);
                        scanner.nextLine();
                        Print.log("enter new employee y/n");
                        choicee = scanner.nextLine();
                    }
                    this.societeService.ajouteEmployee(patientDtos,Soieteid);
                    this.SocieteDashboard(scanner,Soieteid);
                    break;
            case 2:
                Print.log("entrer le matricule de patient");
                String matricule = scanner.nextLine();
                if(this.societeService.checkifemployeealredyexist(matricule)){
                    Print.log("entrer le nombre de jour travaille par ce employée");
                    int NJT = scanner.nextInt();

                    this.societeService.augmenterNombreJourTravaille(matricule,NJT);
                }else{
                    Print.log("there is no employee with this matricule");
                }

                this.SocieteDashboard(scanner,Soieteid);
                break;
                //todo : increase working day

            case 3:

                break;
            case 4:
                // todo:
                break;





                //todo : check al your employee


        }
    }
}
