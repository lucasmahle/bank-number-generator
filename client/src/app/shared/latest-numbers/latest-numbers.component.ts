import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from 'src/app/core/services/web-socket.service';
import { NumberService } from 'src/app/core/services/number.service';
import { WebSocketEvent } from 'src/app/core/models/web-socket-event.enum';

@Component({
  selector: 'pp-latest-numbers',
  templateUrl: './latest-numbers.component.html',
  styleUrls: ['./latest-numbers.component.scss']
})
export class LatestNumbersComponent implements OnInit, OnDestroy {
  private subject: Subscription;

  public historic: string[] = [];

  constructor(
    private webSocketService: WebSocketService,
    private numberService: NumberService,
  ) { }

  ngOnInit(): void {
    this.subject = this.webSocketService.connect().subscribe(message => {
      if (message.data === WebSocketEvent.NewNumber) {
        this.updateLatestNumberList();
      }
    });
    this.updateLatestNumberList();
  }

  ngOnDestroy() {
    this.subject.unsubscribe();
  }


  private updateLatestNumberList(): void {
    this.numberService.getLatestNumberList().subscribe(
      data => {
        this.historic = data.map(generatedNumber => generatedNumber.formatted);
      },
      (err: string) => {
      }
    );
  }
}
