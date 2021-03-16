import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Course } from 'src/app/model/course';
import { CourseService } from 'src/app/service/course.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html'
})
export class DetailComponent implements OnInit {
  public students : any[] = [];
  public course: Course;

  constructor(private service: CourseService, private router: ActivatedRoute) { }

  ngOnInit(): void {
    const courseId: number = this.router.snapshot.params.id
    this.service.filterStudents(courseId).subscribe((res) => this.students = res);
    this.findCourse();
  }

  findCourse(): void {
    this.course = JSON.parse(localStorage.getItem('course'));
    localStorage.removeItem('course');
  }
}
