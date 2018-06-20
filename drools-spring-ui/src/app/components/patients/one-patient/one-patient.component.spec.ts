import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OnePatientComponent } from './one-patient.component';

describe('OnePatientComponent', () => {
  let component: OnePatientComponent;
  let fixture: ComponentFixture<OnePatientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OnePatientComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnePatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
