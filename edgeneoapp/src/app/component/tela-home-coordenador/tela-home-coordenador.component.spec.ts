import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaHomeCoordenadorComponent } from './tela-home-coordenador.component';

describe('TelaHomeCoordenadorComponent', () => {
  let component: TelaHomeCoordenadorComponent;
  let fixture: ComponentFixture<TelaHomeCoordenadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaHomeCoordenadorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaHomeCoordenadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
