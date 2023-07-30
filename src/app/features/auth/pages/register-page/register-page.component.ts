import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth.service';
import { LoginData } from '../../login-data.interface';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {}

  register(data: LoginData) {
    this.authService
      .register(data)
      .then(() => this.router.navigate(['/login']))
      .catch((e) => console.log(e.message));
  }

}
