import { Component, OnInit } from "@angular/core";
import { Postit } from "../postit.model";
import { PostitService } from "../postit.service";

@Component({
    templateUrl: './list-postit.component.html'
})
export class PostitListComponent implements OnInit {

    _postits: Postit[] = [];
    _filterBy: string;
    filteredPostits: Postit[] = [];

    constructor(private postitService: PostitService) {}

    ngOnInit(): void { this.retrieveAll() }

    retrieveAll(): void {
        this.postitService.getAll().subscribe({
            next: postits => { this._postits = postits; 
            this.filteredPostits = this._postits
        },
        error: err => console.log('Error', err)
        })
    }

    deleteById(id : number): void {
        this.postitService.deleteById(id).subscribe({
            next: () => { console.log('Deleted postit with success!', id);
            this.retrieveAll();
        },
        error: err => console.log("Error", err)
        })
    }

    set filter(value : string) {
        this._filterBy = value;
        this.filteredPostits = this._postits.filter
        ((postit: Postit) => postit.title.toLowerCase().indexOf(this._filterBy.toLowerCase()) > -1);
    }

    get filter() {
        return this._filterBy;
    }
}
