import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    AdminComponent
  ],
  imports: [ 
    SharedModule,
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
 