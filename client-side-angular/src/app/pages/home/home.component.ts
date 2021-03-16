import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/model/course';
import { CourseService } from 'src/app/service/course.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  public courses: Course[] = [];

  constructor(private service: CourseService) { }

  ngOnInit(): void {
    this.service.findAllCourses().subscribe((res) => this.courses = res);
  }

  detail():void {
    console.log("Detail");
  }

  register(): void {
    console.log("Register")
  }
}
