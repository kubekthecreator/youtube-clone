import {Component, OnInit} from '@angular/core';
import {UserService} from "../user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrl: './callback.component.css'
})
export class CallbackComponent implements OnInit {
  isLoading = true;
  errorMessage = '';

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.registerAndRedirect();
  }

  private registerAndRedirect(): void {
    this.userService.registerUser().subscribe({
      next: () => {
        setTimeout(() => {
          this.router.navigateByUrl('');
        }, 2000); // Opóźnienie 2 sekundy przed przekierowaniem
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = 'An error occurred during registration. Please try again.';
        console.error('Registration error:', err);
      }
    });
  }
}
