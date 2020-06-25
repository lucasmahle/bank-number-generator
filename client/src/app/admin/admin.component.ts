import { Component, OnInit } from '@angular/core';
import { NumberService } from '../core/services/number.service';
import { GrowlerService, GrowlerMessageType } from '../core/growler/growler.service';

@Component({
  selector: 'pp-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  constructor(
    private numberService: NumberService,
    private growlerService: GrowlerService,
  ) { }

  ngOnInit(): void {
  }

  public generateNumber() {
    this.numberService.getNextNumber().subscribe(
      response => {
        if(response.number === 0){
          this.growlerService.growl(response.message, GrowlerMessageType.Info);
          return;
        }
      },
      (err: string) => {
        this.growlerService.growl(err, GrowlerMessageType.Danger);
      }
    );
  }

  public resetNumberGeneration() {
    this.numberService.resetNumberGeneration().subscribe(
      () => {
        this.growlerService.growl('Contagem reiniciada', GrowlerMessageType.Info);
      },
      (err: string) => {
        this.growlerService.growl(err, GrowlerMessageType.Danger);
      }
    );
  }
}
