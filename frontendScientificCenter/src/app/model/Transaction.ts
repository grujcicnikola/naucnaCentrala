import { Journal } from './Journal';

export class Transaction{
    journalId: Int16Array;


	buyerEmail: String;

    amount: Float32Array;

	status: String;

    orderId: String;
    
	successURL: String;
	
	errorURL: String;
	
    failedURL: String;
    
    merchantIssn: String

    journal: Journal;
}

