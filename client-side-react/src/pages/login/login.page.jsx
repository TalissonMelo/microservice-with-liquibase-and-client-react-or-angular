import React from 'react';
import { User } from '../../model/user';
import UserService from '../../service/user.service';
import './login.page.css';


export default class LoginPage extends React.Component {

    constructor(props) {
        super(props)

        if (UserService.currentUserValue) {
            this.props.history.push('/');
        }

        this.state = {
            user: new User('', ''),
            submitted: false,
            loading: false,
            errorMessage: ''
        };
    }

    handleChange(e) {
        var { name, value } = e.target;
        var user = this.state.user;
        user[name] = value;
        this.setState({ user: user });
    }

    handleLogin(e) {
        e.preventDefault();

        this.setState({ submitted: true });
        const { user } = this.state;

        if (!(user.username && user.password)) {
            return;
        }

        this.setState({ loading: true });
        UserService.login(user).then(data => {
            this.props.history.push("/home");
        }, () => {
            this.setState({
                errorMessage: "Nome de usuário ou senha não são válidos.",
                loading: false
            });
        });
    }

    render() {
        const { user, submitted, loading, errorMessage } = this.state;
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <img id="profile-img" className="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
                    {errorMessage &&
                        <div className="alert alert-danger" role="alert">
                            <strong>Ops, algo deu errado. Tente novamente! </strong> {errorMessage}
                        </div>
                    }
                    <form name="form" onSubmit={(e) => this.handleLogin(e)}>
                        <div className={'form-group' + (submitted && !user.username ? 'has-error' : '')}>
                            <label htmlFor="username">Nome de usuário</label>
                            <input type="text" className="form-control" name="username" value={user.username} onChange={(e) => this.handleChange(e)} />
                            {submitted && !user.username &&
                                <div className="help-block">Nome de usuário é obrigatório.</div>
                            }
                        </div>
                        <div className={'form-group' + (submitted && !user.password ? 'has-error' : '')}>
                            <label htmlFor="password">Senha </label>
                            <input type="password" className="form-control" name="password" value={user.password} onChange={(e) => this.handleChange(e)} />
                            {submitted && !user.password &&
                                <div className="help-block">Senha é obrigatório.</div>
                            }
                        </div>
                        <div className="form-group">
                            <button className="btn btn-lg btn-primary btn-block btn-signin form-submit-button" disabled={loading}>Logar</button>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}