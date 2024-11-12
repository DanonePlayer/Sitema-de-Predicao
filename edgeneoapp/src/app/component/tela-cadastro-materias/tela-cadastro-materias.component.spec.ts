import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaCadastroMateriasComponent } from './tela-cadastro-materias.component';

describe('TelaCadastroMateriasComponent', () => {
  let component: TelaCadastroMateriasComponent;
  let fixture: ComponentFixture<TelaCadastroMateriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaCadastroMateriasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaCadastroMateriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
