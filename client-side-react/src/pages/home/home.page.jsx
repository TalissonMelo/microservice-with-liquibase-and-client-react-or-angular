import React from 'react';
import { Transaction } from '../../model/transaction';
import { User } from '../../model/user';
import CourseService from '../../service/course.service';
import UserService from '../../service/user.service';

export default class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            courses: [],
            errorMessage: '',
            infoMessage: '',
            currentUser: new User()
        };
    }

    componentDidMount() {
        UserService.currentUser.subscribe(data => {
            this.setState({
                currentUser: data
            });
        });

        this.getAllCourses();
    }

    getAllCourses() {
        this.setState({
            courses: { loading: true }
        });

        CourseService.findAllCourses().then(courses => {
            this.setState({ courses: courses.data });
        });
    }

    enroll(course) {
        if (!this.state.currentUser) {
            this.setState({ errorMessage: 'Para se inscrever em um curso, você deve se cadastrar.' });
            return;
        }

        var transaction = new Transaction(this.state.currentUser.id, course);
        CourseService.createTransaction(transaction).then(data => {
            this.setState({ infoMessage: 'Você se inscreveu no curso com sucesso.' });
        }, error => {
            this.setState({ errorMessage: 'Ocorreu um erro inesperado.' });
        });
    }

    detail(course) {
        localStorage.setItem('currentCourse', JSON.stringify(course));
        this.props.history.push('/detail/' + course.id);
    }

    render() {
        const { courses, infoMessage, errorMessage } = this.state;
        return (
            <div className="col-md-12">
                {infoMessage &&
                    <div className="alert alert-success">
                        <strong>Sucesso! </strong>{infoMessage}
                        <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                }
                {errorMessage &&
                    <div className="alert alert-danger">
                        <strong>Ops, algo deu errado. Tente novamente! </strong> {errorMessage}
                        <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                }
                {courses.loading && <em> Carregando cursos ...</em>}
                {courses.length &&
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Curso</th>
                                <th scope="col">Professor</th>
                                <th scope="col">Detalhes</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {courses.map((course, index) =>
                                <tr key={course.id}>
                                    <th scope="row">{index + 1}</th>
                                    <td>{course.title}</td>
                                    <td>{course.author}</td>
                                    <td>
                                        <button className="btn btn-info" onClick={() => this.detail(course)}>Detalhes</button>
                                    </td>
                                    <td>
                                        <button className="btn btn-success" onClick={() => this.enroll(course)}>Matricular-se</button>
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                }
            </div>
        );
    }

}