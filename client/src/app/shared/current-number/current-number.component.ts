import { Component, OnInit, OnDestroy } from '@angular/core';
import { WebSocketService } from 'src/app/core/services/web-socket.service';
import { Subscription } from 'rxjs';
import { WebSocketEvent } from 'src/app/core/models/web-socket-event.enum';
import { NumberService } from 'src/app/core/services/number.service';

@Component({
  selector: 'pp-current-number',
  templateUrl: './current-number.component.html',
  styleUrls: ['./current-number.component.scss']
})
export class CurrentNumberComponent implements OnInit, OnDestroy {
  private subject: Subscription;

  public currentNumber: string = '';

  constructor(
    private webSocketService: WebSocketService,
    private numberService: NumberService,
  ) { }

  ngOnInit(): void {
    const obs = this.webSocketService.connect();
    this.subject = obs.subscribe(message => {
      if (message.data === WebSocketEvent.NewNumber) {
        this.updateCurrentNumber();
      }
    });

    obs.complete();

    this.updateCurrentNumber();
  }

  ngOnDestroy() {
    this.subject.unsubscribe();
  }


  private updateCurrentNumber(): void {
    this.numberService.getCurrentNumber().subscribe(
      data => {
        this.currentNumber = data.formatted;
      },
      (err: string) => {
      }
    );
  }
}
