package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Contract;
import model.entities.Installment;
import model.services.ContractService;
import model.services.PaypalService;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		//Variaveis de inicialização
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		//Corpo do Output
		System.out.println("Enter the contract data: ");
		System.out.print("Number: ");
		int number = sc.nextInt();
		System.out.print("Date: ");
		Date date = sdf.parse(sc.next());
		System.out.print("Contract Value: ");
		Double cT = sc.nextDouble();
		
		//Instanciar contrato
		Contract contract = new Contract(number, date, cT);
		
		//Numero de Parcelas
		System.out.print("Enter number of installments: ");
		int n = sc.nextInt();
		
		//Instanciar servico de parcelas
		ContractService cS = new ContractService(new PaypalService()); //Injeção de dependência pelo paypalservice
		
		//Processar contrato
		cS.processContract(contract, n);
		
		//Procurar e printar as parcelas
		System.out.println("Installments: ");
		for(Installment it : contract.getInstallments()) { //Acessar as parcelas na classe contrato
			System.out.println(it);
		}
		
		sc.close();
		
	}
}
