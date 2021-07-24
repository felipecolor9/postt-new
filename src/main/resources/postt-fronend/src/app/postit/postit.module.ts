import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { RegisterPostitComponent } from "./register-postit/register-postit.component";

@NgModule({
    declarations: [RegisterPostitComponent],
    imports:[
        CommonModule,
        FormsModule,
        RouterModule.forChild([
            {
                path: 'register', component: RegisterPostitComponent
            }
        ])
    ]
})
export class PostitModule { }