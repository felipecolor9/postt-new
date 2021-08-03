import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { PostitListComponent } from "./list-postit/list-postit.component";
import { RegisterPostitComponent } from "./register-postit/register-postit.component";
import { FormsModule } from "@angular/forms";

@NgModule({
    declarations: [RegisterPostitComponent, PostitListComponent],
    imports:[
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule.forChild([
            {
                path: 'register', component: RegisterPostitComponent
            },
            {
                path: 'dashboard', component: PostitListComponent
            }
        ])
    ]
})
export class PostitModule { }