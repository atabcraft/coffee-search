

export interface ICoffee {
    id ?: number;
    coffeeType?: string;
    name?: string;
    image?: any;
}

export class Coffee implements ICoffee {
    constructor(id ?: number,
        coffeeType?: string,
        name?: string,
        image?: any) {}
}
