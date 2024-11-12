import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaAcepCoordComponent } from './tela-acep-coord.component';

describe('TelaAcepCoordComponent', () => {
  let component: TelaAcepCoordComponent;
  let fixture: ComponentFixture<TelaAcepCoordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaAcepCoordComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaAcepCoordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
