package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, int months) {
		double quota = contract.getTotalValue() / months; //Divide o valor total pela quantidade de meses
		for(int i = 1; i <= months; i++) {
			double updatedQuota = quota + onlinePaymentService.interest(quota, i); //Juros mensais
			double totalQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota); //Juros totais
			
			Date dueDate = addMonths(contract.getDate(), i); //Associa uma data de contrato dividida em n vezes à funcção addMonths.
			contract.getInstallments().add(new Installment(dueDate, totalQuota)); //Acessa o contrato, parcelas, adiciona uma nova parcela com base em uma data e um valor.
		}
	}
	
	private Date addMonths(Date date, int N) {
		Calendar calendar = Calendar.getInstance(); //Instanciar Calendar
		calendar.setTime(date);	//Setar uma data
		calendar.add(Calendar.MONTH, N); //Adicionar um mês N vezes nessa data
		return calendar.getTime(); //Retorna-la já atualizada
	}

}
