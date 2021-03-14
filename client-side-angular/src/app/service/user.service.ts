import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { User } from "../model/user";


@Injectable({
    providedIn: 'root'
})
export class UserService {
    constructor(private http: HttpClient) { }

    login(user: User): Observable<any> {
        const headers = { authorization: 'Basic ' + btoa(user.username + ':' + user.password) };
        return this.http.get<any>(`${environment.microservice_user}/login`, { headers: headers })
    }

    logOut(): Observable<void> {
        return this.http.post<any>(`${environment.microservice_user}/logout`, {});
    }

    register(user: User): Observable<User> {
        return this.http.post<User>(`${environment.microservice_user}/registration`, user);
    }
}