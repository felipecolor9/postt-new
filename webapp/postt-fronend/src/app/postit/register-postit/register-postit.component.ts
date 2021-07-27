import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { Postit } from "../postit.model";
import { PostitService } from "../postit.service";
import { Validators } from "@angular/forms";

@Component({
    selector: 'app-register-postit',
    templateUrl: './register-postit.component.html',
})
export class RegisterPostitComponent implements OnInit {

    postitForm: FormGroup;
    postit: Postit;

    constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, private postitService: PostitService) { }
 
    ngOnInit(): void {
        this.createForm();
    }

    createForm() {
        this.postitForm = this.formBuilder.group({
            title: ['', [Validators.minLength(2), Validators.required]],
            details: ''
        })
      
    }

    //TODO: finish save function
    save(): void {
        this.postit = this.postitForm.value;
        console.log(this.postit)
        this.postitService.save(this.postit).subscribe({
            next: postit => console.log('Saved with success!', postit),
            error: err => console.log('Error', err)
        });
    }
  

}