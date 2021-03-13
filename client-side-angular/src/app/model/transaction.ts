import { Course } from "./course";

export class Transaction {
    public id: number;
    public userId: number;
    public dateOfIssue: Date;
    public course: Course
}