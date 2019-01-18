export interface IAuthority {
    name?: string;
}

export class Authority implements Authority {
    constructor( public name?: string) {}
}
