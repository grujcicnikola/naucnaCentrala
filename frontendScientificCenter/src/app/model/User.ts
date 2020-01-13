import { UserRole } from './UserRole';

export class User{
    id : String;
    name : String;
    surname : String;
    password : String;
    email : String;
    roles : Array<UserRole>;
    activated: Boolean;
}

