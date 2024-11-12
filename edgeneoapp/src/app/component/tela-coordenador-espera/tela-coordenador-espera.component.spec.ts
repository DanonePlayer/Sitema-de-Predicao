import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaCoordenadorEsperaComponent } from './tela-coordenador-espera.component';

describe('TelaCoordenadorEsperaComponent', () => {
  let component: TelaCoordenadorEsperaComponent;
  let fixture: ComponentFixture<TelaCoordenadorEsperaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaCoordenadorEsperaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaCoordenadorEsperaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
