import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModePickerComponent } from './mode-picker.component';

const routes: Routes = [
  {
    path: '',
    component: ModePickerComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModePickerRoutingModule { }
