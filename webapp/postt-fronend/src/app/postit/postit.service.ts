import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Postit } from "./postit.model";


@Injectable({
    providedIn: 'root'
})
export class PostitService {

    // Spring Boot app port = 8081
    private URL: string = "http://localhost.8081/api/postits/";

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
