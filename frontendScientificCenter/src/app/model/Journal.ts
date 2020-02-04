import { Subscription } from './Subscription';

export class Journal{
    
    id: Int16Array;
	
	title: String;
	
	issn: String;
	
	isActivated: Boolean;
	
	isOpenAccess: Boolean;
	
	price: Float32Array;

	suscriptionNum : number;

	subscriptions : Array<Subscription>;

	canSubscribe : boolean;

	canUnsubscribe : boolean;
}