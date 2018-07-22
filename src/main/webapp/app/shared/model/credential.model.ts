export interface ICredential {
    id?: number;
    username?: string;
    password?: string;
    hostName?: string;
    hostId?: number;
}

export class Credential implements ICredential {
    constructor(public id?: number, public username?: string, public password?: string, public hostName?: string, public hostId?: number) {}
}
