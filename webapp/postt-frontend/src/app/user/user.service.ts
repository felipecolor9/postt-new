import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { User } from "./user.model";


@Injectable({
    providedIn: 'root'
})
export class UserService {

    // Spring Boot default port = 8000
    private URL: string = "http://localhost.8080/api/user";

    constructor(private httpClient: HttpClient) {} 

    save(user:User): Observable<User> { 
        if (user.id) {
            return this.httpClient.post<User>(`${this.URL}/${user.id}`, user);
        } else {
            return this.httpClient.post<User>(`${this.URL}`, user);
        }
    }

    getAll(): Observable<User[]> { return this.httpClient.get<User[]>(this.URL); }

    retrieveById(id:number): Observable<User>{ return this.httpClient.get<User>(`${this.URL}/${id}`) }

    deleteById(id: number): Observable<any> { return this.httpClient.delete<any>(`${this.URL}/${id}`) }
}