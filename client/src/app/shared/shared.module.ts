import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';

import { CurrentNumberComponent } from './current-number/current-number.component';
import { LatestNumbersComponent } from './latest-numbers/latest-numbers.component';
import { GeneratedNumberComponent } from './generated-number/generated-number.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    RouterModule,
    CurrentNumberComponent,
    LatestNumbersComponent
  ],
  declarations: [
    CurrentNumberComponent,
    LatestNumbersComponent,
    GeneratedNumberComponent
  ]
})
export class SharedModule { }
