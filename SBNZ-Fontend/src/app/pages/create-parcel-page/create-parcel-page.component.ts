import {AfterViewInit, Component} from '@angular/core';
import {
  CreateParcelDto,
  CropType,
  CropTypeMapping,
  Manufacturer,
  ManufacturerMapping,
  WindStrength,
  WindStrengthMapping
} from "../../core/model/parcel";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import * as L from "leaflet";
import {LeafletMouseEvent} from "leaflet";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {ParcelService} from "../../core/services/parcel.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-parcel-page',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgForOf,
    NgClass,
    NgIf
  ],
  templateUrl: './create-parcel-page.component.html',
  styleUrl: './create-parcel-page.component.css'
})
export class CreateParcelPageComponent implements AfterViewInit{
  private map: any;
  private locationMarker?: L.Marker;
  parcelForm:FormGroup;
  selectedCropType?: CropType;
  manufacturers: Manufacturer[] = [];
  selectedManufacturers: Manufacturer[] = [];
  constructor(private parcelService:ParcelService, private router: Router) {
    this.parcelForm = new FormGroup({
      name: new FormControl(null, [Validators.required]),
      humusContent: new FormControl(null, [Validators.required]),
      windStrength: new FormControl(null, [Validators.required]),
      latitude: new FormControl(null, [Validators.required]),
      longitude: new FormControl(null, [Validators.required])
    });
    this.manufacturers = Object.values(Manufacturer).filter(m => m!="ANY");
  }
  ngAfterViewInit(): void {
      this.loadMap();
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
      center:[44.810030, 20.454764],
      zoom:7,
    });
    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );
    tiles.addTo(this.map);

    // const marker = L.marker([this.parcel!.latitude, this.parcel!.longitude]);
    // marker.addTo(this.map);
    this.map.on('click', (e:LeafletMouseEvent) => {
      if(this.locationMarker){
        this.map?.removeControl(this.locationMarker);
      }
      this.locationMarker = L.marker(e.latlng).addTo(this.map!);
      this.parcelForm.patchValue({
        latitude: e.latlng.lat,
        longitude: e.latlng.lng
      })
    });
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

  selectManufacturer(manufacturer: Manufacturer){
    const index = this.selectedManufacturers.indexOf(manufacturer);
    if (index !== -1) {
      this.selectedManufacturers.splice(index, 1);
    } else {
      this.selectedManufacturers.push(manufacturer);
    }
  }
  sowingDate?: Date;
  isSelected(manufacturer: Manufacturer): boolean{
    return this.selectedManufacturers.find(m => m==manufacturer) != undefined;
  }
  createParcel(){
    let parcel: CreateParcelDto = {
      name: this.parcelForm.get("name")?.value,
      expectedWindStrength: this.parcelForm.get("windStrength")?.value,
      humusContent: this.parcelForm.get("humusContent")?.value,
      latitude: this.parcelForm.get("latitude")?.value,
      longitude: this.parcelForm.get("longitude")?.value,
      manufacturerPreferences: this.selectedManufacturers.length == 0 ? [Manufacturer.ANY] : this.manufacturers
    };
    if(this.sowingDate && this.selectedCropType){
      parcel.lastSowing = {
        date: this.sowingDate.toString(),
        plant: this.selectedCropType
      }
    }
    this.parcelService.createParcel(parcel).subscribe(parcel => {
      this.router.navigate(['/home']).then(() => {});
    });
  }

  protected readonly WindStrength = WindStrength;
  protected readonly Object = Object;
  protected readonly CropType = CropType;
  protected readonly Manufacturer = Manufacturer;
}
