package com.Task2HMS;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HospitalManagementSystem {
    static Scanner sc = new Scanner(System.in);
    static Patient[] patients = new Patient[100];
    static Appointment[] appointments = new Appointment[100];
    static EHR[] ehrRecords = new EHR[100];
    static Bill[] bills = new Bill[100];
    static Inventory[] inventories = new Inventory[100];
    static Staff[] staffs = new Staff[100];
    static int patientCount = 0, appointmentCount = 0, ehrCount = 0, billCount = 0, inventoryCount = 0, staffCount = 0;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nHOSPITAL MANAGEMENT SYSTEM");
            System.out.println("1. Register Patient\n2. Schedule Appointment\n3. Manage EHR\n4. Generate Bill\n5. Manage Inventory\n6. Manage Staff\n7. View Patients\n8. View Appointments\n9. View Staff\n10. Exit");
            System.out.print("Choose an option: ");
            
            if (!sc.hasNextInt()) { 
                System.out.println("Invalid input! Please enter a number.");
                sc.next(); 
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: registerPatient(); break;
                case 2: scheduleAppointment(); break;
                case 3: manageEHR(); break;
                case 4: generateBill(); break;
                case 5: manageInventory(); break;
                case 6: manageStaff(); break;
                case 7: viewPatients(); break;
                case 8: viewAppointments(); break;
                case 9: viewStaff(); break;
                case 10: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void registerPatient() {
        System.out.print("Enter Patient Name: ");
        String name = sc.next();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();

        System.out.print("Enter Gender (M/F): ");
        char gender = sc.next().charAt(0); 

        System.out.print("Enter Disease: ");
        String disease = sc.next();

        String registeredDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        patients[patientCount] = new Patient(patientCount + 1, name, age, gender, disease, registeredDate);
        System.out.println("Patient Registered Successfully with ID: " + (patientCount + 1) + " on " + registeredDate);
        patientCount++;
    }

    static void scheduleAppointment() {
        System.out.print("Enter Patient ID: ");
        int patientId = sc.nextInt();

        System.out.print("Enter Doctor ID: ");
        int doctorId = sc.nextInt();

        System.out.print("Enter Appointment Date (dd-MM-yyyy): "); 
        String date = sc.next(); 

        appointments[appointmentCount++] = new Appointment(patientId, doctorId, date);
        System.out.println("Appointment Scheduled Successfully on: " + date);
    }

    static void manageEHR() {
        System.out.print("Enter Patient ID: ");
        int patientId = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter Medical History: ");
        String history = sc.nextLine();

        ehrRecords[ehrCount++] = new EHR(patientId, history);
        System.out.println("EHR Updated Successfully!");
    }

    static void generateBill() {
        System.out.print("Enter Patient ID: ");
        int patientId = sc.nextInt();

        
        if (patientId < 1 || patientId > patientCount || patients[patientId - 1] == null) {
            System.out.println("Error: Patient with ID " + patientId + " does not exist!");
            return;
        }

        System.out.print("Enter Bill Amount: ");
        double amount = sc.nextDouble();
        System.out.print("Enter Doctor Fee: ");
        double doctorFee = sc.nextDouble();
        double totalAmount = amount + doctorFee;

        bills[billCount++] = new Bill(patientId, amount, doctorFee);
        patients[patientId - 1].totalBillAmount += totalAmount; 
        System.out.println("Bill Generated Successfully!");
        System.out.println("Total Bill Amount (including Doctor Fee): " + totalAmount);
    }

    static void manageInventory() {
        System.out.print("Enter Item Name: ");
        String item = sc.next();
        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        inventories[inventoryCount++] = new Inventory(item, quantity);
        System.out.println("Inventory Updated Successfully!");
    }

    static void manageStaff() {
        System.out.print("Enter Staff Name: ");
        String name = sc.nextLine(); 

        System.out.print("Enter Role: ");
        String role = sc.nextLine(); 

        staffs[staffCount++] = new Staff(staffCount + 1, name, role);
        System.out.println("Staff Added Successfully with ID: " + staffCount);
    }

    static void viewPatients() {
        System.out.println("\nPatient Details:");
        for (int i = 0; i < patientCount; i++) {
            System.out.println("ID: " + patients[i].id + ", Name: " + patients[i].name + ", Age: " + patients[i].age +
                    ", Disease: " + patients[i].disease + ", Total_Bill_Amount_Paid: " + patients[i].totalBillAmount +
                    ", Registered Date: " + patients[i].registeredDate); 
        }
    }

    static void viewAppointments() {
        System.out.println("\nAppointments:");
        for (int i = 0; i < appointmentCount; i++) {
            System.out.println("Patient ID: " + appointments[i].patientId + ", Doctor ID: " + appointments[i].doctorId + ", Date: " + appointments[i].date);
        }
    }

    static void viewStaff() {
        System.out.println("\nStaff Details:");
        for (int i = 0; i < staffCount; i++) {
            System.out.println("ID: " + staffs[i].id + ", Name: " + staffs[i].name + ", Role: " + staffs[i].role);
        }
    }
}

class Patient {
    String name, disease, registeredDate;
    int age, id;
    double totalBillAmount;

    Patient(int id, String name, int age, char gender, String disease, String registeredDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.registeredDate = registeredDate;
        this.totalBillAmount = 0;
    }
}

class Appointment {
    int patientId, doctorId;
    String date;

    Appointment(int patientId, int doctorId, String date) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }
}

class EHR {
    int patientId;
    String history;

    EHR(int patientId, String history) {
        this.patientId = patientId;
        this.history = history;
    }
}

class Bill {
    int patientId;
    double amount, doctorFee;

    Bill(int patientId, double amount, double doctorFee) {
        this.patientId = patientId;
        this.amount = amount;
        this.doctorFee = doctorFee;
    }
}

class Inventory {
    String item;
    int quantity;

    Inventory(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}

class Staff {
    int id;
    String name, role;

    Staff(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
