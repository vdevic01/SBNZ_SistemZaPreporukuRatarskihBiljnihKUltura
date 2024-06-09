import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateParcelPageComponent } from './create-parcel-page.component';

describe('CreateParcelPageComponent', () => {
  let component: CreateParcelPageComponent;
  let fixture: ComponentFixture<CreateParcelPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateParcelPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateParcelPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
