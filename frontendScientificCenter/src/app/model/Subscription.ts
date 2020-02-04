export class Subscription{
        id : number;
		type : SubscriptionType;
		frequency : number;
        price : number;
        active : boolean;
        userEmail : string;
        journalIssn : string;
}

enum SubscriptionType{
    WEEK,
    DAY,
    MONTH,
    YEAR
}