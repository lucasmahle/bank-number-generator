
export class ApiResponseError extends Error {
    constructor(message: string){
        super(message);
    }
}