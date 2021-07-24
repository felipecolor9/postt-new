import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Postit } from "../postit.model";
import { PostitService } from "../postit.service";

@Component({
    selector: 'app-register-postit',
    templateUrl: './register-postit.component.html'
})
export class RegisterPostitComponent implements OnInit {

    constructor(private activatedRoute: ActivatedRoute, private postitService: PostitService) { }

    postit:Postit
 
    ngOnInit(): void {}

    //TODO: finish save function
    save(): void {
        this.postitService.save(this.postit).subscribe();
    }
}