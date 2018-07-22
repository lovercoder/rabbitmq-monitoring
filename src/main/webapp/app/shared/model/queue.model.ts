export interface IQueue {
    id?: number;
    name?: string;
    count?: number;
    description?: string;
    hostName?: string;
    hostId?: number;
}

export class Queue implements IQueue {
    constructor(
        public id?: number,
        public name?: string,
        public count?: number,
        public description?: string,
        public hostName?: string,
        public hostId?: number
    ) {}
}
