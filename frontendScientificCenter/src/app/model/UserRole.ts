import { Permission } from './Permission';

export class UserRole{
    id : number;
    name : string;
    permissions : Array<Permission>;
}