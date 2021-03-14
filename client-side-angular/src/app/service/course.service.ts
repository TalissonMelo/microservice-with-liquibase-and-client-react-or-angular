import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Course } from "../model/course";
import { Transaction } from "../model/transaction";

@Injectable({
    providedIn: 'root'
})
export class CourseService {

    constructor(private http: HttpClient) {
    }

    createTransaction(transaction: Transaction): Observable<Transaction> {
        return this.http.post<Transaction>(`${environment.microservice_course}/enroll`, transaction);
    }

    filterTransactions(userId: number): Observable<any> {
        return this.http.get<any>(`${environment.microservice_course}/user/${userId}`);
    }

    filterStudents(courseId: number): Observable<any> {
        return this.http.get<any>(`${environment.microservice_course}/course/${courseId}`);
    }

    findAllCourses(): Observable<Course[]> {
        return this.http.get<Course[]>(`${environment.microservice_course}/all`);
    }
}