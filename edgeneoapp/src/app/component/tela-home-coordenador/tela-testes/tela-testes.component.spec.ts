import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaTestesComponent } from './tela-testes.component';

describe('TelaTestesComponent', () => {
  let component: TelaTestesComponent;
  let fixture: ComponentFixture<TelaTestesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaTestesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaTestesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
