import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaContrucaoComponent } from './tela-contrucao.component';

describe('TelaContrucaoComponent', () => {
  let component: TelaContrucaoComponent;
  let fixture: ComponentFixture<TelaContrucaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaContrucaoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaContrucaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
