import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaEscolherMateriasComponent } from './tela-escolher-materias.component';

describe('TelaEscolherMateriasComponent', () => {
  let component: TelaEscolherMateriasComponent;
  let fixture: ComponentFixture<TelaEscolherMateriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaEscolherMateriasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaEscolherMateriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
