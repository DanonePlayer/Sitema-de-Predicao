import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaDadosComponent } from './tela-dados.component';

describe('TelaDadosComponent', () => {
  let component: TelaDadosComponent;
  let fixture: ComponentFixture<TelaDadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaDadosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaDadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
