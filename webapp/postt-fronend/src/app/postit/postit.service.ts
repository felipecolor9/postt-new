import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Postit } from "./postit.model";


@Injectable({
    providedIn: 'root'
})
export class PostitService {

    // Spring Boot default port = 8000
    private URL: string = "http://localhost.8080/api/postit";

    constructor(private httpClient: HttpClient) {} 

    save(postit:Postit): Observable<Postit> { 
        if (postit.id) {
            return this.httpClient.post<Postit>(`${this.URL}/${postit.id}`, postit);
        } else {
            return this.httpClient.post<Postit>(`${this.URL}`, postit);
        }
    }

    getAll(): Observable<Postit[]> { return this.httpClient.get<Postit[]>(this.URL); }

    retrieveById(id:number): Observable<Postit>{ return this.httpClient.get<Postit>(`${this.URL}/${id}`) }

    deleteById(id: number): Observable<any> { return this.httpClient.delete<any>(`${this.URL}/${id}`) }
}