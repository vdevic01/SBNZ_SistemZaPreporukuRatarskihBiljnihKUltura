import {Component, OnInit} from '@angular/core';
import {ParcelService} from "../../core/services/parcel.service";
import {ActivatedRoute} from "@angular/router";
import {
  CropType, CropTypeMapping,
  Manufacturer,
  ManufacturerMapping,
  Parcel,
  WindStrength,
  WindStrengthMapping
} from "../../core/model/parcel";
import * as L from "leaflet";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-parcel-details-page',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './parcel-details-page.component.html',
  styleUrl: './parcel-details-page.component.css'
})
export class ParcelDetailsPageComponent implements OnInit{
  parcel?: Parcel;
  map: any;
  constructor(private parcelService:ParcelService, private route: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params["id"];
      this.parcelService.getParcel(id).subscribe(parcel => {
        this.parcel = parcel;
        this.loadMap();
      });
    })
  }
  getManufacturerLogoPath(manufacturer:Manufacturer): string{
    return `/assets/images/${manufacturer}.png`;
  }
  getCropTypeIconPath(cropType: CropType): string{
    return `/assets/images/${cropType}.png`;
  }
  getWindStrengthMapping(wind: WindStrength): string{
    return WindStrengthMapping[wind];
  }
  getCropMapping(cropType: CropType): string{
    return CropTypeMapping[cropType];
  }
  getManufacturerMapping(manufacturer: Manufacturer): string{
    return ManufacturerMapping[manufacturer];
  }

  private loadMap(){
    L.Marker.prototype.options.icon = L.icon({
      iconUrl: 'https://unpkg.com/leaflet@1.6.0/dist/images/marker-icon.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [0, -35],
      shadowSize: [41, 41]
    });
    this.map = L.map('map', {
      center:[this.parcel!.latitude, this.parcel!.longitude],
      zoom:12,
    });
    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 11,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );
    tiles.addTo(this.map);

    const marker = L.marker([this.parcel!.latitude, this.parcel!.longitude]);
    marker.addTo(this.map);
  }
}
