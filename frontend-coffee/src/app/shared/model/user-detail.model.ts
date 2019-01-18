import { Authority } from './authority.model';

export interface IUserDetail {
    username ?: string;
    firstName?: string;
    lastName?: string;
    authorites?: Array<Authority>;
}

export class UserDetail implements IUserDetail {
    constructor(username: string,
        firstName: string,
        lastName: string,
        authorites: Array<Authority>){}
}
