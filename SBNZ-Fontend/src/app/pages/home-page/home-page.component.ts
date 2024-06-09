import {Component, OnInit} from '@angular/core';
import {Parcel, WindStrength, WindStrengthMapping} from "../../core/model/parcel";
import {ParcelService} from "../../core/services/parcel.service";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit{
  isLoading: boolean = true;
  parcels: Parcel[] = [];
  tableColumnNames: string[] = ["Name", "Coordinates", "Humus Content", "Wind"];
  constructor(private parcelService: ParcelService, private router: Router) {
  }
  getWindStrengthMapping(wind: WindStrength): string{
    return WindStrengthMapping[wind];
  }
  ngOnInit(): void {
    this.parcelService.getParcelsForCurrentUser().subscribe(parcels => {
      this.parcels = parcels;
      this.isLoading = false;
    });
  }

  openParcelDetails(id:number){
    this.router.navigate([`/parcel/${id}`]).then(() => {});
  }
  openCreateParcelPage(){
    this.router.navigate(['/parcel/create']).then(() => {});
  }

  protected readonly WindStrength = WindStrength;
}
