package com.Task1;

import java.util.ArrayList;
import java.util.Scanner;

public class ATMInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ATM userATM = new ATM(4000.0, 1103); // User's ATM account
        ATM recipientATM = new ATM(2500.0, 2002); // Recipient account for transfers
        
        System.out.print("Enter your PIN: ");
        int enteredPin = sc.nextInt();
        
        if (!userATM.authenticate(enteredPin)) {
            System.out.println("Incorrect PIN. Exiting...");
            sc.close();
            return;
        }
        
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Transaction History");
            System.out.println("6. Change PIN");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                	if(userATM.confirmTransaction(sc))
                		break;
                    System.out.println("Your balance: Rs." + userATM.checkBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount : Rs.");
                    double depositAmount = sc.nextDouble();
                    if(userATM.confirmTransaction(sc))
                    	break;
                    userATM.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: Rs.");
                    double withdrawAmount = sc.nextDouble();
                    if(userATM.confirmTransaction(sc))
                    	break;
                    userATM.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient account number: ");
                    long recipientAccountNumber = sc.nextLong();
                    if(String.valueOf(recipientAccountNumber).length()!=11)
                    {
                    	System.out.println("Invalid account Number, it must be 11 digits");
                    	break;
                    }
//                   
                    System.out.print("Enter transfer amount: Rs.");
                    double transferAmount = sc.nextDouble();
                    if(userATM.confirmTransaction(sc))
                    	break;
                    userATM.transfer(recipientATM, recipientAccountNumber, transferAmount);
                    break;
                    
                case 5:
                	if(userATM.confirmTransaction(sc))
                		break;
                    userATM.transactionHistory();
                    break;
                case 6:
                	userATM.changePin(sc);
                	break;
                case 7:
                    System.out.println("Exiting ATM. Thank you and visit again");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


class ATM {
    private double balance;
    private int pin;
    private ArrayList<String> transactionHistory;
    
    public ATM(double initialBalance, int pin) {
        this.balance = initialBalance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
    }
    
    public boolean authenticate(int enteredPin) {
       // return String.valueOf(enteredPin).length()==4 && this.pin== enteredPin;
    	if(String.valueOf(enteredPin).length()==4)
    	{
    		if(this.pin!=enteredPin)
    		{
    			System.out.println("Invalid pIN");
    			return false;
    		}
    		else
    		{
    			return true;
    		}
    	}
    	else
    	{
    		System.out.println("PIN must be 4 digits");
    		return false;
    	}
        
    }
    
    public boolean confirmTransaction(Scanner scanner) {
        System.out.println("Press Enter to proceed or press 0 to cancel.");
        scanner.nextLine(); // consume the leftover newline
        String option = scanner.nextLine();
        if (option.equals("0")) {
            System.out.println("Returning to ATM Menu...");
            return true;
        } else if (!option.isEmpty()) {
            System.out.println("Invalid input. Returning to ATM Menu...");
            return true;
        }
        System.out.print("Enter your PIN: ");
        int enteredPin = scanner.nextInt();
        if (!authenticate(enteredPin)) {
            
            return true;
        }
        return false;
    }


	void changePin(Scanner sc) {
		if(confirmTransaction(sc))
			return;
		System.out.println("Enter new 4-digit PIN:");
		int newPin = sc.nextInt();
		if(String.valueOf(newPin).length()!=4)
		{
			System.out.println("Invalid PIN , it must be 4 digits.");
			return;
		}
		System.out.println("Confirm new PIN: ");
		int confirmPin = sc.nextInt();
		if(newPin == confirmPin)
		{
			this.pin=newPin;
			System.out.println("PIN changed successfully.");
		}
		else
		{
			System.out.println("PIN confirmation failed, PIN not changed.");
		}
		
	}
    
    public double checkBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0 && (amount % 100 == 0))
        {
            balance += amount;
            transactionHistory.add("Deposited: Rs." + amount);
            System.out.println("Deposit successful. New balance: Rs." + balance);
        } else {
            System.out.println("Invalid deposit amount. Please deposit amount with Rs.100, Rs.200, or Rs.500 notes only.");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && (amount % 100 == 0) &&amount <= balance)
{
            balance -= amount;
            transactionHistory.add("Withdrew: Rs." + amount);
            System.out.println("Withdrawal successful. Remaining balance: Rs." + balance);
        } else {
            System.out.println("Invalid withdrawal amount. Please withdraw amount divisible by 100 or check your balance.");
        }
    }
    
    public void transfer(ATM recipient, long recipientAccountNumber, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: Rs." + amount + " to Account " + recipientAccountNumber);
            System.out.println("Transfer successful to Account " + recipientAccountNumber + ". Remaining balance: Rs." + balance);
        } else {
            System.out.println("Insufficient balance or invalid transfer amount.");
        }
    }
    
    public void transactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }


}