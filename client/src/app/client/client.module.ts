import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { ClientComponent } from './client.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    ClientComponent
  ],
  imports: [
    SharedModule,
    CommonModule,
    ClientRoutingModule
  ]
})
export class ClientModule { }
