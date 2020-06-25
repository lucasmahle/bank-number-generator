import { Component, OnInit, OnDestroy } from '@angular/core';
import { NumberService } from '../core/services/number.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

import { GeneratedNumberComponent } from '../shared/generated-number/generated-number.component';
import { IGeneratedNumber } from '../core/models/generated-number.model';
import { GrowlerService, GrowlerMessageType } from '../core/growler/growler.service';

@Component({
  selector: 'pp-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.scss']
})
export class ClientComponent implements OnInit, OnDestroy {
  private bsModalRef: BsModalRef;

  constructor(
    private numberService: NumberService,
    private modalService: BsModalService,
    private growlerService: GrowlerService,
  ) { }

  ngOnInit(): void {
    document.body.classList.add('client-mode');
  }

  ngOnDestroy(): void {
    document.body.classList.remove('client-mode');
  }

  public generateNormalNumber() {
    this.numberService.generateNormalNumber().subscribe(
      generated => {
        this.showGeneratedNumberModal(generated);
      },
      (err: string) => {
        this.growlerService.growl(err, GrowlerMessageType.Danger);
      }
    );
  }

  public generatePreferentialNumber() {
    this.numberService.generatePreferentialNumber().subscribe(
      generated => {
        this.showGeneratedNumberModal(generated);
      },
      (err: string) => {
        this.growlerService.growl(err, GrowlerMessageType.Danger);
      }
    );
  }

  private showGeneratedNumberModal(generated: IGeneratedNumber) {
    this.bsModalRef = this.modalService.show(GeneratedNumberComponent, {
      initialState: generated
    });
    this.bsModalRef.content.closeBtnName = 'Fechar';
  }
}
