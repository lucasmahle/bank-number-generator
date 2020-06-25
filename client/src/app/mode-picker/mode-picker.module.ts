import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ModePickerRoutingModule } from './mode-picker-routing.module';
import { ModePickerComponent } from './mode-picker.component';


@NgModule({
  declarations: [
    ModePickerComponent
  ],
  imports: [
    CommonModule,
    ModePickerRoutingModule
  ]
})
export class ModePickerModule { }
