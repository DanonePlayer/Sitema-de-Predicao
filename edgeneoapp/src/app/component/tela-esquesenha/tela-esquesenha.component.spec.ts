import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaEsquesenhaComponent } from './tela-esquesenha.component';

describe('TelaEsquesenhaComponent', () => {
  let component: TelaEsquesenhaComponent;
  let fixture: ComponentFixture<TelaEsquesenhaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaEsquesenhaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaEsquesenhaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
