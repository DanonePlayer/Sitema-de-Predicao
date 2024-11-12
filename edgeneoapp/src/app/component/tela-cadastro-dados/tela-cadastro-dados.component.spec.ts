import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaCadastroDadosComponent } from './tela-cadastro-dados.component';

describe('TelaCadastroDadosComponent', () => {
  let component: TelaCadastroDadosComponent;
  let fixture: ComponentFixture<TelaCadastroDadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaCadastroDadosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaCadastroDadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
