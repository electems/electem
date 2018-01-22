import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { CarService } from '../shared/car/car.service';
import { GiphyService } from '../shared/giphy/giphy.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-car-edit',
  templateUrl: './car-edit.component.html',
  styleUrls: ['./car-edit.component.css']
})
export class CarEditComponent implements OnInit, OnDestroy {
  car: any = {};

  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private carService: CarService,
              private giphyService: GiphyService) {
  }

 /*
  * Intialize function: Getting list of records based on the ID provided
  */
  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
		// Follwing snipet makes newtwork call to fetch all the records.
        this.carService.get(id).subscribe((car: any) => {
          if (car) {
            this.car = car;
            this.car.href = car._links.self.href;
            this.giphyService.get(car.name).subscribe(url => car.giphyUrl = url);
          } else {
            this.gotoList();
          }
        });
      }
    });
  }

  /*
   * Destory function: Unsubscribe from Module
   */
  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  /*
   * Navigate to listing page.
  */
  gotoList() {
    this.router.navigate(['/car-list']);
  }

  /*
   * Onclick of Save Form saved to backend.
   */
  save(form: NgForm) {
    this.carService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }

 /*
  * Onclick of Remove deleting the records from list
  */
  remove(href) {
    this.carService.remove(href).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }
}
