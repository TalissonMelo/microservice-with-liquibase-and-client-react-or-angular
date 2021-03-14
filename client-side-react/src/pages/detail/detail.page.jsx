import React from 'react';
import CourseService from '../../service/course.service';

export default class DefaultPage extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      id: this.props.match.params.id,
      course: JSON.parse(localStorage.getItem('currentCourse')),
      students: [],
    };
  }

  componentDidMount() {
    this.findStudentsOfCourse();
  }

  findStudentsOfCourse() {
    CourseService.filterStudents(this.state.id).then(students => {
      this.setState({students: students.data});
    });
  }

  render() {
    const {students} = this.state;
    return (
      <div className="col-md-12">
        <div className="jumbotron">
          <h1 className="display-4">Curso: {this.state.course.title}</h1>
          <h1 className="display-4">Número do curso: {this.state.id}</h1>
        </div>
        {students.length &&
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Nome do Estudante</th>
              </tr>
            </thead>
            <tbody>
              {students.map((student, index) =>
                <tr key={student}>
                  <th scope="row">{index + 1}</th>
                  <td>{student}</td>
                </tr>
              )}
            </tbody>
          </table>
        }
      </div>
    );
  }
}