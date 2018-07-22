export const enum State {
    ENABLED = 'ENABLED',
    DISABLED = 'DISABLED'
}

export interface IHost {
    id?: number;
    name?: string;
    description?: string;
    dns?: string;
    port?: number;
    state?: State;
}

export class Host implements IHost {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public dns?: string,
        public port?: number,
        public state?: State
    ) {}
}
