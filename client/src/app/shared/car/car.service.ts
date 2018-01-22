import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class CarService {
  public API = '//localhost:8080'; // Api call server port No.
  public CAR_API = this.API + '/cars';

  constructor(private http: HttpClient) {
  }

 /*
  * Following function to list the All the records.
  */
  getAll(): Observable<any> {
    return this.http.get(this.API + '/cool-cars');
  }

 /*
  * Following function to list the record based on ID.
  */
  get(id: string) {
    return this.http.get(this.CAR_API + '/' + id);
  }

  /*
   * Following function to Save the record.
   */
  save(car: any): Observable<any> {
    let result: Observable<Object>;
    if (car['href']) {
      result = this.http.put(car.href, car);
    } else {
      result = this.http.post(this.CAR_API, car);
    }
    return result;
  }

  remove(href: string) {
    return this.http.delete(href);
  }
}
