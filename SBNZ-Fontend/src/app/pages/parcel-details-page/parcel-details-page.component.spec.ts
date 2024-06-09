import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParcelDetailsPageComponent } from './parcel-details-page.component';

describe('ParcelDetailsPageComponent', () => {
  let component: ParcelDetailsPageComponent;
  let fixture: ComponentFixture<ParcelDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParcelDetailsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParcelDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
