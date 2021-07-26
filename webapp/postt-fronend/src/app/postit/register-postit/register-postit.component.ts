import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { Postit } from "../postit.model";
import { PostitService } from "../postit.service";

@Component({
    selector: 'app-register-postit',
    templateUrl: './register-postit.component.html',
})
export class RegisterPostitComponent implements OnInit {

    postitForm: FormGroup;

    constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, private postitService: PostitService) { }
 
    ngOnInit(): void {
        this.createForm(new Postit());
    }

    createForm(postit:Postit) {
        this.postitForm = this.formBuilder.group({
            title: '',
            details: ''
        })
        this.postitForm.valueChanges.subscribe(console.log); 
    }

    //TODO: finish save function
    save(): void {
        
    }
  

}