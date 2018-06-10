import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiagnoseIllnessComponent } from './diagnose-illness.component';

describe('DiagnoseIllnessComponent', () => {
  let component: DiagnoseIllnessComponent;
  let fixture: ComponentFixture<DiagnoseIllnessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnoseIllnessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnoseIllnessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
