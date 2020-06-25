import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { NumberType } from 'src/app/core/models/number-type.enum';

@Component({
  selector: 'pp-generated-number',
  templateUrl: './generated-number.component.html',
  styleUrls: ['./generated-number.component.scss']
})
export class GeneratedNumberComponent implements OnInit {
  closeBtnName: string;
  formatted: number;
  type: NumberType;
  typeDescription: string;

  constructor(
    public bsModalRef: BsModalRef
  ) { }

  ngOnInit(): void {
    this.typeDescription = this.type == NumberType.Preferential ? 'Preferencial' : 'Normal';
  }

}
