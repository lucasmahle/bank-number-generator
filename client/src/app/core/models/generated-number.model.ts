import { NumberType } from './number-type.enum';

export interface IGeneratedNumber {
    formatted: string;
    number: number;
    type: NumberType;
}